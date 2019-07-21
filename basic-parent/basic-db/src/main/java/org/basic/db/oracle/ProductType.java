package org.basic.db.oracle;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

public class ProductType implements SQLData {

    public String getSQLTypeName() throws SQLException {
        return "TYPE_PRODUCT";
    }

    public void readSQL(SQLInput stream, String typeName) throws SQLException {
       System.out.println("23"); 
    }

    public void writeSQL(SQLOutput stream) throws SQLException {
        System.out.println("2jkl3");
    }

}
