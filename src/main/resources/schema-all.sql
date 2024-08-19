



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

insert into SSD_SECURITY.REQUEST   (idApplicazione, utenteCancellazione, ufficioCancellazione, operation,status) values ('12', 'mirco1', 'ufficiomio', 'DELETE_APP','TO_BE_ASSIGNED');
insert into SSD_SECURITY.REQUEST   (idApplicazione, utenteCancellazione, ufficioCancellazione, operation,status) values ('13', 'mirco2', 'ufficiomio', 'DELETE_ALL_GROUPS','TO_BE_ASSIGNED');

insert into SSD_SECURITY.REQUEST   (idApplicazione, utenteCancellazione, ufficioCancellazione, operation,status) values ('14', 'mirco1', 'ufficiomio', 'DELETE_ALL_REGOLE_SICUREZZA','TO_BE_ASSIGNED');
insert into SSD_SECURITY.REQUEST   (idApplicazione, utenteCancellazione, ufficioCancellazione, operation,status) values ('15', 'mirco2', 'ufficiomio', 'DELETE_APP','TO_BE_ASSIGNED');

insert into SSD_SECURITY.REQUEST   (idApplicazione, utenteCancellazione, ufficioCancellazione, operation,status) values ('16', 'mirco1', 'ufficiomio', 'DELETE_APP','TO_BE_ASSIGNED');
insert into SSD_SECURITY.REQUEST   (idApplicazione, utenteCancellazione, ufficioCancellazione, operation,status) values ('17', 'mirco2', 'ufficiomio', 'DELETE_APP','TO_BE_ASSIGNED');

insert into SSD_SECURITY.REQUEST   (idApplicazione, utenteCancellazione, ufficioCancellazione, operation,status) values ('18', 'mirco1', 'ufficiomio', 'DELETE_APP','TO_BE_ASSIGNED');
insert into SSD_SECURITY.REQUEST   (idApplicazione, utenteCancellazione, ufficioCancellazione, operation,status) values ('19', 'mirco2', 'ufficiomio', 'DELETE_APP','TO_BE_ASSIGNED');

insert into SSD_SECURITY.REQUEST   (idApplicazione, utenteCancellazione, ufficioCancellazione, operation,status) values ('20', 'mirco1', 'ufficiomio', 'DELETE_APP','TO_BE_ASSIGNED');
insert into SSD_SECURITY.REQUEST   (idApplicazione, utenteCancellazione, ufficioCancellazione, operation,status) values ('21', 'mirco2', 'ufficiomio', 'DELETE_APP','TO_BE_ASSIGNED');

insert into SSD_SECURITY.REQUEST   (idApplicazione, utenteCancellazione, ufficioCancellazione, operation,status) values ('22', 'mirco1', 'ufficiomio', 'DELETE_APP','TO_BE_ASSIGNED');
insert into SSD_SECURITY.REQUEST   (idApplicazione, utenteCancellazione, ufficioCancellazione, operation,status) values ('23', 'mirco2', 'ufficiomio', 'DELETE_APP','TO_BE_ASSIGNED');

insert into SSD_SECURITY.REQUEST   (idApplicazione, utenteCancellazione, ufficioCancellazione, operation,status) values ('23', 'mirco2', 'ufficiomio', 'OTHER','TO_BE_ASSIGNED');


--insert into request   (idApplicazione, utenteCancellazione, ufficioCancellazione, operation,status) values ('14', 'mirco3', 'ufficiomio', 'delete',1);
--insert into request   (idApplicazione, utenteCancellazione, ufficioCancellazione, operation,status) values ('15', 'mirco4', 'ufficiomio', 'delete',1);


insert into SSD_SECURITY.GROUPMEMBERS   (G_MEMBER,G_NAME,APP_ID) values ('bollo','pippo','12');
insert into SSD_SECURITY.GROUPMEMBERS   (G_MEMBER,G_NAME,APP_ID) values ('bollo','super','13');
insert into SSD_SECURITY.GROUPMEMBERS   (G_MEMBER,G_NAME,APP_ID) values ('bollo1','super2','13');


insert into SSD_SECURITY.GROUPMEMBERS   (G_MEMBER,G_NAME,APP_ID) values ('mirco','miaRegola1','14');
insert into SSD_SECURITY.GROUPMEMBERS   (G_MEMBER,G_NAME,APP_ID) values ('bollo','miaRegola2','14');
insert into SSD_SECURITY.GROUPMEMBERS   (G_MEMBER,G_NAME,APP_ID) values ('mirco','miaRegola2','14');
--insert into SSD_SECURITY.GROUPMEMBERS   (G_MEMBER,G_NAME,APP_ID) values ('mirco','miaRegola2','14');

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


insert into SSD_SECURITY.SEC_REGOLE_SICUREZZA (G_NAME, APP_ID, ID_BLOCCO_REGOLA, ID_PRG_BLOCCO_REGOLA,DATE_INS)
values ('miaRegola2','14','1','1',CURRENT_TIMESTAMP());
insert into SSD_SECURITY.SEC_REGOLE_SICUREZZA (G_NAME, APP_ID, ID_BLOCCO_REGOLA, ID_PRG_BLOCCO_REGOLA,DATE_INS)
values ('miaRegola2','14','2','1',CURRENT_TIMESTAMP());
insert into SSD_SECURITY.SEC_REGOLE_SICUREZZA (G_NAME, APP_ID, ID_BLOCCO_REGOLA, ID_PRG_BLOCCO_REGOLA,DATE_INS)
values ('miaRegola2','14','3','1',CURRENT_TIMESTAMP());

