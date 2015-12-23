package org.third.testdata.user.domain;

import java.math.BigInteger;

public class HashCodeTest {

    private boolean boolean1;
    private byte byte1;
//    private char char1;
//    private short short1;
//    private int int1;
//    private long long1;
//    private float float1;
//    private double double1;
//    private String string1;
//    private BigInteger bigInteger1;
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {

        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + (boolean1 ? 1231 : 1237);
        return result;
    }
    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final HashCodeTest other = (HashCodeTest) obj;
        if (boolean1 != other.boolean1)
            return false;
        if (byte1 != other.byte1)
            return false;
        return true;
    }

}
