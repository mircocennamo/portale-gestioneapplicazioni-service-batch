



--CREATE TABLE request  (
--    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
--    idApplicazione VARCHAR(20),
--    utenteCancellazione VARCHAR(20),
--    ufficioCancellazione VARCHAR(30),
--    operation VARCHAR(30)
--);

--status = 1 da lavorare
--status = 2 in lavorazione
--status = 3 lavorato ok
--status = 4 lavorato ko

insert into request   (idApplicazione, utenteCancellazione, ufficioCancellazione, operation,status) values ('12', 'mirco1', 'ufficiomio', 'delete',1);
insert into request   (idApplicazione, utenteCancellazione, ufficioCancellazione, operation,status) values ('13', 'mirco2', 'ufficiomio', 'delete',1);
--insert into request   (idApplicazione, utenteCancellazione, ufficioCancellazione, operation,status) values ('14', 'mirco3', 'ufficiomio', 'delete',1);
--insert into request   (idApplicazione, utenteCancellazione, ufficioCancellazione, operation,status) values ('15', 'mirco4', 'ufficiomio', 'delete',1);


insert into GROUPMEMBERS   (G_MEMBER,G_NAME,APP_ID) values ('bollo','pippo','12');
insert into GROUPMEMBERS   (G_MEMBER,G_NAME,APP_ID) values ('bollo','super','13');
insert into GROUPMEMBERS   (G_MEMBER,G_NAME,APP_ID) values ('bollo1','super2','13');