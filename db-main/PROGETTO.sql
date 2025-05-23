-- *********************************************
-- * Standard SQL generation                   
-- *--------------------------------------------
-- * DB-MAIN version: 11.0.2              
-- * Generator date: Sep 14 2021              
-- * Generation date: Mon May 19 16:19:42 2025 
-- * LUN file: C:\GitRepository\Progetto-BD\db-main\PROGETTO.lun 
-- * Schema: Porto/SQL1 
-- ********************************************* 


-- Database Section
-- ________________ 
DROP DATABASE IF EXISTS Porto;

create database Porto;
use Porto;

-- DBSpace Section
-- _______________


-- Tables Section
-- _____________ 

create table AREA_ATTRACCO (
     CodArea INTEGER not null auto_increment,
     Nome varchar(15) not null,
     constraint ID_AREA_ATTRACCO_ID primary key (CodArea));

create table ASTRONAVE (
     Targa char(8) not null,
     Nome varchar(20) not null,
     CodArea INTEGER,
     NumeroPosto INTEGER,
     CodModello char(6) not null,
     CUICapitano char(20) not null,
     constraint ID_ASTRONAVE_ID primary key (Targa));

create table CARICO (
     Tipologia INTEGER not null auto_increment,
     Quantita INTEGER not null,
     CodRichiesta INTEGER not null,
     constraint ID_CARICO_ID primary key (Tipologia, CodRichiesta));

create table CELLA (
     NumCella INTEGER not null auto_increment,
     Capienza INTEGER not null check ( Capienza > 0 ),
     constraint ID_CELLA_ID primary key (NumCella));

create table DIMENSIONE_PREZZO (
     Superficie INTEGER not null check ( Superficie > 0),
     Prezzo numeric(6,2) not null check ( Prezzo >= 0),
     constraint ID_DIMENSIONE_PREZZO_ID primary key (Superficie));

create table EQUIPAGGIO (
     TargaAstronave char(10) not null,
     CUIAstronauta char(20) not null,
     constraint ID_EQUIPAGGIO_ID primary key (CUIAstronauta, TargaAstronave));

create table MODELLO (
     CodModello char(6) not null,
     Nome varchar(20) not null,
     DimensioneArea int not null,
     constraint ID_MODELLO_ID primary key (CodModello));

create table PERSONA (
     CUI char(13) not null,
     Username varchar(20) not null,
     Nome varchar(25) not null,
     Cognome varchar(25) not null,
     Razza varchar(20) not null,
     DataNascita date not null,
     Ricercato boolean not null,
     Ideologia varchar(10) not null check ( Ideologia in ('Ribelle' , 'Imperiale' , 'Neutrale' )),
     Ruolo varchar(15) not null check (Ruolo in ( 'Astronauta' , 'Capitano' , 'Admin')),
     NumCella INTEGER,
     PianetaNascita char(20) not null,
     constraint ID_PERSONA_ID primary key (CUI),
     constraint SID_PERSONA_ID unique (Username));

create table PIANETA (
     CodPianeta char(10) not null,
     Nome varchar(25) not null,
     constraint ID_PIANETA_ID primary key (CodPianeta));

create table POSTEGGIO (
     CodArea INTEGER not null,
     NumeroPosto INTEGER not null,
     constraint ID_POSTEGGIO_ID primary key (CodArea, NumeroPosto));

create table RICHIESTA (
     CodRichiesta INTEGER not null auto_increment,
     EntrataUscita char(1) not null check ( EntrataUscita in ( 'E' , 'U')),
     DataOra datetime not null,
     Descrizione varchar(50) not null,
     CostoTotale numeric(6,2) not null check ( CostoTotale >= 0),
     Esito char(1) check (Esito in ( 'A' , 'R')),
     DataEsito date,
     TargaAstronave char(10) not null,
     Scopo INTEGER not null,
     PianetaProvenienza char(20) not null,
     PianetaDestinazione char(20) not null,
     GestitaDa char(20),
     constraint ID_RICHIESTA_ID primary key (CodRichiesta));

create table TIPOLOGIA_CARICO (
     CodTipoCarico INTEGER not null auto_increment,
     Nome varchar(15) not null,
     Descrizione varchar(30) not null,
     CostoUnitario numeric(6,2) not null check ( CostoUnitario >= 0),
     constraint ID_TIPOLOGIA_CARICO_ID primary key (CodTipoCarico));

create table TIPOLOGIA_VIAGGIO (
     CodTipoViaggio INTEGER not null auto_increment,
     Nome varchar(30) not null,
     constraint ID_TIPOLOGIA_VIAGGIO_ID primary key (CodTipoViaggio));


