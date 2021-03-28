CREATE TABLE MANAGER
(
  ID         VARCHAR2(100 CHAR),
  HEART_BEAT TIMESTAMP NOT NULL,
  CONSTRAINT MANAGER_PK PRIMARY KEY (ID)
);

CREATE TABLE JOB
(
  ID         VARCHAR2(100 CHAR),
  NAME       VARCHAR2(100 CHAR) NOT NULL,
  DATA       CLOB               NOT NULL,
  MANAGER_ID VARCHAR2(100 CHAR),
  CONSTRAINT JOB_PK PRIMARY KEY (ID),
  CONSTRAINT JOB_FK_MANAGER
    FOREIGN KEY (MANAGER_ID) REFERENCES MANAGER (ID)
);
