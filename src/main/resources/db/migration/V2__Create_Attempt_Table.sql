CREATE TABLE TEST_ATTEMPT
(
    id            SERIAL  NOT NULL PRIMARY KEY,
    numberAttempt INTEGER NOT NULL,
    passed        INTEGER NOT NULL,
    fail          INTEGER NOT NULL,
    user_id       INTEGER
);


