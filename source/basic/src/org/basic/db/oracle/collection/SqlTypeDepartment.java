/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file
 * org.basic.db.oracle.SqlTypeDepartment.java is created on 2008-10-14
 */
package org.basic.db.oracle.collection;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

/**
 * <pre>
 * create or replace
 *   TYPE type_department IS OBJECT(
 *      dep_id VARCHAR2(20),
 *      dep_name VARCHAR2(200)
 * );
 * </pre>
 */
public class SqlTypeDepartment implements SQLData {

    private String depId;
    private String depName;
    public SqlTypeDepartment(){
        
    }
    public SqlTypeDepartment(String dep_id, String dep_name) {

        this.depId = dep_id;
        this.depName = dep_name;
    }

    public String getSQLTypeName() throws SQLException {

        //
        return "TYPE_DEPARTMENT";
    }

    public void readSQL(SQLInput stream, String typeName) throws SQLException {

        this.depId = stream.readString();
        this.depName = stream.readString();

    }

    public void writeSQL(SQLOutput stream) throws SQLException {

        stream.writeString(this.depId);
        stream.writeString(this.depName);

    }

    /**
     * @return get method for the field depId
     */
    public String getDepId() {

        return this.depId;
    }

    /**
     * @param depId
     *            the depId to set
     */
    public void setDepId(String depId) {

        this.depId = depId;
    }

    /**
     * @return get method for the field depName
     */
    public String getDepName() {

        return this.depName;
    }

    /**
     * @param depName
     *            the depName to set
     */
    public void setDepName(String depName) {

        this.depName = depName;
    }
}