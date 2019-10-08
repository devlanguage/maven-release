package org.basic.gui.swing.sort.Jdk6_SortTableTest;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.beans.ConstructorProperties;
import java.io.Serializable;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.tree.TreeCellEditor;

/**
 * <pre>
 *
 * </pre>
 */
public class BaseCellEditor extends AbstractCellEditor implements TableCellEditor, TreeCellEditor {

    //
    // Instance Variables
    //

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /** The Swing component being edited. */
    protected JComponent editorComponent;
    /**
     * The delegate class which handles all methods sent from the <code>CellEditor</code>.
     */
    protected EditorDelegate delegate;
    /**
     * An integer specifying the number of clicks needed to start editing. Even if <code>clickCountToStart</code> is
     * defined as zero, it will not initiate until a click occurs.
     */
    protected int clickCountToStart = 1;

    public BaseCellEditor(final JComponent comp) {
        editorComponent = comp;
        if (comp instanceof JTextField) {
            this.clickCountToStart = 2;
            JTextField textField = (JTextField) comp;
            delegate = new EditorDelegate() {
                public void setValue(Object value) {
                    textField.setText((value != null) ? value.toString() : "");
                }

                public Object getCellEditorValue() {
                    return textField.getText();
                }
            };
            textField.addActionListener(delegate);
        } else if (comp instanceof JCheckBox) {
            JCheckBox checkBox = (JCheckBox) comp;
            delegate = new EditorDelegate() {
                private static final long serialVersionUID = -1335919806720508338L;

                public void setValue(Object value) {
                    boolean selected = false;
                    if (value instanceof Boolean) {
                        selected = ((Boolean) value).booleanValue();
                    } else if (value instanceof String) {
                        selected = value.equals("true");
                    }
                    checkBox.setSelected(selected);
                }

                public Object getCellEditorValue() {
                    return Boolean.valueOf(checkBox.isSelected());
                }
            };
            checkBox.addActionListener(delegate);
            checkBox.setRequestFocusEnabled(false);
        } else if (comp instanceof JComboBox) {
            JComboBox comboBox = (JComboBox) comp;
            comboBox.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);
            delegate = new EditorDelegate() {
                public void setValue(Object value) {
                    comboBox.setSelectedItem(value);
                }

                public Object getCellEditorValue() {
                    return comboBox.getSelectedItem();
                }

                public boolean shouldSelectCell(EventObject anEvent) {
                    if (anEvent instanceof MouseEvent) {
                        MouseEvent e = (MouseEvent) anEvent;
                        return e.getID() != MouseEvent.MOUSE_DRAGGED;
                    }
                    return true;
                }

                public boolean stopCellEditing() {
                    if (comboBox.isEditable()) {
                        // Commit edited value.
                        comboBox.actionPerformed(new ActionEvent(BaseCellEditor.this, 0, ""));
                    }
                    return super.stopCellEditing();
                }
            };
            comboBox.addActionListener(delegate);
        }
    }

    //
    // Constructors
    //
    @ConstructorProperties({ "component" })
    public BaseCellEditor(final JTextArea textField) {
        editorComponent = textField;
        this.clickCountToStart = 2;
        delegate = new EditorDelegate() {
            public void setValue(Object value) {
                textField.setText((value != null) ? value.toString() : "");
            }

            public Object getCellEditorValue() {
                return textField.getText();
            }
        };
        // textField.addActionListener(delegate);
    }

    /**
     * Constructs a <code>DefaultCellEditor</code> that uses a text field.
     *
     * @param textField
     *            a <code>JTextField</code> object
     */
    @ConstructorProperties({ "component" })
    public BaseCellEditor(final JTextField textField) {
        editorComponent = textField;
        this.clickCountToStart = 2;
        delegate = new EditorDelegate() {
            public void setValue(Object value) {
                textField.setText((value != null) ? value.toString() : "");
            }

            public Object getCellEditorValue() {
                return textField.getText();
            }
        };
        textField.addActionListener(delegate);
    }

    /**
     * Constructs a <code>DefaultCellEditor</code> object that uses a check box.
     *
     * @param checkBox
     *            a <code>JCheckBox</code> object
     */
    public BaseCellEditor(final JCheckBox checkBox) {
        editorComponent = checkBox;
        delegate = new EditorDelegate() {
            public void setValue(Object value) {
                boolean selected = false;
                if (value instanceof Boolean) {
                    selected = ((Boolean) value).booleanValue();
                } else if (value instanceof String) {
                    selected = value.equals("true");
                }
                checkBox.setSelected(selected);
            }

            public Object getCellEditorValue() {
                return Boolean.valueOf(checkBox.isSelected());
            }
        };
        checkBox.addActionListener(delegate);
        checkBox.setRequestFocusEnabled(false);
    }

    /**
     * Constructs a <code>DefaultCellEditor</code> object that uses a combo box.
     *
     * @param comboBox
     *            a <code>JComboBox</code> object
     */
    public BaseCellEditor(final JComboBox comboBox) {
        editorComponent = comboBox;
        comboBox.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);
        delegate = new EditorDelegate() {
            private static final long serialVersionUID = 7497540379114209349L;

            public void setValue(Object value) {
                comboBox.setSelectedItem(value);
            }

            public Object getCellEditorValue() {
                return comboBox.getSelectedItem();
            }

            public boolean shouldSelectCell(EventObject anEvent) {
                if (anEvent instanceof MouseEvent) {
                    MouseEvent e = (MouseEvent) anEvent;
                    return e.getID() != MouseEvent.MOUSE_DRAGGED;
                }
                return true;
            }

            public boolean stopCellEditing() {
                if (comboBox.isEditable()) {
                    // Commit edited value.
                    comboBox.actionPerformed(new ActionEvent(BaseCellEditor.this, 0, ""));
                }
                return super.stopCellEditing();
            }
        };
        comboBox.addActionListener(delegate);
    }

    /**
     * Returns a reference to the editor component.
     *
     * @return the editor <code>Component</code>
     */
    public Component getComponent() {
        return editorComponent;
    }

    //
    // Modifying
    //

    /**
     * Specifies the number of clicks needed to start editing.
     *
     * @param count
     *            an int specifying the number of clicks needed to start editing
     * @see #getClickCountToStart
     */
    public void setClickCountToStart(int count) {
        clickCountToStart = count;
    }

    /**
     * Returns the number of clicks needed to start editing.
     * 
     * @return the number of clicks needed to start editing
     */
    public int getClickCountToStart() {
        return clickCountToStart;
    }

    //
    // Override the implementations of the superclass, forwarding all methods
    // from the CellEditor interface to our delegate.
    //

    /**
     * Forwards the message from the <code>CellEditor</code> to the <code>delegate</code>.
     * 
     * @see EditorDelegate#getCellEditorValue
     */
    public Object getCellEditorValue() {
        return delegate.getCellEditorValue();
    }

    /**
     * Forwards the message from the <code>CellEditor</code> to the <code>delegate</code>.
     * 
     * @see EditorDelegate#isCellEditable(EventObject)
     */
    public boolean isCellEditable(EventObject anEvent) {
        return delegate.isCellEditable(anEvent);
    }

    /**
     * Forwards the message from the <code>CellEditor</code> to the <code>delegate</code>.
     * 
     * @see EditorDelegate#shouldSelectCell(EventObject)
     */
    public boolean shouldSelectCell(EventObject anEvent) {
        return delegate.shouldSelectCell(anEvent);
    }

    /**
     * Forwards the message from the <code>CellEditor</code> to the <code>delegate</code>.
     * 
     * @see EditorDelegate#stopCellEditing
     */
    public boolean stopCellEditing() {
        return delegate.stopCellEditing();
    }

    /**
     * Forwards the message from the <code>CellEditor</code> to the <code>delegate</code>.
     * 
     * @see EditorDelegate#cancelCellEditing
     */
    public void cancelCellEditing() {
        delegate.cancelCellEditing();
    }

    //
    // Implementing the TreeCellEditor Interface
    //

    /** Implements the <code>TreeCellEditor</code> interface. */
    public Component getTreeCellEditorComponent(JTree tree, Object value, boolean isSelected, boolean expanded, boolean leaf, int row) {
        String stringValue = tree.convertValueToText(value, isSelected, expanded, leaf, row, false);

        delegate.setValue(stringValue);
        return editorComponent;
    }

    //
    // Implementing the CellEditor Interface
    //
    /** Implements the <code>TableCellEditor</code> interface. */
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        delegate.setValue(value);
        if (editorComponent instanceof JCheckBox) {
            // in order to avoid a "flashing" effect when clicking a checkbox
            // in a table, it is important for the editor to have as a border
            // the same border that the renderer has, and have as the background
            // the same color as the renderer has. This is primarily only
            // needed for JCheckBox since this editor doesn't fill all the
            // visual space of the table cell, unlike a text field.
            TableCellRenderer renderer = table.getCellRenderer(row, column);
            Component c = renderer.getTableCellRendererComponent(table, value, isSelected, true, row, column);
            if (c != null) {
                editorComponent.setOpaque(true);
                editorComponent.setBackground(c.getBackground());
                if (c instanceof JComponent) {
                    editorComponent.setBorder(((JComponent) c).getBorder());
                }
            } else {
                editorComponent.setOpaque(false);
            }
        }
        return editorComponent;
    }

    //
    // Protected EditorDelegate class
    //

    /**
     * The protected <code>EditorDelegate</code> class.
     */
    protected class EditorDelegate implements ActionListener, ItemListener, Serializable {
        private static final long serialVersionUID = -7032368981224396220L;
        /** The value of this cell. */
        protected Object value;

        /**
         * Returns the value of this cell.
         * 
         * @return the value of this cell
         */
        public Object getCellEditorValue() {
            return value;
        }

        /**
         * Sets the value of this cell.
         * 
         * @param value
         *            the new value of this cell
         */
        public void setValue(Object value) {
            this.value = value;
        }

        /**
         * Returns true if <code>anEvent</code> is <b>not</b> a <code>MouseEvent</code>. Otherwise, it returns true if
         * the necessary number of clicks have occurred, and returns false otherwise.
         *
         * @param anEvent
         *            the event
         * @return true if cell is ready for editing, false otherwise
         * @see #setClickCountToStart
         * @see #shouldSelectCell
         */
        public boolean isCellEditable(EventObject anEvent) {
            if (anEvent instanceof MouseEvent) {
                return ((MouseEvent) anEvent).getClickCount() >= clickCountToStart;
            }
            return true;
        }

        /**
         * Returns true to indicate that the editing cell may be selected.
         *
         * @param anEvent
         *            the event
         * @return true
         * @see #isCellEditable
         */
        public boolean shouldSelectCell(EventObject anEvent) {
            return true;
        }

        /**
         * Returns true to indicate that editing has begun.
         *
         * @param anEvent
         *            the event
         */
        public boolean startCellEditing(EventObject anEvent) {
            return true;
        }

        /**
         * Stops editing and returns true to indicate that editing has stopped. This method calls
         * <code>fireEditingStopped</code>.
         *
         * @return true
         */
        public boolean stopCellEditing() {
            fireEditingStopped();
            return true;
        }

        /**
         * Cancels editing. This method calls <code>fireEditingCanceled</code>.
         */
        public void cancelCellEditing() {
            fireEditingCanceled();
        }

        /**
         * When an action is performed, editing is ended.
         * 
         * @param e
         *            the action event
         * @see #stopCellEditing
         */
        public void actionPerformed(ActionEvent e) {
            BaseCellEditor.this.stopCellEditing();
        }

        /**
         * When an item's state changes, editing is ended.
         * 
         * @param e
         *            the action event
         * @see #stopCellEditing
         */
        public void itemStateChanged(ItemEvent e) {
            BaseCellEditor.this.stopCellEditing();
        }
    }

} // End of class JCellEditor