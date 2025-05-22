
USE Porto;

-- Popolamento tabella DIMENSIONE_PREZZO
INSERT INTO DIMENSIONE_PREZZO (Superficie, Prezzo) VALUES
(10, 50.00),
(20, 80.00),
(30, 120.00),
(50, 200.00),
(100, 350.00);

-- Popolamento tabella MODELLO
INSERT INTO MODELLO (CodModello, Nome, DimensioneArea) VALUES
('MOD01', 'Star Explorer', 20),
('MOD02', 'Cargo Hauler', 50),
('MOD03', 'Speed Demon', 10),
('MOD04', 'Galaxy Cruiser', 100);

-- Popolamento tabella AREA_ATTRACCO
INSERT INTO AREA_ATTRACCO (Nome) VALUES
('Area Alpha'),
('Area Beta'),
('Area Gamma');

-- Popolamento tabella POSTEGGIO
INSERT INTO POSTEGGIO (CodArea, NumeroPosto) VALUES
(1, 1),
(1, 2),
(1, 3),
(2, 1),
(2, 2),
(3, 1);

-- Popolamento tabella CELLA
INSERT INTO CELLA (Capienza) VALUES
(1),
(2),
(3),
(1);

-- Popolamento tabella PIANETA
INSERT INTO PIANETA (CodPianeta, Nome) VALUES
('TERRA', 'Terra'),
('MARTE', 'Marte'),
('GIOVE', 'Giove'),
('SATURNO', 'Saturno'),
('ALDEB', 'Aldebaran IV');

-- Popolamento tabella PERSONA
INSERT INTO PERSONA (CUI, Username, Nome, Cognome, Razza, DataNascita, Ricercato, Ideologia, Ruolo, NumCella, PianetaNascita) VALUES
('CUI001', 'j.skywalker', 'Luke', 'Skywalker', 'Umano', '1977-05-25', FALSE, 'Ribelle', 'Capitano', NULL, 'TERRA'),
('CUI002', 'l.organa', 'Leia', 'Organa', 'Umano', '1977-05-25', FALSE, 'Ribelle', 'Astronauta', NULL, 'TERRA'),
('CUI003', 'd.vader', 'Anakin', 'Skywalker', 'Umano', '1945-05-19', TRUE, 'Imperiale', 'Admin', 1, 'MARTE'),
('CUI004', 'h.solo', 'Han', 'Solo', 'Umano', '1955-03-13', FALSE, 'Neutrale', 'Capitano', NULL, 'GIOVE'),
('CUI005', 'c.palpatine', 'Sheev', 'Palpatine', 'Umano', '1920-01-01', TRUE, 'Imperiale', 'Admin', 2, 'SATURNO'),
('CUI006', 'c.rex', 'Rex', 'Clone', 'Clone', '2000-01-01', FALSE, 'Neutrale', 'Astronauta', NULL, 'TERRA'),
('CUI007', 's.snoke', 'Snoke', 'N/A', 'Sith', '1000-01-01', TRUE, 'Imperiale', 'Admin', 3, 'ALDEB'),
('CUI0011', 'jyn.erso', 'Jyn', 'Erso', 'Umano', '1990-01-01', TRUE, 'Ribelle', 'Astronauta', NULL, 'VALLT'),
('CUI0012', 'cassian.a', 'Cassian', 'Andor', 'Umano', '1985-01-01', TRUE, 'Ribelle', 'Capitano', NULL, 'FELOOK'),
('CUI0013', 'k2so.droid', 'K-2SO', 'Droid', 'Droide', '2000-01-01', FALSE, 'Ribelle', 'Astronauta', NULL, 'CYGNI'),
('CUI0014', 'grand.admiral', 'Thrawn', 'N/A', 'Chiss', '1970-01-01', TRUE, 'Imperiale', 'Admin', 4, 'CSAPL_II'),
('CUI0015', 'sabinewren', 'Sabine', 'Wren', 'Mandaloriana', '1995-01-01', FALSE, 'Ribelle', 'Astronauta', NULL, 'MANDALORE'),
('CUI0016', 'ezramiller', 'Ezra', 'Bridger', 'Umano', '1998-01-01', FALSE, 'Ribelle', 'Astronauta', NULL, 'LOTHAL'),
('CUI0017', 'chopper', 'Chopper', 'Droid', 'Droide', '2005-01-01', FALSE, 'Ribelle', 'Astronauta', NULL, 'ASTROMECH'),
('CUI0018', 'hera.syndulla', 'Hera', 'Syndulla', 'Twi''lek', '1980-01-01', FALSE, 'Ribelle', 'Capitano', NULL, 'RYLOTH'),
('CUI0019', 'kanan.jarrus', 'Kanan', 'Jarrus', 'Umano', '1975-01-01', FALSE, 'Ribelle', 'Astronauta', NULL, 'MYGEETO'),
('CUI0020', 'agent.kallus', 'Alexsandr', 'Kallus', 'Umano', '1970-01-01', FALSE, 'Imperiale', 'Admin', 5, 'KALLUS'),
('CUI0021', 'darth.maul', 'Maul', 'N/A', 'Zabrak', '1990-01-01', TRUE, 'Neutrale', 'Astronauta', 6, 'DAHOMIR');


