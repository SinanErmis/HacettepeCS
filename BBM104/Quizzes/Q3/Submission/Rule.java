import java.util.List;

/**
 * Represents a production rule in a BNF (Backus-Naur Form) grammar.
 * A rule defines how a non-terminal symbol (lhs) can be expanded into
 * one or more sequences of symbols (expansion) in the grammar.
 */
public class Rule {
    private NonTerminal lhs; // Left-hand side (non-terminal)

    // Using a List for the expansion allows flexibility in representing the right-hand side of the production rule.
    // Lists provide dynamic sizing, allowing rules with varying numbers of symbols.
    // Lists are iterable, making it easy to traverse and process the symbols during rule expansion.
    private List<Symbol> expansion; // Right-hand side (sequence of symbols)

    /**
     * Constructs a new rule.
     *
     * @param lhs       The non-terminal on the left-hand side.
     * @param expansion The list of symbols on the right-hand side.
     */
    public Rule(NonTerminal lhs, List<Symbol> expansion) {
        this.lhs = lhs;
        this.expansion = expansion;
    }

    /**
     * Expands the rule into a string representation.
     *
     * @return The expanded rule as a string.
     */
    public String expand() {
        StringBuilder sb = new StringBuilder();
        sb.append('(');
        for (int i = 0; i < expansion.size(); i++) {
            sb.append(expansion.get(i).expand());
            if (i < expansion.size() - 1) {
                sb.append('|');
            }
        }
        sb.append(')');
        return sb.toString();
    }

    /**
     * Gets the left-hand side (non-terminal) of the rule.
     *
     * @return The non-terminal symbol.
     */
    public NonTerminal getLhs() {
        return lhs;
    }

    /**
     * Overrides the default hashCode method.
     *
     * @return The hash code based on the left-hand side symbol.
     */
    @Override
    public int hashCode() {
        return lhs.hashCode();
    }
}
