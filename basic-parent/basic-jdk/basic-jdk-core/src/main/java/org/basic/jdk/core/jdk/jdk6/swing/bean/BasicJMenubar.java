package org.basic.jdk.core.jdk.jdk6.swing.bean;

import java.io.InputStream;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.MenuElement;

import org.basic.common.bean.CommonConstants;
import org.basic.common.util.BasicException;
import org.basic.common.util.XmlUtils;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * 
 */
public class BasicJMenubar extends JMenuBar {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 123423423423412356L;

    public static final String ELEMENT_MENUS = "menus";
    public static final String ELEMENT_HIERARCHY = "hierarchy";
    public static final String ELEMENT_PREFIX_LEVEL = "level";
    public static final String ATTR_NAME = "name";
    public static final String ATTR_IS_MENU_LEVEL = "isMenuLevel";
    public static final String ATTR_ATTACH_DELIMITER = "attachDelimiter";
    private static final String ATTR_IDENTIFIER = "identifier";

    private Map<String, MenuLevel> menuItemMap = new HashMap<String, MenuLevel>();

    public void buildMenuBar(InputStream menuDataStream) {
        this.add(PersonTable.getRightKeyMenu());
        try {

            Element jMenuBar = XmlUtils.getRootNode(menuDataStream);
            int currentLevel = 1;
            NodeList jMenuLevelNodes = XmlUtils.getNodeList(XmlUtils.getNode(
                    jMenuBar, ELEMENT_HIERARCHY), ELEMENT_PREFIX_LEVEL
                    + currentLevel);

            for (int i = 0; i < jMenuLevelNodes.getLength(); i++) {

                Element menuLevelElement = (Element) jMenuLevelNodes.item(i);
                MenuLevel menuLevel = parseMenuLevel(menuLevelElement);
                menuItemMap.put(menuLevel.getIdentifier(), menuLevel);

                // first level is JMenu
                if (menuLevel.isMenuLevel()) {
                    JMenu jMenu = new JMenu(menuLevel.getName());
                    menuLevel.setMenuElement(jMenu);
                    this.add(jMenu);
                    buildMenus(jMenu, menuLevelElement, menuLevel, currentLevel);
                }
            }

        } catch (BasicException e) {
        }
    }

    private void buildMenus(MenuElement parentMenu,
            Element parentMenuLevelElement, MenuLevel parentMenuLevel,
            int parentCurrentLevel) {

        if (!parentMenuLevel.isMenuLevel()) {
            return;
        }
        NodeList jMenuNodes = XmlUtils.getNodeList(parentMenuLevelElement,
                ELEMENT_PREFIX_LEVEL + (++parentCurrentLevel));
        for (int i = 0; i < jMenuNodes.getLength(); i++) {

            Element menuLevelElement = (Element) jMenuNodes.item(i);
            MenuLevel menuLevel = parseMenuLevel(menuLevelElement);
            menuItemMap.put(menuLevel.getIdentifier(), menuLevel);
            parentMenuLevel.addChild(menuLevel);
            if (menuLevel.isMenuLevel()) {
                JMenu currentJMenu = new JMenu(menuLevel.getName());
                menuLevel.setMenuElement(currentJMenu);
                JMenu parentJMenu = (JMenu) parentMenu;
                parentJMenu.add(currentJMenu);

                buildMenus(currentJMenu, menuLevelElement, menuLevel,
                        parentCurrentLevel);
            } else {
                JMenu jMenu = (JMenu) parentMenu;
                JMenuItem jMenuItem = new JMenuItem(menuLevel.getName());
                menuLevel.setMenuElement(jMenuItem);
                if (menuLevel.isAttachDelimiter()) {
                    jMenu.addSeparator();
                }
                jMenu.add(jMenuItem);
            }
        }

    }

    private final static MenuLevel parseMenuLevel(Element menuLevelElement) {

        MenuLevel menuLevel = new MenuLevel();

        menuLevel.setName(menuLevelElement.getAttribute(ATTR_NAME));
        menuLevel.setNodeName(menuLevelElement.getNodeName());

        menuLevel.setMenuLevel(Boolean.parseBoolean(menuLevelElement
                .getAttribute(ATTR_IS_MENU_LEVEL)));
        menuLevel.setAttachDelimiter(Boolean.parseBoolean(menuLevelElement
                .getAttribute(ATTR_ATTACH_DELIMITER)));

        menuLevel.setIdentifier(menuLevelElement.getAttribute(ATTR_IDENTIFIER));

        return menuLevel;
    }

    public BasicJMenubar() {

        buildMenuBar(BasicJMenubar.class
                .getResourceAsStream(CommonConstants.MENU_DATA_FILE));

    }

    public MenuLevel getMenuLevel(String identifier) throws BasicException {

        if (this.getMenuItemMap().get(identifier) == null) {

            throw new BasicException(
                    "Can not found the particular element by the identifier "
                            + identifier);
        }
        return this.getMenuItemMap().get(identifier);
    }

    public MenuElement getMenuElement(String identifier) throws BasicException {

        return this.getMenuLevel(identifier).getMenuElement();

    }

    public void AddListener(String identifer, EventListener listener) {

    }

    /**
     * @return get method for the field menuItemMap
     */
    public Map<String, MenuLevel> getMenuItemMap() {

        return this.menuItemMap;
    }

    /**
     * @param menuItemMap
     *            the menuItemMap to set
     */
    public void setMenuItemMap(Map<String, MenuLevel> menuItemMap) {

        this.menuItemMap = menuItemMap;
    }
}
