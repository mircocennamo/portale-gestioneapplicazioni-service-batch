



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

insert into SSD_SECURITY.REQUEST   (idApplicazione, utenteCancellazione, ufficioCancellazione, operation,status) values ('12', 'mirco1', 'ufficiomio', 'delete',1);
insert into SSD_SECURITY.REQUEST   (idApplicazione, utenteCancellazione, ufficioCancellazione, operation,status) values ('13', 'mirco2', 'ufficiomio', 'delete',1);
--insert into request   (idApplicazione, utenteCancellazione, ufficioCancellazione, operation,status) values ('14', 'mirco3', 'ufficiomio', 'delete',1);
--insert into request   (idApplicazione, utenteCancellazione, ufficioCancellazione, operation,status) values ('15', 'mirco4', 'ufficiomio', 'delete',1);


insert into SSD_SECURITY.GROUPMEMBERS   (G_MEMBER,G_NAME,APP_ID) values ('bollo','pippo','12');
insert into SSD_SECURITY.GROUPMEMBERS   (G_MEMBER,G_NAME,APP_ID) values ('bollo','super','13');
insert into SSD_SECURITY.GROUPMEMBERS   (G_MEMBER,G_NAME,APP_ID) values ('bollo1','super2','13');

insert into SSD_SECURITY.SEC_REGOLE_SICUREZZA (G_NAME, APP_ID, ID_BLOCCO_REGOLA, ID_PRG_BLOCCO_REGOLA,DATE_INS)
values ('miaRegola1','12','1','1',CURRENT_TIMESTAMP());
insert into SSD_SECURITY.SEC_REGOLE_SICUREZZA (G_NAME, APP_ID, ID_BLOCCO_REGOLA, ID_PRG_BLOCCO_REGOLA,DATE_INS)
values ('miaRegola1','12','2','1',CURRENT_TIMESTAMP());
insert into SSD_SECURITY.SEC_REGOLE_SICUREZZA (G_NAME, APP_ID, ID_BLOCCO_REGOLA, ID_PRG_BLOCCO_REGOLA,DATE_INS)
values ('miaRegola1','12','3','1',CURRENT_TIMESTAMP());
insert into SSD_SECURITY.SEC_REGOLE_SICUREZZA (G_NAME, APP_ID, ID_BLOCCO_REGOLA, ID_PRG_BLOCCO_REGOLA,DATE_INS)
values ('miaRegola1','12','4','1',CURRENT_TIMESTAMP());
insert into SSD_SECURITY.SEC_REGOLE_SICUREZZA (G_NAME, APP_ID, ID_BLOCCO_REGOLA, ID_PRG_BLOCCO_REGOLA,DATE_INS)
values ('miaRegola1','12','5','1',CURRENT_TIMESTAMP());


insert into SSD_SECURITY.SEC_REGOLE_SICUREZZA (G_NAME, APP_ID, ID_BLOCCO_REGOLA, ID_PRG_BLOCCO_REGOLA,DATE_INS)
values ('miaRegola2','13','1','1',CURRENT_TIMESTAMP());
insert into SSD_SECURITY.SEC_REGOLE_SICUREZZA (G_NAME, APP_ID, ID_BLOCCO_REGOLA, ID_PRG_BLOCCO_REGOLA,DATE_INS)
values ('miaRegola2','13','2','1',CURRENT_TIMESTAMP());
insert into SSD_SECURITY.SEC_REGOLE_SICUREZZA (G_NAME, APP_ID, ID_BLOCCO_REGOLA, ID_PRG_BLOCCO_REGOLA,DATE_INS)
values ('miaRegola2','13','3','1',CURRENT_TIMESTAMP());