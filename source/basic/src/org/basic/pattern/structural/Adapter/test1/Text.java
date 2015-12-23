package org.basic.pattern.structural.Adapter.test1;

/**
 * The Adaptee in this sample
 */
public class Text {

    private String content;

    public Text() {

    }

    /**
     * @return get method for the field content
     */
    public String getContent() {

        return this.content;
    }

    /**
     * @param content
     *            the content to set
     */
    public void setContent(String content) {

        this.content = content;
    }

}