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
DROP DATABASE IF EXISTS PortoMorteNera;

create database PortoMorteNera;
use PortoMorteNera;

-- DBSpace Section
-- _______________


-- Tables Section
-- _____________ 

create table AREE_ATTRACCO (
     CodArea int not null auto_increment,
     Nome varchar(15) not null,
     constraint ID_AREE_ATTRACCO_ID primary key (CodArea));

create table ASTRONAVI (
     Targa char(8) not null,
     Nome varchar(20) not null,
     CodArea int,
     NumeroPosto int,
     CodModello char(6) not null,
     CUICapitano char(20) not null,
     constraint ID_ASTRONAVI_ID primary key (Targa),
     constraint UNIQ_POSTEGGIO unique (CodArea, NumeroPosto));

create table CARICHI (
     Tipologia int not null auto_increment,
     Quantita int not null,
     CodRichiesta int not null,
     constraint ID_CARICHI_ID primary key (Tipologia, CodRichiesta));

create table CELLE (
     NumCella int not null auto_increment,
     Capienza int not null check (Capienza > 0),
     constraint ID_CELLE_ID primary key (NumCella));

create table DIMENSIONI_PREZZI (
     Superficie int not null check (Superficie > 0),
     Prezzo numeric(6,2) not null check (Prezzo >= 0),
     constraint ID_DIMENSIONI_PREZZI_ID primary key (Superficie));

create table EQUIPAGGI (
     TargaAstronave char(10) not null,
     CUIAstronauta char(20) not null,
     constraint ID_EQUIPAGGI_ID primary key (CUIAstronauta, TargaAstronave));

create table MODELLI (
     CodModello char(6) not null,
     Nome varchar(20) not null,
     DimensioneArea int not null,
     constraint ID_MODELLI_ID primary key (CodModello));

create table PERSONE (
     CUI char(13) not null,
     Username varchar(20) not null,
     Nome varchar(25) not null,
     Cognome varchar(25) not null,
     Razza varchar(20) not null,
     DataNascita date not null,
     Ricercato boolean not null default false,
     Ideologia enum('Ribelle', 'Imperiale', 'Neutrale') not null default 'Neutrale',
     Ruolo enum('Astronauta', 'Capitano', 'Admin') not null default 'Astronauta',
     NumCella int,
     PianetaNascita char(20) not null,
     constraint ID_PERSONE_ID primary key (CUI),
     constraint SID_PERSONE_ID unique (Username));

create table PIANETI (
     CodPianeta char(10) not null,
     Nome varchar(25) not null,
     constraint ID_PIANETI_ID primary key (CodPianeta));

create table POSTEGGI (
     CodArea int not null,
     NumeroPosto int not null,
     constraint ID_POSTEGGI_ID primary key (CodArea, NumeroPosto));

create table RICHIESTE (
     CodRichiesta int not null auto_increment,
     EntrataUscita enum('E', 'U') not null,
     DataOra datetime not null default now(),
     Descrizione varchar(50) not null,
     CostoTotale numeric(6,2) not null check (CostoTotale >= 0),
     Esito enum('A', 'R'),
     DataEsito date,
     TargaAstronave char(10) not null,
     Scopo int not null,
     PianetaProvenienza char(20) not null,
     PianetaDestinazione char(20) not null,
     GestitaDa char(20),
     constraint ID_RICHIESTE_ID primary key (CodRichiesta));

create table TIPOLOGIE_CARICO (
     CodTipoCarico int not null auto_increment,
     Nome varchar(15) not null,
     Descrizione varchar(30) not null,
     CostoUnitario numeric(6,2) not null check (CostoUnitario >= 0),
     constraint ID_TIPOLOGIE_CARICO_ID primary key (CodTipoCarico));

create table TIPOLOGIE_VIAGGIO (
     CodTipoViaggio int not null auto_increment,
     Nome varchar(30) not null,
     constraint ID_TIPOLOGIE_VIAGGIO_ID primary key (CodTipoViaggio));


-- Constraints Section
-- ___________________ 

alter table ASTRONAVI add constraint REF_ASTRO_POSTE_FK
     foreign key (CodArea, NumeroPosto)
     references POSTEGGI(CodArea, NumeroPosto);

