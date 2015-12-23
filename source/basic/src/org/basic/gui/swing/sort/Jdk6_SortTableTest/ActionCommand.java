package org.basic.gui.swing.sort.Jdk6_SortTableTest;

public enum ActionCommand {
    Add, Refresh, Delete, Cancel, OK;
    String getActionName() {
        return this.name();
    }
}
