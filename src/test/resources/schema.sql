CREATE TABLE wave_form
(
ID BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
REC_DATE VARCHAR(256) NOT NULL,
LOCATION VARCHAR(256) NOT NULL
);