-- *********************************************
-- * Standard SQL generation                   
-- *--------------------------------------------
-- * DB-MAIN version: 11.0.2              
-- * Generator date: Sep 14 2021              
-- * Generation date: Mon May 19 16:19:42 2025 
-- ********************************************* 


-- Database Section
-- ________________ 
DROP DATABASE IF EXISTS PortoMorteNera;

CREATE DATABASE PortoMorteNera;
USE PortoMorteNera;

-- DBSpace Section
-- _______________


-- Tables Section
-- _____________ 

CREATE TABLE AREE_ATTRACCO (
     CodArea INT NOT NULL AUTO_INCREMENT,
     Nome VARCHAR(15) NOT NULL,
     CONSTRAINT ID_AREE_ATTRACCO_ID PRIMARY KEY (CodArea));

CREATE TABLE ASTRONAVI (
     Targa CHAR(8) NOT NULL,
     Nome VARCHAR(20) NOT NULL,
     CodArea INT DEFAULT NULL,
     NumeroPosto INT DEFAULT NULL,
     CodModello CHAR(6) NOT NULL,
     CUICapitano CHAR(20) NOT NULL,
     CONSTRAINT ID_ASTRONAVI_ID PRIMARY KEY (Targa),
     CONSTRAINT UNIQ_POSTEGGIO UNIQUE (CodArea, NumeroPosto));

CREATE TABLE CARICHI (
     Tipologia INT NOT NULL AUTO_INCREMENT,
     Quantita INT NOT NULL,
     CodRichiesta INT NOT NULL,
     CONSTRAINT ID_CARICHI_ID PRIMARY KEY (Tipologia, CodRichiesta));

CREATE TABLE CELLE (
     NumCella INT NOT NULL AUTO_INCREMENT,
     Capienza INT NOT NULL CHECK (Capienza > 0),
     CONSTRAINT ID_CELLE_ID PRIMARY KEY (NumCella));

CREATE TABLE DIMENSIONI_PREZZI (
     Superficie INT NOT NULL CHECK (Superficie > 0),
     Prezzo NUMERIC(6,2) NOT NULL CHECK (Prezzo >= 0),
     CONSTRAINT ID_DIMENSIONI_PREZZI_ID PRIMARY KEY (Superficie));

CREATE TABLE EQUIPAGGI (
     TargaAstronave CHAR(10) NOT NULL,
     CUIAstronauta CHAR(20) NOT NULL,
     CONSTRAINT ID_EQUIPAGGI_ID PRIMARY KEY (CUIAstronauta, TargaAstronave));

CREATE TABLE MODELLI (
     CodModello CHAR(6) NOT NULL,
     Nome VARCHAR(20) NOT NULL,
     DimensioneArea INT NOT NULL,
     CONSTRAINT ID_MODELLI_ID PRIMARY KEY (CodModello));

CREATE TABLE PERSONE (
     CUI CHAR(13) NOT NULL,
     Username VARCHAR(20) NOT NULL,
     Password VARCHAR(20) NOT NULL,
     Nome VARCHAR(25) NOT NULL,
     Cognome VARCHAR(25) NOT NULL,
     Razza VARCHAR(20) NOT NULL,
     DataNascita DATE NOT NULL,
     Ricercato BOOLEAN NOT NULL DEFAULT FALSE,
     Ideologia ENUM('Ribelle', 'Imperiale', 'Neutrale') NOT NULL DEFAULT 'Neutrale',
     Ruolo ENUM('Astronauta', 'Capitano', 'Admin') NOT NULL DEFAULT 'Astronauta',
     NumCella INT DEFAULT NULL,
     PianetaNascita CHAR(20) NOT NULL,
     CONSTRAINT ID_PERSONE_ID PRIMARY KEY (CUI),
     CONSTRAINT SID_PERSONE_ID UNIQUE (Username));

CREATE TABLE PIANETI (
     CodPianeta CHAR(10) NOT NULL,
     Nome VARCHAR(25) NOT NULL,
     CONSTRAINT ID_PIANETI_ID PRIMARY KEY (CodPianeta));

CREATE TABLE POSTEGGI (
     CodArea INT NOT NULL,
     NumeroPosto INT NOT NULL,
     CONSTRAINT ID_POSTEGGI_ID PRIMARY KEY (CodArea, NumeroPosto));

CREATE TABLE RICHIESTE (
     CodRichiesta INT NOT NULL AUTO_INCREMENT,
     EntrataUscita ENUM('E', 'U') NOT NULL,
     DataOra DATETIME NOT NULL DEFAULT NOW(),
     Descrizione VARCHAR(50) NOT NULL,
     CostoTotale NUMERIC(8,2) NOT NULL CHECK (CostoTotale >= 0),
     Esito ENUM('A', 'R'),
     DataEsito DATETIME,
     TargaAstronave CHAR(10) NOT NULL,
     Scopo INT NOT NULL,
     PianetaProvenienza CHAR(20) NOT NULL,
     PianetaDestinazione CHAR(20) NOT NULL,
     GestitaDa CHAR(20),
     CONSTRAINT ID_RICHIESTE_ID PRIMARY KEY (CodRichiesta));

