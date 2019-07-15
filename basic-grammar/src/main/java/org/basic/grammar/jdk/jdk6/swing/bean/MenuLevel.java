package org.basic.grammar.jdk.jdk6.swing.bean;

import java.util.ArrayList;
import java.util.List;

import javax.swing.MenuElement;

public class MenuLevel {

    private String name;
    private String nodeName;

    private boolean menuLevel;
    private boolean attachDelimiter;

    private String identifier;
    private MenuElement menuElement;

    private MenuLevel parent;
    private List<MenuLevel> chilren;

    /**
     * @return get method for the field chilren
     */
    public List<MenuLevel> getChilren() {

        return this.chilren = this.chilren == null ? new ArrayList<MenuLevel>() : this.chilren;
    }

    public void addChild(MenuLevel menuLevel) {

        menuLevel.setParent(this);
        this.getChilren().add(menuLevel);
    }

    /**
     * @return get method for the field identifier
     */
    public String getIdentifier() {

        return this.identifier;
    }

    /**
     * @param identifier
     *            the identifier to set
     */
    public void setIdentifier(String identifier) {

        this.identifier = identifier;
    }

    /**
     * @return get method for the field menuElement
     */
    public MenuElement getMenuElement() {

        return this.menuElement;
    }

    /**
     * @return get method for the field name
     */
    public String getName() {

        return this.name;
    }

    /**
     * @return get method for the field parent
     */
    public MenuLevel getParent() {

        return this.parent;
    }

    /**
     * @return get method for the field attachDelimiter
     */
    public boolean isAttachDelimiter() {

        return this.attachDelimiter;
    }

    /**
     * @param attachDelimiter
     *            the attachDelimiter to set
     */
    public void setAttachDelimiter(boolean attachDelimiter) {

        this.attachDelimiter = attachDelimiter;
    }

    /**
     * @param chilren
     *            the chilren to set
     */
    public void setChilren(List<MenuLevel> chilren) {

        this.chilren = chilren;
    }

    /**
     * @return get method for the field nodeName
     */
    public String getNodeName() {

        return this.nodeName;
    }

    /**
     * @param nodeName
     *            the nodeName to set
     */
    public void setNodeName(String nodeName) {

        this.nodeName = nodeName;
    }

    /**
     * @param menuElement
     *            the menuElement to set
     */
    public void setMenuElement(MenuElement menuElement) {

        this.menuElement = menuElement;
    }

    /**
     * @return get method for the field menuLevel
     */
    public boolean isMenuLevel() {

        return this.menuLevel;
    }

    /**
     * @param menuLevel
     *            the menuLevel to set
     */
    public void setMenuLevel(boolean menuLevel) {

        this.menuLevel = menuLevel;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {

        this.name = name;
    }

    /**
     * @param parent
     *            the parent to set
     */
    public void setParent(MenuLevel parent) {

        this.parent = parent;
    }

    @Override
    public String toString() {

        return new StringBuffer().append("JMenuLevel:[name=").append(this.name).append(
                ", identifier=").append(this.identifier).append("]").toString();
    }
}
