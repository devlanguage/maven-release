DROP TABLE item IF EXISTS;
DROP TABLE PRODUCT IF EXISTS;
DROP TABLE INVOICE IF EXISTS;
DROP TABLE customer IF EXISTS;
--CREATE MEMORY TABLE PUBLIC."flyway_schema_history"("installed_rank" INTEGER NOT NULL,"version" VARCHAR(50),"description" VARCHAR(200) NOT NULL,"type" VARCHAR(20) NOT NULL,"script" VARCHAR(1000) NOT NULL,"checksum" INTEGER,"installed_by" VARCHAR(100) NOT NULL,"installed_on" TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,"execution_time" INTEGER NOT NULL,"success" BIT(1) NOT NULL,CONSTRAINT "flyway_schema_history_pk" PRIMARY KEY("installed_rank"))