alter table ASTRONAVI add constraint REF_ASTRO_POSTE_CHK
     check((CodArea is not null and NumeroPosto is not null)
           or (CodArea is null and NumeroPosto is null)); 

alter table ASTRONAVI add constraint REF_ASTRO_MODEL_FK
     foreign key (CodModello)
     references MODELLI(CodModello);

alter table ASTRONAVI add constraint REF_ASTRO_PERSO_FK
     foreign key (CUICapitano)
     references PERSONE(CUI);

alter table CARICHI add constraint REF_CARIC_TIPOL
     foreign key (Tipologia)
     references TIPOLOGIE_CARICO(CodTipoCarico);

alter table CARICHI add constraint REF_CARIC_RICHI_FK
     foreign key (CodRichiesta)
     references RICHIESTE(CodRichiesta);

alter table EQUIPAGGI add constraint REF_EQUIP_PERSO
     foreign key (CUIAstronauta)
     references PERSONE(CUI);

alter table EQUIPAGGI add constraint REF_EQUIP_ASTRO_FK
     foreign key (TargaAstronave)
     references ASTRONAVI(Targa);

alter table MODELLI add constraint REF_MODEL_DIMEN_FK
     foreign key (DimensioneArea)
     references DIMENSIONI_PREZZI(Superficie);

alter table PERSONE add constraint REF_PERSO_CELLA_FK
     foreign key (NumCella)
     references CELLE(NumCella);

alter table PERSONE add constraint REF_PERSO_PIANE_FK
     foreign key (PianetaNascita)
     references PIANETI(CodPianeta);

alter table POSTEGGI add constraint REF_POSTE_AREA_
     foreign key (CodArea)
     references AREE_ATTRACCO(CodArea);

alter table RICHIESTE add constraint REF_RICHI_ASTRO_FK
     foreign key (TargaAstronave)
     references ASTRONAVI(Targa);

alter table RICHIESTE add constraint REF_RICHI_TIPOL_FK
     foreign key (Scopo)
     references TIPOLOGIE_VIAGGIO(CodTipoViaggio);

alter table RICHIESTE add constraint REF_RICHI_PIANE_1_FK
     foreign key (PianetaProvenienza)
     references PIANETI(CodPianeta);

alter table RICHIESTE add constraint REF_RICHI_PIANE_FK
     foreign key (PianetaDestinazione)
     references PIANETI(CodPianeta);

alter table RICHIESTE add constraint REF_RICHI_PERSO_FK
     foreign key (GestitaDa)
     references PERSONE(CUI);

alter table RICHIESTE add constraint COEX_RICHIESTE
     check((Esito is not null and DataEsito is not null and GestitaDa is not null)
           or (Esito is null and DataEsito is null and GestitaDa is null)); 

-- Index Section
-- _____________ 

create unique index ID_AREE_ATTRACCO_IND
     on AREE_ATTRACCO (CodArea);

create unique index ID_ASTRONAVI_IND
     on ASTRONAVI (Targa);

create index REF_ASTRO_POSTE_IND
     on ASTRONAVI (CodArea, NumeroPosto);

create index REF_ASTRO_MODEL_IND
     on ASTRONAVI (CodModello);

create index REF_ASTRO_PERSO_IND
     on ASTRONAVI (CUICapitano);

create index REF_CARIC_RICHI_IND
     on CARICHI (CodRichiesta);

create unique index ID_CARICHI_IND
     on CARICHI (Tipologia, CodRichiesta);

create unique index ID_CELLE_IND
     on CELLE (NumCella);

create unique index ID_DIMENSIONI_PREZZI_IND
     on DIMENSIONI_PREZZI (Superficie);

create unique index ID_EQUIPAGGI_IND
     on EQUIPAGGI (CUIAstronauta, TargaAstronave);

create index REF_EQUIP_ASTRO_IND
     on EQUIPAGGI (TargaAstronave);

create unique index ID_MODELLI_IND
     on MODELLI (CodModello);

create index REF_MODEL_DIMEN_IND
     on MODELLI (DimensioneArea);

create unique index ID_PERSONE_IND
     on PERSONE (CUI);

create index REF_PERSO_CELLE_IND
     on PERSONE (NumCella);

create index REF_PERSO_PIANE_IND
     on PERSONE (PianetaNascita);

create unique index ID_PIANETI_IND
     on PIANETI (CodPianeta);