-- Popolamento tabella ASTRONAVE
INSERT INTO ASTRONAVE (Targa, Nome, CodArea, NumeroPosto, CodModello, CUICapitano) VALUES
('FALCON77', 'Millennium Falcon', 1, 1, 'MOD002', 'CUI0004'),
('STARDESTR1', 'Executor', 2, 1, 'MOD011', 'CUI0003'),
('XWINGRED5', 'Red Five', 1, 2, 'MOD003', 'CUI0001'),
('CARGOALPHA', 'Ironclad', 2, 2, 'MOD002', 'CUI0004'),
('TIEFIGHTER', 'Black Squadron 1', 3, 1, 'MOD001', 'CUI0003'),
('SERENITY', 'Serenity', 1, 3, 'MOD004', 'CUI0008'),
('GHOST', 'Ghost', 4, 1, 'MOD010', 'CUI0018'),
('RAZORCREST', 'Razor Crest', 4, 2, 'MOD009', 'CUI0008'),
('STARFORGE', 'Star Forge', 5, 1, 'MOD011', 'CUI0003'),
('OUTRIDER', 'Outrider', 1, 4, 'MOD006', 'CUI0012'),
('ECLIPSE', 'Eclipse', 2, 3, 'MOD005', 'CUI0003'),
('VANGUARD', 'Vanguard', 3, 2, 'MOD007', 'CUI0001'),
('PHOENIX', 'Phoenix Home', 4, 3, 'MOD008', 'CUI0018'),
('SLAVE1', 'Slave I', 5, 2, 'MOD009', 'CUI0008');


-- Popolamento tabella EQUIPAGGIO
INSERT INTO EQUIPAGGIO (TargaAstronave, CUIAstronauta) VALUES
('FALCON123', 'CUI002'),
('FALCON123', 'CUI006'),
('XWING001', 'CUI002'),
('STAR100', 'CUI006');


-- Popolamento tabella TIPOLOGIA_VIAGGIO
INSERT INTO TIPOLOGIA_VIAGGIO (Nome) VALUES
('Commercio'),
('Esplorazione'),
('Trasporto Passeggeri'),
('Militare');

-- Popolamento tabella TIPOLOGIA_CARICO
INSERT INTO TIPOLOGIA_CARICO (Nome, Descrizione, CostoUnitario) VALUES
('Minerali', 'Materiali grezzi', 10.50),
('Tecnologia', 'Componenti elettronici', 150.00),
('Alimenti', 'Prodotti commestibili', 5.20),
('Armi', 'Equipaggiamento militare', 500.00);


-- Popolamento tabella RICHIESTA
INSERT INTO RICHIESTA (EntrataUscita, DataOra, Descrizione, CostoTotale, Esito, DataEsito, TargaAstronave, Scopo, PianetaProvenienza, PianetaDestinazione, GestitaDa) VALUES
('E', '2025-05-15 10:00:00', 'Richiesta di attracco per manutenzione', 150.00, 'A', '2025-05-15', 'FALCON123', 3, 'TERRA', 'TERRA', 'CUI005'),
('U', '2025-05-16 14:30:00', 'Richiesta di partenza per missione', 250.00, 'A', '2025-05-16', 'STAR100', 4, 'MARTE', 'GIOVE', 'CUI003'),
('E', '2025-05-17 08:00:00', 'Richiesta di rifornimento', 80.00, NULL, NULL, 'XWING001', 1, 'GIOVE', 'TERRA', NULL),
('U', '2025-05-18 11:00:00', 'Trasporto merci urgenti', 300.00, 'R', '2025-05-18', 'CARGO001', 1, 'SATURNO', 'MARTE', 'CUI005');


-- Popolamento tabella CARICO
INSERT INTO CARICO (Tipologia, Quantita, CodRichiesta) VALUES
(1, 100, 1),
(2, 5, 1),
(3, 50, 2),
(4, 2, 2),
(1, 200, 3);