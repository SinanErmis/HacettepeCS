/**
 * Represents a terminal symbol in a context-free grammar.
 * A terminal symbol corresponds to a specific character or token.
 */
public class Terminal extends Symbol {
    private char value;

    /**
     * Constructs a Terminal with the specified character value.
     *
     * @param value The character value representing the terminal symbol.
     */
    public Terminal(char value) {
        this.value = value;
    }

    /**
     * Expands the terminal symbol into its string representation.
     *
     * @return The expanded terminal symbol as a string.
     */
    @Override
    public String expand() {
        return Character.toString(value);
    }
}
