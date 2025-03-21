import java.util.HashSet;

/**
 * Represents a set of production rules in a BNF (Backus-Naur Form) grammar.
 * The rules define how non-terminal symbols can be expanded into sequences of symbols.
 */
public class Ruleset {
    // HashSet is suitable for storing unique elements (in this case, unique rules).
    // It ensures that duplicate rules are automatically eliminated.
    // The lookup time for retrieving rules based on non-terminal symbols is constant (O(1)).
    // The order of rules is not important, and a HashSet provides efficient storage and retrieval without maintaining order.
    private static HashSet<Rule> rules = new HashSet<>();

    /**
     * Retrieves the expansion for a given non-terminal symbol.
     *
     * @param value The non-terminal symbol to look up.
     * @return The expanded rule as a string.
     * @throws IllegalArgumentException if no rule is found for the specified non-terminal.
     */
    public static String getExpansion(NonTerminal value) {
        for (Rule r : rules) {
            if (r.getLhs().equals(value)) {
                return r.expand();
            }
        }
        throw new IllegalArgumentException("No rule found for " + value);
    }

    /**
     * Prints all rules in the ruleset.
     */
    public static void print() {
        StringBuilder sb = new StringBuilder();
        for (Rule r : rules) {
            sb.append(r.getLhs().getValue()).append(" => ").append(r.expand()).append("\n");
        }
        System.out.print(sb.toString());
    }

    /**
     * Adds a new rule to the ruleset.
     *
     * @param rule The rule to add.
     */
    public static void add(Rule rule) {
        rules.add(rule);
    }

    /**
     * Clears all rules from the ruleset.
     */
    public static void clear() {
        rules.clear();
    }
}
