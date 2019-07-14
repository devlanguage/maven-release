/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file org.basic.threads.Apple.java is
 * created on 2008-3-12
 */
package org.basic.grammar.ucf.apple;

/**
 * 
 */
public class Apple {

    private String id;
    private String name;
    private String color;
    private boolean enable = false;

    private final static String DEFAULT_NAME = "TIANSHUI_APPLE";
    private static final String DEFAULT_COLOR = "Green";

    @Override
    public String toString() {

        return new StringBuffer().append(this.getClass().getSimpleName()).append(":[")
                .append("id=").append(this.id).append(",name=").append(this.name).append(",color=")
                .append(this.color).append("]").toString();
    }

    public Apple(String id) {

        this(id, DEFAULT_NAME, DEFAULT_COLOR);
    }

    public Apple(String id, String name, String color) {

        this(id, DEFAULT_NAME, DEFAULT_COLOR, false);
    }

    public Apple(String id, String name, String color, boolean enable) {

        this.id = id;
        this.name = name;
        this.color = color;
        this.enable = enable;
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

    /**
     * @return get method for the field name
     */
    public String getName() {

        return this.name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {

        this.name = name;
    }

    /**
     * @return get method for the field color
     */
    public String getColor() {

        return this.color;
    }

    /**
     * @param color
     *            the color to set
     */
    public void setColor(String color) {

        this.color = color;
    }

    /**
     * @return get method for the field enable
     */
    public boolean isEnable() {

        return this.enable;
    }

    /**
     * @param enable
     *            the enable to set
     */
    public void setEnable(boolean enable) {

        this.enable = enable;
    }
}