CREATE TABLE TIPOLOGIE_CARICO (
     CodTipoCarico INT NOT NULL AUTO_INCREMENT,
     Nome VARCHAR(15) NOT NULL,
     Descrizione VARCHAR(30) NOT NULL,
     CostoUnitario NUMERIC(6,2) NOT NULL CHECK (CostoUnitario >= 0),
     CONSTRAINT ID_TIPOLOGIE_CARICO_ID PRIMARY KEY (CodTipoCarico));

CREATE TABLE TIPOLOGIE_VIAGGIO (
     CodTipoViaggio INT NOT NULL AUTO_INCREMENT,
     Nome VARCHAR(30) NOT NULL,
     CONSTRAINT ID_TIPOLOGIE_VIAGGIO_ID PRIMARY KEY (CodTipoViaggio));


-- Constraints Section
-- ___________________ 

ALTER TABLE ASTRONAVI ADD CONSTRAINT REF_ASTRO_POSTE_FK
     FOREIGN KEY (CodArea, NumeroPosto)
     REFERENCES POSTEGGI(CodArea, NumeroPosto);

ALTER TABLE ASTRONAVI ADD CONSTRAINT REF_ASTRO_POSTE_CHK
     CHECK((CodArea IS NOT NULL AND NumeroPosto IS NOT NULL)
           OR (CodArea IS NULL AND NumeroPosto IS NULL)); 

ALTER TABLE ASTRONAVI ADD CONSTRAINT REF_ASTRO_MODEL_FK
     FOREIGN KEY (CodModello)
     REFERENCES MODELLI(CodModello);

ALTER TABLE ASTRONAVI ADD CONSTRAINT REF_ASTRO_PERSO_FK
     FOREIGN KEY (CUICapitano)
     REFERENCES PERSONE(CUI);

ALTER TABLE CARICHI ADD CONSTRAINT REF_CARIC_TIPOL
     FOREIGN KEY (Tipologia)
     REFERENCES TIPOLOGIE_CARICO(CodTipoCarico);

ALTER TABLE CARICHI ADD CONSTRAINT REF_CARIC_RICHI_FK
     FOREIGN KEY (CodRichiesta)
     REFERENCES RICHIESTE(CodRichiesta);

ALTER TABLE EQUIPAGGI ADD CONSTRAINT REF_EQUIP_PERSO
     FOREIGN KEY (CUIAstronauta)
     REFERENCES PERSONE(CUI);

ALTER TABLE EQUIPAGGI ADD CONSTRAINT REF_EQUIP_ASTRO_FK
     FOREIGN KEY (TargaAstronave)
     REFERENCES ASTRONAVI(Targa);

ALTER TABLE MODELLI ADD CONSTRAINT REF_MODEL_DIMEN_FK
     FOREIGN KEY (DimensioneArea)
     REFERENCES DIMENSIONI_PREZZI(Superficie);

ALTER TABLE PERSONE ADD CONSTRAINT REF_PERSO_CELLA_FK
     FOREIGN KEY (NumCella)
     REFERENCES CELLE(NumCella);

ALTER TABLE PERSONE ADD CONSTRAINT REF_PERSO_PIANE_FK
     FOREIGN KEY (PianetaNascita)
     REFERENCES PIANETI(CodPianeta);

ALTER TABLE POSTEGGI ADD CONSTRAINT REF_POSTE_AREA_
     FOREIGN KEY (CodArea)
     REFERENCES AREE_ATTRACCO(CodArea);

ALTER TABLE RICHIESTE ADD CONSTRAINT REF_RICHI_ASTRO_FK
     FOREIGN KEY (TargaAstronave)
     REFERENCES ASTRONAVI(Targa);

ALTER TABLE RICHIESTE ADD CONSTRAINT REF_RICHI_TIPOL_FK
     FOREIGN KEY (Scopo)
     REFERENCES TIPOLOGIE_VIAGGIO(CodTipoViaggio);

ALTER TABLE RICHIESTE ADD CONSTRAINT REF_RICHI_PIANE_1_FK
     FOREIGN KEY (PianetaProvenienza)
     REFERENCES PIANETI(CodPianeta);

ALTER TABLE RICHIESTE ADD CONSTRAINT REF_RICHI_PIANE_FK
     FOREIGN KEY (PianetaDestinazione)
     REFERENCES PIANETI(CodPianeta);

