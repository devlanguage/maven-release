package org.basic.gui.swing.sort.Jdk6_SortTableTest;

import javax.swing.Icon;
import javax.swing.JButton;

public class BaseButton extends JButton {

    public BaseButton(ActionCommand action, Icon icon) {
        super(action.getActionName(), icon);
    }

    public BaseButton(ActionCommand action) {
        super(action.getActionName(), null);
    }

}