create unique index ID_POSTEGGI_IND
     on POSTEGGI (CodArea, NumeroPosto);

create index REF_RICHI_ASTRO_IND
     on RICHIESTE (TargaAstronave);

create index REF_RICHI_TIPOL_IND
     on RICHIESTE (Scopo);

create index REF_RICHI_PIANE_1_IND
     on RICHIESTE (PianetaProvenienza);

create index REF_RICHI_PIANE_IND
     on RICHIESTE (PianetaDestinazione);

create index REF_RICHI_PERSO_IND
     on RICHIESTE (GestitaDa);

create unique index ID_RICHIESTE_IND
     on RICHIESTE (CodRichiesta);

create unique index ID_TIPOLOGIE_CARICO_IND
     on TIPOLOGIE_CARICO (CodTipoCarico);

create unique index ID_TIPOLOGIE_VIAGGIO_IND
     on TIPOLOGIE_VIAGGIO (CodTipoViaggio);
     
-- Views Section
-- _____________

create view RICHIESTE_PENDENTI as
select * from RICHIESTE where Esito is null;

-- Insertions Section
-- _____________ 

INSERT INTO PIANETI (CodPianeta, Nome) VALUES
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

INSERT INTO DIMENSIONI_PREZZI (Superficie, Prezzo) VALUES
(50, 100.00),
(100, 180.00),
(200, 350.00),
(500, 800.00),
(1000, 1500.00);

INSERT INTO MODELLI (CodModello, Nome, DimensioneArea) VALUES
('XW0001', 'X-Wing', 50),
('MF0002', 'Millennium Falcon', 100),
('SD0003', 'Star Destroyer', 1000),
('TIF004', 'TIE Fighter', 50),
('CR9005', 'Corvette CR90', 200),
('GR7506', 'Trasporto GR-75', 200);


INSERT INTO CELLE (NumCella, Capienza) VALUES
(1, 5),
(2, 3),
(3, 10),
(4, 2),
(5, 7);

INSERT INTO PERSONE (CUI, Username, Nome, Cognome, Razza, DataNascita, Ricercato, Ideologia, Ruolo, NumCella, PianetaNascita) VALUES
('SKWLKE510925T', 'L.Skywalker', 'Luke', 'Skywalker', 'Umano', '1951-09-25', TRUE, 'Ribelle', 'Astronauta', NULL, 'TATO002'),
('SLOHAN420713C', 'H.Solo', 'Han', 'Solo', 'Umano', '1942-07-13', FALSE, 'Neutrale', 'Capitano', NULL, 'CORU001'),
('RGNLLA510925A', 'L.Organa', 'Leia', 'Organa', 'Umano', '1951-09-25', TRUE, 'Ribelle', 'Astronauta', 4, 'ALDE005'),
('SKWNKN410419T', 'D.Vader', 'Anakin', 'Skywalker', 'Umano', '1941-04-19', FALSE, 'Imperiale', 'Capitano', NULL, 'TATO002'),
('PLPSHV201204N', 'E.Palatine', 'Sheev', 'Palpatine', 'Umano', '1920-12-04', FALSE, 'Imperiale', 'Admin', NULL, 'NABO004'),
('CHWBCC000101K', 'Chewie', 'Chewbacca', 'unknow', 'Wookiee', '1900-01-01', TRUE, 'Ribelle', 'Astronauta', NULL, 'KASH006'),
('KNBOBI370825C', 'O.Kenobi', 'Obi-Wan', 'Kenobi', 'Umano', '1937-08-25', TRUE, 'Ribelle', 'Astronauta', NULL, 'CORU001'),
('MULDRT600322D', 'D.Maul', 'Darth', 'Maul', 'Zabrak', '1960-03-22', FALSE, 'Imperiale', 'Capitano', NULL, 'DANT010'),
('TRKMFF220306M', 'G.Tarkin', 'Moff', 'Tarkin', 'Umano', '1922-03-06', FALSE, 'Imperiale', 'Admin', NULL, 'MUST007'),
('STRMTR0000000', 'Trooper0', 'Stormtrooper', '00000', 'Clone', '2000-01-01', FALSE, 'Imperiale', 'Astronauta', NULL, 'DTHSTR0'),
('STRMTR0000001', 'Trooper1', 'Stormtrooper', '00001', 'Clone', '2000-01-01', FALSE, 'Imperiale', 'Astronauta', NULL, 'DTHSTR0'),
('STRMTR0000002', 'Trooper2', 'Stormtrooper', '00002', 'Clone', '2000-01-01', FALSE, 'Imperiale', 'Astronauta', NULL, 'DTHSTR0'),
('STRMTR0000003', 'Trooper3', 'Stormtrooper', '00003', 'Clone', '2000-01-01', FALSE, 'Imperiale', 'Astronauta', NULL, 'DTHSTR0');