ALTER TABLE RICHIESTE ADD CONSTRAINT REF_RICHI_PERSO_FK
     FOREIGN KEY (GestitaDa)
     REFERENCES PERSONE(CUI);

ALTER TABLE RICHIESTE ADD CONSTRAINT COEX_RICHIESTE
     CHECK((Esito IS NOT NULL AND DataEsito IS NOT NULL AND GestitaDa IS NOT NULL)
           OR (Esito IS NULL AND DataEsito IS NULL AND GestitaDa IS NULL)); 

-- Index Section
-- _____________ 

CREATE UNIQUE INDEX ID_AREE_ATTRACCO_IND
     ON AREE_ATTRACCO (CodArea);

CREATE UNIQUE INDEX ID_ASTRONAVI_IND
     ON ASTRONAVI (Targa);

CREATE INDEX REF_ASTRO_POSTE_IND
     ON ASTRONAVI (CodArea, NumeroPosto);

CREATE INDEX REF_ASTRO_MODEL_IND
     ON ASTRONAVI (CodModello);

CREATE INDEX REF_ASTRO_PERSO_IND
     ON ASTRONAVI (CUICapitano);

CREATE INDEX REF_CARIC_RICHI_IND
     ON CARICHI (CodRichiesta);

CREATE UNIQUE INDEX ID_CARICHI_IND
     ON CARICHI (Tipologia, CodRichiesta);

CREATE UNIQUE INDEX ID_CELLE_IND
     ON CELLE (NumCella);

CREATE UNIQUE INDEX ID_DIMENSIONI_PREZZI_IND
     ON DIMENSIONI_PREZZI (Superficie);

CREATE UNIQUE INDEX ID_EQUIPAGGI_IND
     ON EQUIPAGGI (CUIAstronauta, TargaAstronave);

CREATE INDEX REF_EQUIP_ASTRO_IND
     ON EQUIPAGGI (TargaAstronave);

CREATE UNIQUE INDEX ID_MODELLI_IND
     ON MODELLI (CodModello);

CREATE INDEX REF_MODEL_DIMEN_IND
     ON MODELLI (DimensioneArea);

CREATE UNIQUE INDEX ID_PERSONE_IND
     ON PERSONE (CUI);

CREATE INDEX REF_PERSO_CELLE_IND
     ON PERSONE (NumCella);

CREATE INDEX REF_PERSO_PIANE_IND
     ON PERSONE (PianetaNascita);

CREATE UNIQUE INDEX ID_PIANETI_IND
     ON PIANETI (CodPianeta);

CREATE UNIQUE INDEX ID_POSTEGGI_IND
     ON POSTEGGI (CodArea, NumeroPosto);

CREATE INDEX REF_RICHI_ASTRO_IND
     ON RICHIESTE (TargaAstronave);

CREATE INDEX REF_RICHI_TIPOL_IND
     ON RICHIESTE (Scopo);

CREATE INDEX REF_RICHI_PIANE_1_IND
     ON RICHIESTE (PianetaProvenienza);

CREATE INDEX REF_RICHI_PIANE_IND
     ON RICHIESTE (PianetaDestinazione);

CREATE INDEX REF_RICHI_PERSO_IND
     ON RICHIESTE (GestitaDa);

CREATE UNIQUE INDEX ID_RICHIESTE_IND
     ON RICHIESTE (CodRichiesta);

CREATE UNIQUE INDEX ID_TIPOLOGIE_CARICO_IND
     ON TIPOLOGIE_CARICO (CodTipoCarico);

CREATE UNIQUE INDEX ID_TIPOLOGIE_VIAGGIO_IND
     ON TIPOLOGIE_VIAGGIO (CodTipoViaggio);
     
-- Views Section
-- _____________

CREATE VIEW RICHIESTE_PENDENTI AS
    SELECT *
    FROM RICHIESTE
    WHERE Esito IS NULL;
    
CREATE VIEW RICHIESTE_ACCETTATE AS
	SELECT *
	FROM Richieste
	WHERE Esito = 'A';
    
CREATE VIEW RICHIESTE_RIFIUTATE AS
	SELECT *
	FROM Richieste
	WHERE Esito = 'R';
    
CREATE OR REPLACE VIEW ULTIMA_RICHIESTA AS
	SELECT r.CodRichiesta
	FROM richieste r
	ORDER BY r.CodRichiesta DESC
	LIMIT 1;
        
