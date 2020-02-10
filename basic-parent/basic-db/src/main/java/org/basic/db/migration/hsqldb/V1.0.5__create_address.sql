--created_date date default current_date
CREATE TABLE DM_ADDRESS (
  ID INTEGER NOT NULL IDENTITY,
  COUNTRY VARCHAR(32),
  CITY VARCHAR(36),
  VERSION INTEGER DEFAULT 0,
  MODIFIED_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CREATED_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT CRT_DM_ADDRESS_PK PRIMARY KEY (ID)
);

INSERT INTO DM_ADDRESS (id,country,city) VALUES (1, 'China', 'Shanghai');
INSERT INTO DM_ADDRESS (id,country,city) VALUES (2, 'China', 'Beijin');
INSERT INTO DM_ADDRESS (id,country,city) VALUES (3, 'China', 'Tianshui');
INSERT INTO DM_ADDRESS (id,country,city) VALUES (4, 'USA', 'NewYork');
INSERT INTO DM_ADDRESS (country,city) VALUES ('USA', 'Washington');