insert into SSD_SECURITY.SEC_REGOLE_SICUREZZA (G_NAME, APP_ID, ID_BLOCCO_REGOLA, ID_PRG_BLOCCO_REGOLA,DATE_INS)
values ('miaRegola1','14','2','1',CURRENT_TIMESTAMP());
insert into SSD_SECURITY.SEC_REGOLE_SICUREZZA (G_NAME, APP_ID, ID_BLOCCO_REGOLA, ID_PRG_BLOCCO_REGOLA,DATE_INS)
values ('miaRegola1','14','3','1',CURRENT_TIMESTAMP());


INSERT into SSD_SECURITY.GROUPS (G_NAME, G_APP) values  ('miaRegola1','12');
INSERT into SSD_SECURITY.GROUPS (G_NAME, G_APP) values  ('miaRegola11','12');
INSERT into SSD_SECURITY.GROUPS (G_NAME, G_APP) values  ('miaRegola2','13');
INSERT into SSD_SECURITY.GROUPS (G_NAME, G_APP) values  ('miaRegola3','13');
INSERT into SSD_SECURITY.GROUPS (G_NAME, G_APP) values  ('miaRegola4','13');

INSERT into SSD_SECURITY.SEC_QUALIFICA_ASSEGNABILITA (QUALIFICA_ASSEGNABILITA_ID, QUALIFICA_ASSEGNABILITA) values  (1,'qualifica1');
INSERT into SSD_SECURITY.SEC_QUALIFICA_ASSEGNABILITA (QUALIFICA_ASSEGNABILITA_ID, QUALIFICA_ASSEGNABILITA) values  (2,'qualifica1');

INSERT into SSD_SECURITY.SEC_RUOLO_QUALIF_ASSEGNABILITA(G_NAME, G_APP, QUALIFICA_ASSEGNABILITA_ID) values  ('miaRegola1','12',1);
INSERT into SSD_SECURITY.SEC_RUOLO_QUALIF_ASSEGNABILITA(G_NAME, G_APP, QUALIFICA_ASSEGNABILITA_ID) values  ('miaRegola2','13',2);


INSERT into SSD_SECURITY.GROUPS_AGGREG(G_NAME_PRINC, G_APP_PRINC, G_NAME_DIP,G_APP_DIP) values  ('miaRegola1','appPrincipale','regolaDip','appDip');

INSERT into SSD_SECURITY.GROUPS_AGGREG(G_NAME_PRINC, G_APP_PRINC, G_NAME_DIP,G_APP_DIP) values  ('regolaPrincipale','appPrincipale','miaRegola2','appDip');


INSERT into SSD_SECURITY.SEC_APPLICAZIONE_MOTIVAZIONE(APP_ID,ID_TIPO_MOTIVAZIONE) values('12','1');
INSERT into SSD_SECURITY.SEC_APPLICAZIONE_MOTIVAZIONE(APP_ID,ID_TIPO_MOTIVAZIONE) values('12','2');
INSERT into SSD_SECURITY.SEC_APPLICAZIONE_MOTIVAZIONE(APP_ID,ID_TIPO_MOTIVAZIONE) values('12','3');
INSERT into SSD_SECURITY.SEC_APPLICAZIONE_MOTIVAZIONE(APP_ID,ID_TIPO_MOTIVAZIONE) values('12','4');
INSERT into SSD_SECURITY.SEC_APPLICAZIONE_MOTIVAZIONE(APP_ID,ID_TIPO_MOTIVAZIONE) values('12','5');


INSERT into SSD_SECURITY.SEC_APPLICAZIONE_MOTIVAZIONE(APP_ID,ID_TIPO_MOTIVAZIONE) values('13','1');
INSERT into SSD_SECURITY.SEC_APPLICAZIONE_MOTIVAZIONE(APP_ID,ID_TIPO_MOTIVAZIONE) values('13','2');
INSERT into SSD_SECURITY.SEC_APPLICAZIONE_MOTIVAZIONE(APP_ID,ID_TIPO_MOTIVAZIONE) values('13','3');



INSERT into SSD_SECURITY.SEC_APPLIC_MOTIV_MEMBERS(G_MEMBER,APP_ID,ID_TIPO_MOTIVAZIONE) values('1','12','1');
INSERT into SSD_SECURITY.SEC_APPLIC_MOTIV_MEMBERS(G_MEMBER,APP_ID,ID_TIPO_MOTIVAZIONE) values('1','12','2');
INSERT into SSD_SECURITY.SEC_APPLIC_MOTIV_MEMBERS(G_MEMBER,APP_ID,ID_TIPO_MOTIVAZIONE) values('1','12','3');

INSERT into SSD_SECURITY.SEC_APPLIC_MOTIV_MEMBERS(G_MEMBER,APP_ID,ID_TIPO_MOTIVAZIONE) values('2','13','1');
INSERT into SSD_SECURITY.SEC_APPLIC_MOTIV_MEMBERS(G_MEMBER,APP_ID,ID_TIPO_MOTIVAZIONE) values('3','13','2');


INSERT into SSD_SECURITY.SEC_APPLIC_MOTIV_MEMBERS(G_MEMBER,APP_ID,ID_TIPO_MOTIVAZIONE) values('mirco','14','1');
INSERT into SSD_SECURITY.SEC_APPLIC_MOTIV_MEMBERS(G_MEMBER,APP_ID,ID_TIPO_MOTIVAZIONE) values('mirco','14','2');

INSERT into SSD_SECURITY.SEC_APPLICAZIONE(APP_ID,APP_NAME) values('12','miaApp1');
INSERT into SSD_SECURITY.SEC_APPLICAZIONE(APP_ID,APP_NAME) values('13','miaApp2');