-- Constraints Section
-- ___________________ 

alter table ASTRONAVE add constraint REF_ASTRO_POSTE_FK
     foreign key (CodArea, NumeroPosto)
     references POSTEGGIO(CodArea, NumeroPosto);

alter table ASTRONAVE add constraint REF_ASTRO_POSTE_CHK
     check((CodArea is not null and NumeroPosto is not null)
           or (CodArea is null and NumeroPosto is null)); 

alter table ASTRONAVE add constraint REF_ASTRO_MODEL_FK
     foreign key (CodModello)
     references MODELLO(CodModello);

alter table ASTRONAVE add constraint REF_ASTRO_PERSO_FK
     foreign key (CUICapitano)
     references PERSONA(CUI);

alter table CARICO add constraint REF_CARIC_TIPOL
     foreign key (Tipologia)
     references TIPOLOGIA_CARICO(CodTipoCarico);

alter table CARICO add constraint REF_CARIC_RICHI_FK
     foreign key (CodRichiesta)
     references RICHIESTA(CodRichiesta);

alter table EQUIPAGGIO add constraint REF_EQUIP_PERSO
     foreign key (CUIAstronauta)
     references PERSONA(CUI);

alter table EQUIPAGGIO add constraint REF_EQUIP_ASTRO_FK
     foreign key (TargaAstronave)
     references ASTRONAVE(Targa);

alter table MODELLO add constraint REF_MODEL_DIMEN_FK
     foreign key (DimensioneArea)
     references DIMENSIONE_PREZZO(Superficie);

alter table PERSONA add constraint REF_PERSO_CELLA_FK
     foreign key (NumCella)
     references CELLA(NumCella);

alter table PERSONA add constraint REF_PERSO_PIANE_FK
     foreign key (PianetaNascita)
     references PIANETA(CodPianeta);

alter table POSTEGGIO add constraint REF_POSTE_AREA_
     foreign key (CodArea)
     references AREA_ATTRACCO(CodArea);

alter table RICHIESTA add constraint REF_RICHI_ASTRO_FK
     foreign key (TargaAstronave)
     references ASTRONAVE(Targa);

alter table RICHIESTA add constraint REF_RICHI_TIPOL_FK
     foreign key (Scopo)
     references TIPOLOGIA_VIAGGIO(CodTipoViaggio);

alter table RICHIESTA add constraint REF_RICHI_PIANE_1_FK
     foreign key (PianetaProvenienza)
     references PIANETA(CodPianeta);

alter table RICHIESTA add constraint REF_RICHI_PIANE_FK
     foreign key (PianetaDestinazione)
     references PIANETA(CodPianeta);

alter table RICHIESTA add constraint REF_RICHI_PERSO_FK
     foreign key (GestitaDa)
     references PERSONA(CUI);

alter table RICHIESTA add constraint COEX_RICHIESTA
     check((Esito is not null and DataEsito is not null and GestitaDa is not null)
           or (Esito is null and DataEsito is null and GestitaDa is null)); 


-- Index Section
-- _____________ 

create unique index ID_AREA_ATTRACCO_IND
     on AREA_ATTRACCO (CodArea);

create unique index ID_ASTRONAVE_IND
     on ASTRONAVE (Targa);

create index REF_ASTRO_POSTE_IND
     on ASTRONAVE (CodArea, NumeroPosto);

create index REF_ASTRO_MODEL_IND
     on ASTRONAVE (CodModello);

create index REF_ASTRO_PERSO_IND
     on ASTRONAVE (CUICapitano);

create index REF_CARIC_RICHI_IND
     on CARICO (CodRichiesta);

create unique index ID_CARICO_IND
     on CARICO (Tipologia, CodRichiesta);

create unique index ID_CELLA_IND
     on CELLA (NumCella);

create unique index ID_DIMENSIONE_PREZZO_IND
     on DIMENSIONE_PREZZO (Superficie);

create unique index ID_EQUIPAGGIO_IND
     on EQUIPAGGIO (CUIAstronauta, TargaAstronave);

create index REF_EQUIP_ASTRO_IND
     on EQUIPAGGIO (TargaAstronave);

create unique index ID_MODELLO_IND
     on MODELLO (CodModello);

create index REF_MODEL_DIMEN_IND
     on MODELLO (DimensioneArea);

create unique index ID_PERSONA_IND
     on PERSONA (CUI);

create unique index SID_PERSONA_IND
     on PERSONA (Username);

create index REF_PERSO_CELLA_IND
     on PERSONA (NumCella);

create index REF_PERSO_PIANE_IND
     on PERSONA (PianetaNascita);

