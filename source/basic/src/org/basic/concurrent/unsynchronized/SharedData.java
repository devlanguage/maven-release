package org.basic.concurrent.unsynchronized;

public class SharedData {

    private char character;

    /**
     * @return get method for the field character
     */
    public char getCharacter() {

        return this.character;
    }

    /**
     * @param character
     *            the character to set
     */
    public void setCharacter(char character) {

        this.character = character;
    }
}
