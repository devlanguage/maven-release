package org.third.build.ant;

import org.apache.tools.ant.BuildException;

public class PrintTask extends org.apache.tools.ant.Task {

    private String content;
    private String id;
//getter and setter method
@Override
public void init() throws BuildException {

    super.init();
}


    @Override
    public void execute() throws BuildException {

        System.out.println("content=" + content + ", id=" + id);
        super.execute();
    }

    public static void main(String[] args) {

        System.out.println("this my task");
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

    /**
     * @return get method for the field id
     */
    public String getId() {

        return this.id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id) {

        this.id = id;
    }
}
