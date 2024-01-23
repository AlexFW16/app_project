package uk.bradford.app_project;


public class Cipher {

    private final int LAYOUT;
    private final int ERROR_MSG;

    private final int USAGE;

    private final int DESCRIPTION;

    private final Type TYPE;


    // Create a Cipher object that links to all relevant strings/ids
    public Cipher(int LAYOUT, int ERROR_MSG, int USAGE, int DESCRIPTION, Type TYPE) {
        this.LAYOUT = LAYOUT;
        this.ERROR_MSG = ERROR_MSG;
        this.USAGE = USAGE;
        this.DESCRIPTION = DESCRIPTION;
        this.TYPE = TYPE;
    }

    public int getLayout() {
        return LAYOUT;
    }

    public int getErrorMsg() {
        return ERROR_MSG;
    }

    public Cipher.Type getType() {
        return TYPE;
    }

    public int getUsage() {
        return USAGE;
    }

    public int getDescription() {
        return DESCRIPTION;
    }

    public enum Type {
        VIGENERE, XOR, SUBSTITUTION, TRANSPOSITION
    }
}