/* Calcolo del costo dell'ultima richiesta */
CREATE OR REPLACE VIEW COSTO_ULTIMA_RICHIESTA AS
	SELECT SUM((c.Quantita * tc.CostoUnitario) + dp.Prezzo) AS costo
	FROM richieste r, carichi c, tipologie_carico tc, astronavi ast, modelli m, dimensioni_prezzi dp
	WHERE r.CodRichiesta = c.CodRichiesta
	AND c.Tipologia = tc.CodTipoCarico
	AND r.TargaAstronave = ast.Targa
	AND ast.CodModello = m.CodModello
	AND m.DimensioneArea = dp.Superficie
	AND r.CodRichiesta = (SELECT CodRichiesta FROM ULTIMA_RICHIESTA);

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

INSERT INTO PERSONE (CUI, Username, Password, Nome, Cognome, Razza, DataNascita, Ricercato, Ideologia, Ruolo, NumCella, PianetaNascita) VALUES
('SKWLKE510925T', 'L.Skywalker', '', 'Luke', 'Skywalker', 'Umano', '1951-09-25', TRUE, 'Ribelle', 'Astronauta', NULL, 'TATO002'),
('SLOHAN420713C', 'H.Solo', '', 'Han', 'Solo', 'Umano', '1942-07-13', FALSE, 'Neutrale', 'Capitano', NULL, 'CORU001'),
('RGNLLA510925A', 'L.Organa', '', 'Leia', 'Organa', 'Umano', '1951-09-25', TRUE, 'Ribelle', 'Astronauta', 4, 'ALDE005'),
('SKWNKN410419T', 'D.Vader', '', 'Anakin', 'Skywalker', 'Umano', '1941-04-19', FALSE, 'Imperiale', 'Capitano', NULL, 'TATO002'),
('PLPSHV201204N', 'E.Palatine', '', 'Sheev', 'Palpatine', 'Umano', '1920-12-04', FALSE, 'Imperiale', 'Admin', NULL, 'NABO004'),
('CHWBCC000101K', 'Chewie', '', 'Chewbacca', 'unknow', 'Wookiee', '1900-01-01', TRUE, 'Ribelle', 'Astronauta', NULL, 'KASH006'),
('KNBOBI370825C', 'O.Kenobi', '', 'Obi-Wan', 'Kenobi', 'Umano', '1937-08-25', TRUE, 'Ribelle', 'Astronauta', NULL, 'CORU001'),
('MULDRT600322D', 'D.Maul', '', 'Darth', 'Maul', 'Zabrak', '1960-03-22', FALSE, 'Imperiale', 'Capitano', NULL, 'DANT010'),
('TRKMFF220306M', 'G.Tarkin', '', 'Moff', 'Tarkin', 'Umano', '1922-03-06', FALSE, 'Imperiale', 'Admin', NULL, 'MUST007'),
('STRMTR0000000', 'Trooper0', '', 'Stormtrooper', '00000', 'Clone', '2000-01-01', FALSE, 'Imperiale', 'Astronauta', NULL, 'DTHSTR0'),
('STRMTR0000001', 'Trooper1', 'pippo', 'Stormtrooper', '00001', 'Clone', '2000-01-01', FALSE, 'Imperiale', 'Astronauta', NULL, 'DTHSTR0'),
('STRMTR0000002', 'Trooper2', '', 'Stormtrooper', '00002', 'Clone', '2000-01-01', FALSE, 'Imperiale', 'Astronauta', NULL, 'DTHSTR0'),
('STRMTR0000003', 'Trooper3', '', 'Stormtrooper', '00003', 'Clone', '2000-01-01', FALSE, 'Imperiale', 'Astronauta', NULL, 'DTHSTR0');

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
('TIEF0005', 'Black Squadron 1', NULL, NULL, 'TIF004', 'SKWNKN410419T');

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
('E', '2025-05-21 12:00:00', 'Arrivo per missione diplomatica', 350.00, 'A', '2025-05-21',  'CR900004', 2, 'ALDE005', 'DTHSTR0', 'PLPSHV201204N'),
('U', '2025-05-21 14:30:00', 'Partenza per missione diplomatica', 350.00, 'A', '2025-05-21',  'CR900004', 2, 'DTHSTR0', 'ALDE005', 'PLPSHV201204N'),
('E', '2025-05-22 08:00:00', 'Rifornimento e manutenzione', 1600.00, NULL, NULL, 'XWING002', 4, 'HOTH003', 'DTHSTR0', NULL),
('U', '2025-05-22 18:00:00', 'Pattuglia settore 7', 1500.00, 'A', '2025-05-22', 'STARD003', 3, 'DTHSTR0', 'GEON008', 'PLPSHV201204N'),
('E', '2025-05-23 09:15:00', 'Arrivo carico armi', 5300.00, NULL, NULL, 'TIEF0005', 1, 'NABO004', 'DTHSTR0', NULL);

INSERT INTO CARICHI (Tipologia, Quantita, CodRichiesta) VALUES
(1, 3, 1),
(2, 1, 5),
(3, 2, 5),
(4, 10, 3);
