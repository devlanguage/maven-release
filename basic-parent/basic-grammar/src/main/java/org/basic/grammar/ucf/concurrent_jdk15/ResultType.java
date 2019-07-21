package org.basic.grammar.ucf.concurrent_jdk15;

public class ResultType {

    private String content;

    public ResultType() {

        this("No Any Result searched");
    }

    public ResultType(String content) {

        this.content = content;
    }

    @Override
    public String toString() {
    
        return this.getClass().getSimpleName()+":[content="+this.content+"]";
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
