package org.basic.jdk.core.ucf.synchronized_method;

public class SharedData {

    private char character;
    private boolean writeable = true;

    /**
     * @return get method for the field character
     */
    public synchronized char getCharacter() {

//        if (writeable) { being replaced by while clasued target to avoid the fake wakeup 
        while (writeable) { 
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
