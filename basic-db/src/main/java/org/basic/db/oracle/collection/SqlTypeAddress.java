/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file org.basic.db.oracle.Address.java
 * is created on 2008-3-20
 */
package org.basic.db.oracle.collection;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

/**
 * 
 */
public class SqlTypeAddress implements SQLData {

    private long addressID;
    private String addressName;

    public SqlTypeAddress() {

    }

    private String sqlType = "Address";
    public String getSQLTypeName() throws SQLException {

        return this.sqlType;
    }

    public void readSQL(SQLInput stream, String typeName) throws SQLException {

        this.addressID = stream.readLong();
        this.addressName = stream.readString();

        this.sqlType = typeName;
    }

    public void writeSQL(SQLOutput stream) throws SQLException {

        stream.writeLong(this.addressID);
        stream.writeString(this.addressName);
    }

    @Override
    public String toString() {

        return new StringBuffer(getClass().getSimpleName()).append(":[addressId=").append(
                this.addressID).append(", addressName=").append(this.addressName).toString();
    }
}
