CREATE TABLE TEST_ATTEMPT
(
    id            SERIAL  NOT NULL PRIMARY KEY,
    number_attempt INTEGER NOT NULL,
    test_case     INTEGER,
    passed        INTEGER NOT NULL,
    fail          INTEGER NOT NULL,
    user_id       INTEGER
);


