package org.basic.gui.swing.component;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * <pre>
 *
 * </pre>
 */
public class NumericRangeDocument extends PlainDocument {
    private static final long serialVersionUID = -8665249724576598559L;
    private int iLowerBound = 0;
    private int iUpperBound = 0;
    private int iMaxFracLenght = 0;

    private boolean _useDecimal = true;
    private boolean _acceptNegativeValues = true;

    /**
     *
     * @param lowerBound
     * @param upperBound
     * @param fracLen
     */
    public NumericRangeDocument(int lowerBound, int upperBound, int fracLen) {
        this(lowerBound, upperBound, fracLen, true, true);
    }

    /**
     * Use this constructor if you want to restrict the decimal or negative values from the document
     * 
     * @param lowerBound
     *            int
     * @param upperBound
     *            int
     * @param fracLen
     *            int
     * @param useDecimal
     *            boolean return true if deciaml point is desired
     * @param acceptNegativeValues
     *            boolean return true if a negative number is desired
     */
    public NumericRangeDocument(int lowerBound, int upperBound, int fracLen, boolean useDecimal, boolean acceptNegativeValues) {
        iLowerBound = lowerBound;
        iUpperBound = upperBound;
        iMaxFracLenght = fracLen;

        _useDecimal = useDecimal;
        _acceptNegativeValues = acceptNegativeValues;
    }

    /**
     * @see javax.swing.text.Document#insertString(int, java.lang.String, javax.swing.text.AttributeSet)
     */
    public void insertString(int offset, String str, AttributeSet a) throws BadLocationException {

        // this works by insering the string, testing the value
        // and putting back the old one if it's too large or isn't valid

        String strOldValue = getText(0, getLength());
        super.insertString(offset, str, a);
        String strNewValue = getText(0, getLength());

        char cInput = str.charAt(0);
        if (Character.isDigit(cInput)) {
            double i = Double.parseDouble(strNewValue);
            if (i > iUpperBound || i < iLowerBound) {
                undo(strOldValue, a);
            }

            if (isFracOutOfBounds(strNewValue)) {
                undo(strOldValue, a);
            }
        } else if (_useDecimal && cInput == '.') {
            // make sure there is only ever one '.'
            if (strOldValue.contains(".")) {
                undo(strOldValue, a);
            }

            if (isFracOutOfBounds(strNewValue)) {
                undo(strOldValue, a);
            }
        } else if (_acceptNegativeValues && cInput == '-') {
            // make sure there is only ever one '-'
            if (strOldValue.contains("-")) {
                undo(strOldValue, a);
            }

            // make sure that the '-' can only be in the first position
            if (strNewValue.indexOf('-') != 0) {
                undo(strOldValue, a);
            }
        } else {
            undo(strOldValue, a);
        }
    }

    /**
     * resets to the old value
     */
    private void undo(String oldValue, AttributeSet a) throws BadLocationException {
        super.remove(0, getLength());
        super.insertString(0, oldValue, a);
    }

    /**
     * @see javax.swing.text.Document#remove(int, int)
     */
    public void remove(int offs, int len) throws BadLocationException {
        String strOldValue = getText(0, getLength());
        super.remove(offs, len);
        String strNewValue = getText(0, getLength());
        if (strNewValue == null || strNewValue.length() == 0) {
            return;
        }
        // ignore a single negitive sign
        if (!strNewValue.equals("-")) {
            double d = Double.parseDouble(strNewValue);
            if (d > iUpperBound || d < iLowerBound) {
                undo(strOldValue, null);
            }
        }
    }

    /**
     * @param strNewValue
     * @return - true if the string exceeds the maxumum fraction length
     */
    private boolean isFracOutOfBounds(String strNewValue) {
        int iFracPos = strNewValue.indexOf(".");
        if (iFracPos != -1) {
            int ifracLen = strNewValue.substring(iFracPos + 1).length();
            if (ifracLen > iMaxFracLenght) {
                return true;
            }
        }
        return false;
    }
}