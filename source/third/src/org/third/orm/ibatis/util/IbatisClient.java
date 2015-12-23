/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file org.ibatis.util.IbatisClient.java
 * is created on 2008-6-18
 */
package org.third.orm.ibatis.util;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

public class IbatisClient {

    private static IbatisClient instance;
    private SqlMapClient sqlMapperClient;

    private IbatisClient() {

        init();

    }

    private void init() {

        try {

            Reader reader = com.ibatis.common.resources.Resources.getResourceAsReader("sql-map-config.xml");
            sqlMapperClient = SqlMapClientBuilder.buildSqlMapClient(reader);

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            // Fail fast.
            throw new RuntimeException("Something bad happened while building the SqlMapClient instance." + e, e);
        }

    }

    public synchronized final static IbatisClient getInstance() {

        if (instance == null) {
            instance = new IbatisClient();
        }
        return instance;
    }

    public SqlMapClient getSqlMapClient() {

        return sqlMapperClient;
    }

    public static void main(String[] args) {

        SqlMapClient client = IbatisClient.getInstance().getSqlMapClient();
    }

}
