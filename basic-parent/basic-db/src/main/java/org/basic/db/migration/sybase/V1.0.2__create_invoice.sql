DROP TABLE INVOICE IF EXISTS;
CREATE TABLE INVOICE (
  ID INTEGER NOT NULL,
  CUSTOMERID INTEGER,
  TOTAL DECIMAL(10,2),
  CONSTRAINT SYS_PK_10100 PRIMARY KEY (ID),
  CONSTRAINT SYS_FK_10101 FOREIGN KEY (CUSTOMERID) REFERENCES CUSTOMER(ID) ON DELETE CASCADE
);
CREATE INDEX SYS_IDX_SYS_FK_10101_10103 ON INVOICE (CUSTOMERID);
CREATE UNIQUE INDEX SYS_IDX_SYS_PK_10100_10102 ON INVOICE (ID);

INSERT INTO INVOICE (ID,CUSTOMERID,TOTAL) VALUES (
0,10,1806.60);
INSERT INTO INVOICE (ID,CUSTOMERID,TOTAL) VALUES (
1,48,2463.60);
INSERT INTO INVOICE (ID,CUSTOMERID,TOTAL) VALUES (
2,23,4032.90);
INSERT INTO INVOICE (ID,CUSTOMERID,TOTAL) VALUES (
3,25,5136.00);
INSERT INTO INVOICE (ID,CUSTOMERID,TOTAL) VALUES (
4,24,5139.90);
INSERT INTO INVOICE (ID,CUSTOMERID,TOTAL) VALUES (
5,1,938.70);
INSERT INTO INVOICE (ID,CUSTOMERID,TOTAL) VALUES (
6,13,3336.30);
INSERT INTO INVOICE (ID,CUSTOMERID,TOTAL) VALUES (
7,39,3071.70);
INSERT INTO INVOICE (ID,CUSTOMERID,TOTAL) VALUES (
8,30,2773.20);
INSERT INTO INVOICE (ID,CUSTOMERID,TOTAL) VALUES (
9,3,1680.00);
INSERT INTO INVOICE (ID,CUSTOMERID,TOTAL) VALUES (
10,36,1501.20);
INSERT INTO INVOICE (ID,CUSTOMERID,TOTAL) VALUES (
11,12,3939.60);
INSERT INTO INVOICE (ID,CUSTOMERID,TOTAL) VALUES (
12,14,1866.00);
INSERT INTO INVOICE (ID,CUSTOMERID,TOTAL) VALUES (
13,6,2945.40);
INSERT INTO INVOICE (ID,CUSTOMERID,TOTAL) VALUES (
14,7,4036.50);
INSERT INTO INVOICE (ID,CUSTOMERID,TOTAL) VALUES (
15,1,894.00);
INSERT INTO INVOICE (ID,CUSTOMERID,TOTAL) VALUES (
16,13,1345.50);
INSERT INTO INVOICE (ID,CUSTOMERID,TOTAL) VALUES (
17,23,4169.10);
INSERT INTO INVOICE (ID,CUSTOMERID,TOTAL) VALUES (
18,2,3463.20);
INSERT INTO INVOICE (ID,CUSTOMERID,TOTAL) VALUES (
19,43,940.80);
INSERT INTO INVOICE (ID,CUSTOMERID,TOTAL) VALUES (
20,46,914.40);
INSERT INTO INVOICE (ID,CUSTOMERID,TOTAL) VALUES (
21,20,587.40);
INSERT INTO INVOICE (ID,CUSTOMERID,TOTAL) VALUES (
22,11,4045.20);
INSERT INTO INVOICE (ID,CUSTOMERID,TOTAL) VALUES (
23,20,2541.90);
INSERT INTO INVOICE (ID,CUSTOMERID,TOTAL) VALUES (
24,12,5373.60);
INSERT INTO INVOICE (ID,CUSTOMERID,TOTAL) VALUES (
25,14,702.60);
INSERT INTO INVOICE (ID,CUSTOMERID,TOTAL) VALUES (
26,19,4320.30);
INSERT INTO INVOICE (ID,CUSTOMERID,TOTAL) VALUES (
27,39,1203.00);
INSERT INTO INVOICE (ID,CUSTOMERID,TOTAL) VALUES (
28,16,5108.70);
INSERT INTO INVOICE (ID,CUSTOMERID,TOTAL) VALUES (
29,31,2573.40);
INSERT INTO INVOICE (ID,CUSTOMERID,TOTAL) VALUES (
30,42,3347.40);
INSERT INTO INVOICE (ID,CUSTOMERID,TOTAL) VALUES (
31,46,3705.00);
INSERT INTO INVOICE (ID,CUSTOMERID,TOTAL) VALUES (
32,29,2743.50);
INSERT INTO INVOICE (ID,CUSTOMERID,TOTAL) VALUES (
33,5,3966.90);
INSERT INTO INVOICE (ID,CUSTOMERID,TOTAL) VALUES (
34,7,2458.50);
INSERT INTO INVOICE (ID,CUSTOMERID,TOTAL) VALUES (
35,3,1426.20);
INSERT INTO INVOICE (ID,CUSTOMERID,TOTAL) VALUES (
36,36,3631.50);
INSERT INTO INVOICE (ID,CUSTOMERID,TOTAL) VALUES (
37,40,6675.00);
INSERT INTO INVOICE (ID,CUSTOMERID,TOTAL) VALUES (
38,33,2583.90);
INSERT INTO INVOICE (ID,CUSTOMERID,TOTAL) VALUES (
39,47,3355.20);
INSERT INTO INVOICE (ID,CUSTOMERID,TOTAL) VALUES (
40,13,5538.30);
INSERT INTO INVOICE (ID,CUSTOMERID,TOTAL) VALUES (
41,43,2333.70);
INSERT INTO INVOICE (ID,CUSTOMERID,TOTAL) VALUES (
42,13,2954.10);
INSERT INTO INVOICE (ID,CUSTOMERID,TOTAL) VALUES (
43,14,7737.90);
INSERT INTO INVOICE (ID,CUSTOMERID,TOTAL) VALUES (
44,27,4673.40);
INSERT INTO INVOICE (ID,CUSTOMERID,TOTAL) VALUES (
45,26,1686.60);
INSERT INTO INVOICE (ID,CUSTOMERID,TOTAL) VALUES (
46,30,2652.30);
INSERT INTO INVOICE (ID,CUSTOMERID,TOTAL) VALUES (
47,32,1569.60);
INSERT INTO INVOICE (ID,CUSTOMERID,TOTAL) VALUES (
48,14,5783.40);
INSERT INTO INVOICE (ID,CUSTOMERID,TOTAL) VALUES (
49,0,3583.50);
