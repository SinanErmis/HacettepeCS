import java.util.List;

/**
 * Represents a group of symbols in a context-free grammar.
 * A SymbolGroup is used to combine multiple symbols into a single expansion.
 */
public class SymbolGroup extends Symbol {
    private List<Symbol> expansion;

    /**
     * Constructs a SymbolGroup with the specified list of symbols.
     *
     * @param expansion The list of symbols to be combined.
     */
    public SymbolGroup(List<Symbol> expansion) {
        this.expansion = expansion;
    }

    /**
     * Expands the SymbolGroup into a string representation.
     *
     * @return The expanded group of symbols as a string.
     */
    @Override
    public String expand() {
        StringBuilder sb = new StringBuilder();
        for (Symbol s : expansion) {
            sb.append(s.expand());
        }
        return sb.toString();
    }
}
