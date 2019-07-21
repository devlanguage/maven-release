package org.basic.common.bean;

/**
 *  
 */
public enum RelativePath {
    
    CLASS_PATH("class_path"), OPERATE_SYSTEM_ABSOLUTE_PATH("operate_system_absolute_path");

    private String name;

    private RelativePath(String name) {

        this.name = name;
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
}
