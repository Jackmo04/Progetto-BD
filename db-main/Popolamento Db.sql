-- Usa il database Porto
USE Porto;

-- Popolamento della tabella PIANETA
INSERT INTO PIANETA (CodPianeta, Nome) VALUES
('CORU001', 'Coruscant'),
('TATO002', 'Tatooine'),
('HOTH003', 'Hoth'),
('NABO004', 'Naboo'),
('ALDE005', 'Alderaan'),
('KASH006', 'Kashyyyk'),
('MUST007', 'Mustafar'),
('GEON008', 'Geonosis'),
('ENDOR009', 'Endor'),
('DANT010', 'Dantooine');

-- Popolamento della tabella DIMENSIONE_PREZZO
INSERT INTO DIMENSIONE_PREZZO (Superficie, Prezzo) VALUES
(50, 100.00),
(100, 180.00),
(200, 350.00),
(500, 800.00),
(1000, 1500.00);

-- Popolamento della tabella MODELLO
INSERT INTO MODELLO (CodModello, Nome, DimensioneArea) VALUES
('XW0001', 'X-Wing', 50),
('MF0002', 'Millennium Falcon', 100),
('SD0003', 'Star Destroyer', 1000),
('TIF004', 'TIE Fighter', 50),
('CR9005', 'Corvette CR90', 200),
('GR7506', 'Trasporto GR-75', 200);


-- Popolamento della tabella CELLA
INSERT INTO CELLA (NumCella, Capienza) VALUES
(1, 5),
(2, 3),
(3, 10),
(4, 2),
(5, 7);

-- Popolamento della tabella PERSONA
INSERT INTO PERSONA (CUI, Username, Nome, Cognome, Razza, DataNascita, Ricercato, Ideologia, Ruolo, NumCella, PianetaNascita) VALUES
('CUI001LUKE', 'LSkywalker', 'Luke', 'Skywalker', 'Umano', '1951-09-25', FALSE, 'Ribelle', 'Astronauta', NULL, 'TATO002'),
('CUI002HANS', 'HSolo', 'Han', 'Solo', 'Umano', '1942-07-13', FALSE, 'Neutrale', 'Capitano', NULL, 'CORU001'),
('CUI003LEIA', 'LOrgana', 'Leia', 'Organa', 'Umano', '1951-09-25', FALSE, 'Ribelle', 'Astronauta', NULL, 'ALDE005'),
('CUI004VADE', 'DVader', 'Anakin', 'Skywalker', 'Umano', '1941-04-19', TRUE, 'Imperiale', 'Capitano', 1, 'TATO002'),
('CUI005EMPE', 'EPalatine', 'Sheev', 'Palpatine', 'Umano', '1920-12-04', TRUE, 'Imperiale', 'Admin', NULL, 'NABO004'),
('CUI006CHEW', 'Chewie', 'Chewbacca', 'unknow', 'Wookiee', '1900-01-01', FALSE, 'Ribelle', 'Astronauta', NULL, 'KASH006'),
('CUI007OBI', 'OKenobi', 'Obi-Wan', 'Kenobi', 'Umano', '1937-08-25', FALSE, 'Ribelle', 'Astronauta', NULL, 'CORU001'),
('CUI008MAUL', 'DMaul', 'Darth', 'Maul', 'Zabrak', '1960-03-22', TRUE, 'Imperiale', 'Capitano', 2, 'DANT010');

-- Popolamento della tabella AREA_ATTRACCO
INSERT INTO AREA_ATTRACCO (CodArea, Nome) VALUES
(1, 'Alpha'),
(2, 'Beta'),
(3, 'Gamma'),
(4, 'Delta');

-- Popolamento della tabella POSTEGGIO
INSERT INTO POSTEGGIO (CodArea, NumeroPosto) VALUES
(1, 1), (1, 2), (1, 3),
(2, 1), (2, 2), (2, 3),
(3, 1), (3, 2), (3, 3), (3, 4), (3, 5),
(4, 1), (4, 2);

-- Popolamento della tabella ASTRONAVE
INSERT INTO ASTRONAVE (Targa, Nome, CodArea, NumeroPosto, CodModello, CUICapitano) VALUES
('MFALC001', 'Millennium Falcon', 2, 1, 'MF0002', 'CUI002HANS'),
('XWING002', 'Red Five', 3, 1, 'XW0001', 'CUI001LUKE'),
('STARD003', 'Executor', 3, 5, 'SD0003', 'CUI004VADE'),
('CR90004', 'Tantive IV', 4, 1, 'CR9005', 'CUI003LEIA'),
('TIEF005', 'Black Squadron 1', 3, 2, 'TIF004', 'CUI008MAUL');

-- Popolamento della tabella EQUIPAGGIO
INSERT INTO EQUIPAGGIO (CUIAstronauta, TargaAstronave) VALUES
('CUI001LUKE', 'XWING002'),
('CUI002HANS', 'MFALC001'),
('CUI003LEIA', 'CR90004'),
('CUI004VADE', 'STARD003'),
('CUI006CHEW', 'MFALC001'),
('CUI007OBI', 'CR90004'),
('CUI008MAUL', 'TIEF005');

-- Popolamento della tabella TIPOLOGIA_VIAGGIO
INSERT INTO TIPOLOGIA_VIAGGIO (CodTipoViaggio, Nome) VALUES
(1, 'Commercio di spezie'),
(2, 'Missione diplomatica'),
(3, 'Pattugliamento imperiale'),
(4, 'Trasporto ribelle'),
(5, 'Esplorazione');

-- Popolamento della tabella TIPOLOGIA_CARICO
INSERT INTO TIPOLOGIA_CARICO (CodTipoCarico, Nome, Descrizione, CostoUnitario) VALUES
(1, 'Spezie', 'Carico illegale di spezie', 500.00),
(2, 'Armi', 'Armi per la Ribellione', 1200.00),
(3, 'Dati', 'Informazioni classificate', 2000.00),
(4, 'Droidi', 'Componenti per droidi', 150.00),
(5, 'Alimentari', 'Razioni standard', 50.00);

-- Popolamento della tabella RICHIESTA
INSERT INTO RICHIESTA (CodRichiesta, EntrataUscita, DataOra, Descrizione, CostoTotale, Esito, DataEsito, TargaAstronave, Scopo, PianetaProvenienza, PianetaDestinazione, GestitaDa) VALUES
(1, 'E', '2025-05-20 10:00:00', 'Arrivo per scarico spezie', 1800.00, 'A', '2025-05-20', 'MFALC001', 1, 'TATO002', 'CORU001', 'CUI005EMPE'),
(2, 'U', '2025-05-21 14:30:00', 'Partenza per missione diplomatica', 350.00, 'A', '2025-05-21', 'CR90004', 2, 'CORU001', 'ALDE005', 'CUI005EMPE'),
(3, 'E', '2025-05-22 08:00:00', 'Rifornimento e manutenzione', 150.00, NULL, NULL, 'XWING002', 4, 'HOTH003', 'CORU001', NULL),
(4, 'U', '2025-05-22 18:00:00', 'Pattuglia settore 7', 800.00, 'A', '2025-05-22', 'STARD003', 3, 'CORU001', 'GEON008', 'CUI005EMPE'),
(5, 'E', '2025-05-23 09:15:00', 'Arrivo carico armi', 1000.00, NULL, NULL, 'MFALC001', 1, 'NABO004', 'CORU001', NULL);

-- Popolamento della tabella CARICO
INSERT INTO CARICO (Tipologia, Quantita, CodRichiesta) VALUES
(1, 3, 1),
(2, 1, 5),
(4, 10, 3);