create unique index ID_PIANETA_IND
     on PIANETA (CodPianeta);

create unique index ID_POSTEGGIO_IND
     on POSTEGGIO (CodArea, NumeroPosto);

create index REF_RICHI_ASTRO_IND
     on RICHIESTA (TargaAstronave);

create index REF_RICHI_TIPOL_IND
     on RICHIESTA (Scopo);

create index REF_RICHI_PIANE_1_IND
     on RICHIESTA (PianetaProvenienza);

create index REF_RICHI_PIANE_IND
     on RICHIESTA (PianetaDestinazione);

create index REF_RICHI_PERSO_IND
     on RICHIESTA (GestitaDa);

create unique index ID_RICHIESTA_IND
     on RICHIESTA (CodRichiesta);

create unique index ID_TIPOLOGIA_CARICO_IND
     on TIPOLOGIA_CARICO (CodTipoCarico);

create unique index ID_TIPOLOGIA_VIAGGIO_IND
     on TIPOLOGIA_VIAGGIO (CodTipoViaggio);

-- Insertions Section
-- _____________ 

INSERT INTO PIANETA (CodPianeta, Nome) VALUES
('DTHSTR0', 'Morte Nera'),
('CORU001', 'Coruscant'),
('TATO002', 'Tatooine'),
('HOTH003', 'Hoth'),
('NABO004', 'Naboo'),
('ALDE005', 'Alderaan'),
('KASH006', 'Kashyyyk'),
('MUST007', 'Mustafar'),
('GEON008', 'Geonosis'),
('ENDOR09', 'Endor'),
('DANT010', 'Dantooine');

INSERT INTO DIMENSIONE_PREZZO (Superficie, Prezzo) VALUES
(50, 100.00),
(100, 180.00),
(200, 350.00),
(500, 800.00),
(1000, 1500.00);

INSERT INTO MODELLO (CodModello, Nome, DimensioneArea) VALUES
('XW0001', 'X-Wing', 50),
('MF0002', 'Millennium Falcon', 100),
('SD0003', 'Star Destroyer', 1000),
('TIF004', 'TIE Fighter', 50),
('CR9005', 'Corvette CR90', 200),
('GR7506', 'Trasporto GR-75', 200);


INSERT INTO CELLA (NumCella, Capienza) VALUES
(1, 5),
(2, 3),
(3, 10),
(4, 2),
(5, 7);

INSERT INTO PERSONA (CUI, Username, Nome, Cognome, Razza, DataNascita, Ricercato, Ideologia, Ruolo, NumCella, PianetaNascita) VALUES
('SKWLKE510925T', 'L.Skywalker', 'Luke', 'Skywalker', 'Umano', '1951-09-25', TRUE, 'Ribelle', 'Astronauta', NULL, 'TATO002'),
('SLOHAN420713C', 'H.Solo', 'Han', 'Solo', 'Umano', '1942-07-13', FALSE, 'Neutrale', 'Capitano', NULL, 'CORU001'),
('RGNLLA510925A', 'L.Organa', 'Leia', 'Organa', 'Umano', '1951-09-25', TRUE, 'Ribelle', 'Astronauta', 4, 'ALDE005'),
('SKWNKN410419T', 'D.Vader', 'Anakin', 'Skywalker', 'Umano', '1941-04-19', FALSE, 'Imperiale', 'Capitano', NULL, 'TATO002'),
('PLPSHV201204N', 'E.Palatine', 'Sheev', 'Palpatine', 'Umano', '1920-12-04', FALSE, 'Imperiale', 'Admin', NULL, 'NABO004'),
('CHWBCC000101K', 'Chewie', 'Chewbacca', 'unknow', 'Wookiee', '1900-01-01', TRUE, 'Ribelle', 'Astronauta', NULL, 'KASH006'),
('KNBOBI370825C', 'O.Kenobi', 'Obi-Wan', 'Kenobi', 'Umano', '1937-08-25', TRUE, 'Ribelle', 'Astronauta', NULL, 'CORU001'),
('MULDRT600322D', 'D.Maul', 'Darth', 'Maul', 'Zabrak', '1960-03-22', FALSE, 'Imperiale', 'Capitano', NULL, 'DANT010'),
('TRKMFF220306M', 'G.Tarkin', 'Moff', 'Tarkin', 'Umano', '1922-03-06', FALSE, 'Imperiale', 'Admin', NULL, 'MUST007'),
('STRMTR0000000', 'Trooper0', 'Stormtrooper', '00000', 'Clone', '', FALSE, 'Imperiale', 'Astronauta', NULL, 'DTHSTR0'),
('STRMTR0000001', 'Trooper1', 'Stormtrooper', '00001', 'Clone', '', FALSE, 'Imperiale', 'Astronauta', NULL, 'DTHSTR0'),
('STRMTR0000002', 'Trooper2', 'Stormtrooper', '00002', 'Clone', '', FALSE, 'Imperiale', 'Astronauta', NULL, 'DTHSTR0'),
('STRMTR0000003', 'Trooper3', 'Stormtrooper', '00003', 'Clone', '', FALSE, 'Imperiale', 'Astronauta', NULL, 'DTHSTR0');

