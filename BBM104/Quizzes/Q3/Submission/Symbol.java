/**
 * Represents an abstract symbol in a context-free grammar.
 * Subclasses of Symbol can be either terminal or non-terminal symbols.
 */
public abstract class Symbol {
    /**
     * Expands the symbol into a string representation.
     *
     * @return The expanded symbol as a string.
     */
    public abstract String expand();
}
