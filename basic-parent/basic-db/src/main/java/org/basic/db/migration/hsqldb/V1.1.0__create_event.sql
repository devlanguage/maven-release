CREATE table dm_event (
  id INTEGER  identity,
  name VARCHAR(64),
  title VARCHAR(64),
  source VARCHAR(64),
  VERSION INTEGER DEFAULT 0,
  MODIFIED_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CREATED_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT CRT_DM_EVENT_PK PRIMARY KEY (ID)

);
--CREATE UNIQUE INDEX IDX_USER_PK ON User(ID);

INSERT INTO dm_event(ID, NAME, title, source, VERSION, MODIFIED_DATE, CREATED_DATE)
   VALUES(1, 'PC-001', 'Memory Not Enough', 'Hardware', 0, '2020-01-02 21:03:01.871', '2020-01-02 21:03:01.871');
INSERT INTO dm_event(ID, NAME, title, source, VERSION, MODIFIED_DATE, CREATED_DATE)
   VALUES(2, 'PC-002','Disk Not Enough', 'Hardware', 0, '2020-01-02 21:03:01.871', '2020-01-02 21:03:01.871');
INSERT INTO dm_event(ID, NAME, title, source, VERSION, MODIFIED_DATE, CREATED_DATE)
   VALUES(3, 'PC-003','CPU Not Enough', 'Hardware', 0, '2020-01-02 21:03:01.871', '2020-01-02 21:03:01.871');
