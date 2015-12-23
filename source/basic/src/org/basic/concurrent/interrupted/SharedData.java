package org.basic.concurrent.interrupted;

public class SharedData {

    private char character;
    private boolean writeable = true;

    /**
     * @return get method for the field character
     */
    public synchronized char getCharacter() {

        if (writeable) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        writeable = true;
        notify();
        return this.character;
    }

    /**
     * current thread not owner"
     * 
     * @param character
     *            the character to set
     */
    public synchronized void setCharacter(char character) {

        if (writeable) {
            this.character = character;
            writeable = false;
            notify();
        } else {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
