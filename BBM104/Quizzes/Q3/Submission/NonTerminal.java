/**
 * Represents a non-terminal symbol in a BNF grammar.
 * Non-terminal symbols are uppercase letters used to denote syntactic categories.
 * Each non-terminal symbol can be expanded into one or more sequences of symbols
 * based on the production rules defined in the BNF grammar.
 */
public class NonTerminal extends Symbol {
    private char value;

    /**
     * Constructs a NonTerminal object with the specified character value.
     *
     * @param value The character representing the non-terminal symbol.
     */
    public NonTerminal(char value) {
        this.value = value;
    }

    /**
     * Retrieves the character value of this non-terminal symbol.
     *
     * @return The character value representing the non-terminal symbol.
     */
    public char getValue() {
        return value;
    }

    /**
     * Expands this non-terminal symbol into its possible sequences of symbols
     * based on the BNF production rules defined in the Ruleset.
     *
     * @return String representation of the expansion of this non-terminal symbol.
     */
    @Override
    public String expand() {
        return Ruleset.getExpansion(this);
    }

    /**
     * Computes the hash code of this non-terminal symbol based on its character value.
     *
     * @return The hash code value for this non-terminal symbol.
     */
    @Override
    public int hashCode() {
        return Character.hashCode(value);
    }

    /**
     * Checks if this non-terminal symbol is equal to another object.
     * Two NonTerminal objects are considered equal if they have the same character value.
     *
     * @param obj The object to compare with this non-terminal symbol.
     * @return true if the objects are equal (i.e., both are NonTerminal with the same character value), otherwise false.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof NonTerminal) {
            return ((NonTerminal) obj).value == value;
        }
        return false;
    }
}