INSERT INTO AREE_ATTRACCO (Nome) VALUES
('Alpha'),
('Beta'),
('Officina'),
('Rifornimento');

INSERT INTO POSTEGGI (CodArea, NumeroPosto) VALUES
(1, 1), (1, 2), (1, 3), (1, 4), (1, 5),
(2, 1), (2, 2), (2, 3), (2, 4),
(3, 1), (3, 2), (3, 3), (3, 4), (3, 5), (3, 6),
(4, 1), (4, 2);

INSERT INTO ASTRONAVI (Targa, Nome, CodArea, NumeroPosto, CodModello, CUICapitano) VALUES
('MFALC001', 'Millennium Falcon', 2, 1, 'MF0002', 'SLOHAN420713C'),
('XWING002', 'Red Five', 3, 1, 'XW0001', 'MULDRT600322D'),
('STARD003', 'Executor', 3, 5, 'SD0003', 'MULDRT600322D'),
('CR900004', 'Tantive IV', 4, 1, 'CR9005', 'MULDRT600322D'),
('TIEF0005', 'Black Squadron 1', 3, 2, 'TIF004', 'SKWNKN410419T');

INSERT INTO EQUIPAGGI (CUIAstronauta, TargaAstronave) VALUES
('STRMTR0000002', 'XWING002'),
('SKWLKE510925T', 'MFALC001'),
('STRMTR0000003', 'CR900004'),
('STRMTR0000001', 'STARD003'),
('CHWBCC000101K', 'MFALC001'),
('STRMTR0000001', 'CR900004'),
('STRMTR0000000', 'TIEF0005');

INSERT INTO TIPOLOGIE_VIAGGIO (Nome) VALUES
('Trasporto merci'),
('Missione diplomatica'),
('Pattugliamento imperiale'),
('Trasporto prigionieri'),
('Esplorazione');

INSERT INTO TIPOLOGIE_CARICO (Nome, Descrizione, CostoUnitario) VALUES
('Spezie', 'Carico di spezie', 500.00),
('Armi', 'Armi imperiali', 1200.00),
('Dati', 'Informazioni classificate', 2000.00),
('Droidi', 'Componenti per droidi', 150.00),
('Alimentari', 'Razioni standard', 50.00);

INSERT INTO RICHIESTE (EntrataUscita, DataOra, Descrizione, CostoTotale, Esito, DataEsito, TargaAstronave, Scopo, PianetaProvenienza, PianetaDestinazione, GestitaDa) VALUES
('E', '2025-05-20 10:00:00', 'Arrivo per scarico spezie', 1680.00, 'R', '2025-05-20',  'MFALC001', 1, 'TATO002', 'DTHSTR0', 'TRKMFF220306M'),
('U', '2025-05-21 14:30:00', 'Partenza per missione diplomatica', 350.00, 'A', '2025-05-21',  'CR900004', 2, 'DTHSTR0', 'ALDE005', 'PLPSHV201204N'),
('E', '2025-05-22 08:00:00', 'Rifornimento e manutenzione', 1600.00, NULL, NULL, 'XWING002', 4, 'HOTH003', 'DTHSTR0', NULL),
('U', '2025-05-22 18:00:00', 'Pattuglia settore 7', 1500.00, 'A', '2025-05-22', 'STARD003', 3, 'DTHSTR0', 'GEON008', 'PLPSHV201204N'),
('E', '2025-05-23 09:15:00', 'Arrivo carico armi', 5300.00, NULL, NULL, 'TIEF0005', 1, 'NABO004', 'DTHSTR0', NULL);

INSERT INTO CARICHI (Tipologia, Quantita, CodRichiesta) VALUES
(1, 3, 1),
(2, 1, 5),
(3, 2, 5),
(4, 10, 3);
