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
     Targa char(10) not null,
     Nome varchar(20) not null,
     CodArea INTEGER,
     NumeroPosto INTEGER,
     CodModello char(5) not null,
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
     CodModello char(5) not null,
     Nome varchar(20) not null,
     DimensioneArea int not null,
     constraint ID_MODELLO_ID primary key (CodModello));

create table PERSONA (
     CUI char(20) not null,
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
     CodPianeta char(20) not null,
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

