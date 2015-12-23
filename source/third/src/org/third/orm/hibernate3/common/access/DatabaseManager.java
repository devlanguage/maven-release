package org.third.orm.hibernate3.common.access;

import org.third.orm.hibernate3.common.bean.Database;
import org.third.orm.hibernate3.common.bean.DatabaseType;
import org.third.orm.hibernate3.common.bean.OracleDatabase;

public class DatabaseManager {

    private final static DatabaseManager instance = new DatabaseManager();

    public final static DatabaseManager getInstance() {

        return instance;
    }

    public final static Database getDatabase(DatabaseType dbType) {

        Database database = null;
        switch (dbType) {
            case ORACLE:
                database = new OracleDatabase();
                break;

        }

        return database;
    }

}
