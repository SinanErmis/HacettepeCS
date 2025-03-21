import java.util.ArrayList;
import java.util.List;

/**
 * Backus-Naur Form (BNF) parser and generator.
 * This class reads BNF rules from an input file, generates possible expansions
 * for the defined grammar, and writes the results to an output file.
 */
public class BNF {
    public static void main(String[] args) {
        String inputFileName = args[0];
        String outputFileName = args[1];

        // Read BNF rules from the input file
        String[] lines = FileUtilities.readFile(inputFileName, true, true);

        // Create a BNF instance and define the ruleset from input lines
        BNF bnf = new BNF();
        bnf.defineRuleset(lines);

        // Get all possible expansions starting from the 'S' symbol
        String output = bnf.getAllPossibilities();

        // Write the generated expansions to the output file
        FileUtilities.writeToFile(outputFileName, output, false, false);
    }

    /**
     * Retrieve all possible expansions starting from the designated start symbol 'S'.
     *
     * @return String containing all possible expansions derived from the start symbol 'S'.
     */
    private String getAllPossibilities() {
        return Ruleset.getExpansion(new NonTerminal('S'));
    }

    /**
     * Define the ruleset by parsing BNF rules from input lines.
     *
     * @param lines Array of strings representing BNF rules in the format "LHS -> RHS".
     */
    private void defineRuleset(String[] lines) {
        for (String line : lines) {
            String[] parts = line.split("->");
            char lhs = parts[0].trim().charAt(0);
            String rhs = parts[1].trim();
            List<Symbol> expansion = new ArrayList<Symbol>();
            for (String symbolString : rhs.split("\\|")) {
                expansion.add(getSymbol(symbolString));
            }
            // Add a new rule to the ruleset based on parsed LHS and RHS
            Ruleset.add(new Rule(new NonTerminal(lhs), expansion));
        }
    }

    /**
     * Create a Symbol object based on the given symbol string.
     *
     * @param symbol The string representation of the symbol (either terminal or non-terminal).
     * @return Symbol object representing the parsed symbol.
     */
    private Symbol getSymbol(String symbol) {
        if (symbol.length() > 1) {
            // If the symbol is a sequence of characters, create a SymbolGroup
            List<Symbol> expansion = new ArrayList<Symbol>();
            for (char c : symbol.toCharArray()) {
                expansion.add(getSymbol(Character.toString(c)));
            }
            return new SymbolGroup(expansion);
        }
        // If the symbol is a single character
        char c = symbol.charAt(0);
        if (Character.isUpperCase(c)) {
            // If it's an uppercase letter, treat it as a NonTerminal
            return new NonTerminal(c);
        } else {
            // Otherwise, treat it as a Terminal
            return new Terminal(c);
        }
    }
}

