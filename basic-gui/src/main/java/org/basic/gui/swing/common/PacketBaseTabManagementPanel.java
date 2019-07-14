package org.basic.gui.swing.common;


/**
 * <pre>
 *
 * </pre>
 */
public class PacketBaseTabManagementPanel extends PacketBasePanel {

    public PacketBaseTabManagementPanel(EmsBaseMObject baseMOClass) {
        super(baseMOClass);
    }

    public PacketBaseTabManagementPanel() {
    }

    public boolean populateData(Object[] obj) {
        return true;
    }

    /**
     * Populate the table from the server
     *
     * @param commandResultAny
     *            Any
     */
    public boolean populateData(Object commandResultAny) {
        return true;
    }

}