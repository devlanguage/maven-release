CREATE table dm_person_event (
    person_id INTEGER,
    event_id INTEGER
);
ALTER TABLE dm_person_event ADD CONSTRAINT dm_person_event_pk PRIMARY KEY (person_id, event_id);
--ALTER TABLE dm_person_event ADD CONSTRAINT dm_fk_event_id FOREIGN KEY (event_id) REFERENCES dm_event(ID) ON DELETE CASCADE
--ALTER TABLE dm_person_event ADD CONSTRAINT dm_fk_person_id FOREIGN KEY (person_id) REFERENCES dm_person(ID) ON DELETE CASCADE


--CREATE UNIQUE INDEX IDX_USER_PK ON User(ID);

INSERT INTO dm_person_event(person_id, event_id) VALUES(1, 1);
INSERT INTO dm_person_event(person_id, event_id) VALUES(1, 2);
INSERT INTO dm_person_event(person_id, event_id) VALUES(1, 3);