INSERT INTO AREA_ATTRACCO (CodArea, Nome) VALUES
(1, 'Alpha'),
(2, 'Beta'),
(3, 'Officina'),
(4, 'Rifornimento');

INSERT INTO POSTEGGIO (CodArea, NumeroPosto) VALUES
(1, 1), (1, 2), (1, 3), (1, 4), (1, 5),
(2, 1), (2, 2), (2, 3), (2, 4),
(3, 1), (3, 2), (3, 3), (3, 4), (3, 5), (3, 6),
(4, 1), (4, 2);

INSERT INTO ASTRONAVE (Targa, Nome, CodArea, NumeroPosto, CodModello, CUICapitano) VALUES
('MFALC001', 'Millennium Falcon', 2, 1, 'MF0002', 'SLOHAN420713C'),
('XWING002', 'Red Five', 3, 1, 'XW0001', 'MULDRT600322D'),
('STARD003', 'Executor', 3, 5, 'SD0003', 'MULDRT600322D'),
('CR900004', 'Tantive IV', 4, 1, 'CR9005', 'MULDRT600322D'),
('TIEF0005', 'Black Squadron 1', 3, 2, 'TIF004', 'SKWNKN410419T');

INSERT INTO EQUIPAGGIO (CUIAstronauta, TargaAstronave) VALUES
('STRMTR0000002', 'XWING002'),
('SKWLKE510925T', 'MFALC001'),
('STRMTR0000003', 'CR900004'),
('STRMTR0000001', 'STARD003'),
('CHWBCC000101K', 'MFALC001'),
('STRMTR0000001', 'CR900004'),
('STRMTR0000000', 'TIEF0005');

INSERT INTO TIPOLOGIA_VIAGGIO (CodTipoViaggio, Nome) VALUES
(1, 'Trasporto merci'),
(2, 'Missione diplomatica'),
(3, 'Pattugliamento imperiale'),
(4, 'Trasporto prigionieri'),
(5, 'Esplorazione');

INSERT INTO TIPOLOGIA_CARICO (CodTipoCarico, Nome, Descrizione, CostoUnitario) VALUES
(1, 'Spezie', 'Carico di spezie', 500.00),
(2, 'Armi', 'Armi imperiali', 1200.00),
(3, 'Dati', 'Informazioni classificate', 2000.00),
(4, 'Droidi', 'Componenti per droidi', 150.00),
(5, 'Alimentari', 'Razioni standard', 50.00);

INSERT INTO RICHIESTA (CodRichiesta, EntrataUscita, DataOra, Descrizione, CostoTotale, Esito, DataEsito, TargaAstronave, Scopo, PianetaProvenienza, PianetaDestinazione, GestitaDa) VALUES
(1, 'E', '2025-05-20 10:00:00', 'Arrivo per scarico spezie', 1680.00, 'R', '2025-05-20',  'MFALC001', 1, 'TATO002', 'DTHSTR0', 'TRKMFF220306M'),
(2, 'U', '2025-05-21 14:30:00', 'Partenza per missione diplomatica', 350.00, 'A', '2025-05-21',  'CR900004', 2, 'DTHSTR0', 'ALDE005', 'PLPSHV201204N'),
(3, 'E', '2025-05-22 08:00:00', 'Rifornimento e manutenzione', 1600.00, NULL, NULL, 'XWING002', 4, 'HOTH003', 'DTHSTR0', NULL),
(4, 'U', '2025-05-22 18:00:00', 'Pattuglia settore 7', 1500.00, 'A', '2025-05-22', 'STARD003', 3, 'DTHSTR0', 'GEON008', 'PLPSHV201204N'),
(5, 'E', '2025-05-23 09:15:00', 'Arrivo carico armi', 5300.00, NULL, NULL, 'TIEF0005', 1, 'NABO004', 'DTHSTR0', NULL);

INSERT INTO CARICO (Tipologia, Quantita, CodRichiesta) VALUES
(1, 3, 1),
(2, 1, 5),
(3, 2, 5),
(4, 10, 3);