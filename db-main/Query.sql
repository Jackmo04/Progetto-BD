USE PortoMorteNera;

-- _____________________________________________
/*
	S1 -- Creare un nuovo account persona registrando tutti i propri dati nell’applicazione.
*/
INSERT INTO PERSONE (CUI, Username, Nome, Cognome, Razza, DataNascita, Ricercato, Ideologia, Ruolo, PianetaNascita) VALUES
('SKWLKE510925T', 'L.Skywalker', 'Luke', 'Skywalker', 'Umano', '1951-09-25', FALSE, 'Neutrale', 'Astronauta', 'TATO002');

/* Java
INSERT INTO PERSONE (CUI, Username, Nome, Cognome, Razza, DataNascita, Ricercato, Ideologia, Ruolo, NumCella, PianetaNascita) VALUES
(?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
*/

-- _____________________________________________
/*
	S2 -- Accedere al proprio account tramite il CUI o il proprio username e visualizzare le astronavi a cui si appartiene
*/
SELECT DISTINCT n.targa, n.nome
FROM astronavi n, persone p, equipaggi e
WHERE ((p.CUI = e.CUIAstronauta AND e.TargaAstronave = n.Targa) OR (p.CUI = n.CUICapitano))
AND (p.CUI = 'STRMTR0000001' OR p.Username = 'Trooper1')
AND p.Password = 'pippo';

/* Java
SELECT DISTINCT n.targa, n.nome
FROM astronavi n, persone p, equipaggi e
WHERE ((p.CUI = e.CUIAstronauta AND e.TargaAstronave = n.Targa) OR (p.CUI = n.CUICapitano))
AND (p.CUI = ? OR p.Username = ?)
AND p.Password = ?;
*/

-- _____________________________________________
/*
	S3 -- Visualizzare il posteggio della propria nave e l’area di attracco all’interno del porto,
	operazione effettuabile solo se la nave è attraccata
*/
SELECT ast.CodArea, ar.Nome AS 'Nome Area', ast.NumeroPosto
FROM astronavi ast, aree_attracco ar
WHERE ast.CodArea = ar.CodArea
AND Targa = 'TIEF0005';

/* Java
SELECT ast.CodArea, ar.Nome AS 'Nome Area', ast.NumeroPosto
FROM astronavi ast, aree_attracco ar
WHERE ast.CodArea = ar.CodArea
AND Targa = ?;
*/

-- _____________________________________________
/*
	S4 -- Visualizzare l’ultima richiesta effettuata da una nave
*/
SELECT r.*
FROM richieste r, astronavi n
WHERE r.TargaAstronave = n.Targa
AND n.Targa = 'MFALC001'
ORDER BY r.DataOra DESC
LIMIT 1;

/* Java
SELECT r.*
FROM richieste r, astronavi n
WHERE r.TargaAstronave = n.Targa
AND n.Targa = ?
ORDER BY r.DataOra DESC
LIMIT 1;
*/

-- _____________________________________________
/*
	S5 -- Visualizzare le informazioni dettagliate di una richiesta.
*/
SELECT DISTINCT r.CodRichiesta, r.EntrataUscita, r.DataOra, r.Descrizione, r.CostoTotale, r.Esito, r.TargaAstronave, tv.Nome AS TipologiaViaggio,
	pnt1.Nome AS PianetaDestinazione, pnt2.Nome AS PianetaDestinazione, SUM(DISTINCT c.Quantita) AS QuantitaTotCarico
FROM richieste r, persone p, tipologie_viaggio tv, pianeti pnt1, pianeti pnt2, carichi c
WHERE pnt1.CodPianeta = r.PianetaDestinazione
AND pnt2.CodPianeta = r.PianetaProvenienza
AND r.Scopo = tv.CodTipoViaggio
AND r.CodRichiesta = c.CodRichiesta
AND r.CodRichiesta = 1
GROUP BY 1-10;

/* Java
SELECT DISTINCT r.CodRichiesta, r.EntrataUscita, r.DataOra, r.Descrizione, r.CostoTotale, r.Esito, r.TargaAstronave, tv.Nome AS TipologiaViaggio,
	pnt1.Nome AS PianetaDestinazione, pnt2.Nome AS PianetaDestinazione, SUM(DISTINCT c.Quantita) AS QuantitaTotCarico
FROM richieste r, persone p, tipologie_viaggio tv, pianeti pnt1, pianeti pnt2, carichi c
WHERE pnt1.CodPianeta = r.PianetaDestinazione
AND pnt2.CodPianeta = r.PianetaProvenienza
AND r.Scopo = tv.CodTipoViaggio
AND r.CodRichiesta = c.CodRichiesta
AND r.CodRichiesta = ?
GROUP BY 1-10;
*/

-- _____________________________________________
/*
	C1 -- Registrare la propria nave all'applicazione.
*/
-- Visualizz. scelta modello
SELECT CodModello, Nome
FROM Modelli;

INSERT INTO ASTRONAVI (Targa, Nome, CodModello, CUICapitano) VALUES
('MFALC001', 'Millennium Falcon', 'MF0002', 'SLOHAN420713C');

/* Java
INSERT INTO ASTRONAVI (Targa, Nome, CodModello, CUICapitano) VALUES
(?, ?, ?, ?);
*/

-- _____________________________________________
/*
	C2 -- Aggiungere o rimuovere membri all’equipaggio di una nave
*/
-- Aggiungere
INSERT INTO equipaggi (TargaAstronave, CUIAstronauta) VALUES
('XWING002', 'STRMTR0000003');

/* Java
INSERT INTO equipaggi (TargaAstronave, CUIAstronauta) VALUES
(?, ?);
*/

-- Rimuovere
DELETE FROM equipaggi e
WHERE e.TargaAstronave = 'XWING002'
AND e.CUIAstronauta = 'STRMTR0000003';

/* Java
DELETE FROM equipaggi e
WHERE e.TargaAstronave = ?
AND e.CUIAstronauta = ?;
*/

-- _____________________________________________
/*
	C3 -- Visualizzare tutti i dati dei membri del proprio equipaggio
*/
SELECT p.CUI, p.Nome, p.Cognome, p.Razza, p.DataNascita, p.Ricercato, pn.Nome AS PianetaNascita
FROM persone p, equipaggi e, pianeti pn
WHERE p.CUI = e.CUIAstronauta
AND p.PianetaNascita = pn.CodPianeta
AND e.TargaAstronave = 'MFALC001';

/* Java 
SELECT p.CUI, p.Nome, p.Cognome, p.Razza, p.DataNascita, p.Ricercato, pn.Nome AS PianetaNascita
FROM persone p, equipaggi e, pianeti pn
WHERE p.CUI = e.CUIAstronauta
AND p.PianetaNascita = pn.CodPianeta
AND e.TargaAstronave = ?;
*/

-- _____________________________________________
/*
	C4 -- Richiedere accesso al porto
*/
-- !! SELEZIONI SCOPO E PIANETI

INSERT INTO Richieste (EntrataUscita, Descrizione, CostoTotale, TargaAstronave, Scopo, PianetaProvenienza, PianetaDestinazione) VALUES
('E', 'PippoPluto2', 0, 'TIEF0005', 3, 'NABO004', 'DTHSTR0');

-- Se ci sono dei carichi
INSERT INTO Carichi (Tipologia, Quantita, CodRichiesta) VALUES
(1, 20, (SELECT CodRichiesta FROM Ultima_richiesta)),
(2, 5, (SELECT CodRichiesta FROM Ultima_richiesta ));

UPDATE richieste
SET CostoTotale = (SELECT Costo FROM Costo_ultima_richiesta)
WHERE CodRichiesta = (SELECT CodRichiesta FROM Ultima_richiesta);

/* Java
INSERT INTO Richieste (EntrataUscita, Descrizione, CostoTotale, TargaAstronave, Scopo, PianetaProvenienza, PianetaDestinazione) VALUES
('E', ?, 0, ?, ?, ?, 'DTHSTR0');

INSERT INTO Carichi (Tipologia, Quantita, CodRichiesta) VALUES
(?, ?, (SELECT CodRichiesta FROM Ultima_richiesta));

UPDATE richieste
SET CostoTotale = (SELECT Costo FROM Costo_ultima_richiesta)
WHERE CodRichiesta = (SELECT CodRichiesta FROM Ultima_richiesta);
*/
-- _____________________________________________
/*
	C5 -- Richiedere uscita dal porto
*/
-- Inseriamo la richiesta
INSERT INTO Richieste (EntrataUscita, Descrizione, CostoTotale, TargaAstronave, Scopo, PianetaProvenienza, PianetaDestinazione) VALUES
('U', 'PippoPluto2', 0, 'TIEF0005', 3, 'DTHSTR0', 'NABO004');

-- Inseriamo il carico all'ultima richiesta aggiunta
INSERT INTO Carichi (Tipologia, Quantita, CodRichiesta) VALUES
(1, 20, (SELECT CodRichiesta FROM Ultima_richiesta));

-- Inseriamo il costo totoale calcolato all'ultima richiesta
UPDATE richieste
SET CostoTotale = (SELECT Costo FROM Costo_ultima_richiesta)
WHERE CodRichiesta = (SELECT CodRichiesta FROM Ultima_richiesta);

/* Java
INSERT INTO Richieste (EntrataUscita, Descrizione, CostoTotale, TargaAstronave, Scopo, PianetaProvenienza, PianetaDestinazione) VALUES
('U', ?, ?, ?, ?, 'DTHSTR0', ?);

INSERT INTO Carichi (Tipologia, Quantita, CodRichiesta) VALUES
(?, ?, (SELECT CodRichiesta FROM Ultima_richiesta));

UPDATE richieste
SET CostoTotale = (SELECT Costo FROM Costo_ultima_richiesta)
WHERE CodRichiesta = (SELECT CodRichiesta FROM Ultima_richiesta);
*/

-- _____________________________________________
/*
	C6 -- Visualizzare lo storico completo delle richieste effettuate da una astronave.
*/
SELECT DISTINCT r.*
FROM richieste r, astronavi n
WHERE r.TargaAstronave = n.Targa
AND n.Targa = 'MFALC001';

/* Java
SELECT DISTINCT r.*
FROM richieste r, astronavi n
WHERE r.TargaAstronave = n.Targa
AND n.Targa = ?;
*/

-- _____________________________________________
/*
	C7 -- Visualizzare il costo di una richiesta di accesso o uscita.
*/
SELECT r.CostoTotale
FROM richieste r
WHERE r.CodRichiesta = 1;

/* Java 
SELECT r.CostoTotale
FROM richieste r
WHERE r.CodRichiesta = ?;
*/

-- _____________________________________________
/*
	A1 -- Visualizzare tutte le richieste pendenti
*/
SELECT * FROM Richieste_pendenti;

/*java
SELECT * FROM Richieste_pendenti;
*/

-- _____________________________________________
/*
	A2 -- Valutare una richiesta pendente
*/
UPDATE richieste
SET esito = 'A', dataEsito = NOW(), gestitaDa = 'PLPSHV201204N'
WHERE CodRichiesta = 3;

-- Se accettata:
UPDATE astronavi
SET codArea = 4, numeroPosto = 2
WHERE targa = (SELECT targaAstronave
			   FROM richieste r
               WHERE r.codRichiesta = 3
               AND r.esito = 'A');

/* Java
UPDATE richieste
SET esito = ?, dataEsito = NOW(), gestitaDa = ?
WHERE CodRichiesta = ?;

UPDATE astronavi
SET codArea = ?, numeroPosto = ?
WHERE targa = (SELECT targaAstronave
			   FROM richieste r
               WHERE r.codRichiesta = ?
               AND r.esito = 'A');
*/

-- _____________________________________________
/*
	A3 -- Arrestare un astronauta rimuovendolo dall’equipaggio della propria astronave
*/

-- Selezione celle non piene 
SELECT c.NumCella
FROM celle c
WHERE c.NumCella NOT IN (SELECT c1.NumCella
						 FROM celle c1
                         WHERE c1.Capienza <= (SELECT COUNT(p.NumCella)
											  FROM persone p
                                              WHERE p.NumCella = c1.NumCella
                                              GROUP BY p.NumCella));
                                              
-- Oppure                              

SELECT c.NumCella
FROM celle c
LEFT JOIN persone p ON c.NumCella = p.NumCella
GROUP BY c.NumCella, c.Capienza
HAVING COUNT(p.CUI) < c.Capienza;

-- Poi segna e rimuovi
UPDATE Persone
SET NumCella = 4
WHERE CUI = 'KNBOBI370825C';

DELETE FROM Equipaggi
WHERE CUIAstronauta = 'KNBOBI370825C';

/* Java
UPDATE Persone
SET NumCella = ?
WHERE CUI = ?;

DELETE FROM Equipaggi
WHERE CUIAstronauta = ?;
*/

-- _____________________________________________
/*
	A4 -- Visualizzare il numero di persone presenti nel porto attualmente
*/
SELECT COUNT(DISTINCT p.CUI) AS `Astronauti in porto`
FROM astronavi a, equipaggi e, persone p
WHERE ((p.CUI = e.CUIAstronauta AND e.TargaAstronave = a.Targa) OR (p.CUI = a.CUICapitano))
AND a.numeroPosto IS NOT NULL;

-- _____________________________________________
/*
	A5 -- Visualizzare la percentuale di richieste accettate e rifiutate in un dato intervallo di tempo
*/
SELECT 
	ROUND(cra.num * 100 / crt.num, 2) AS `% Accettate`,
    ROUND(crr.num * 100 / crt.num, 2) AS `% Rifiutate`
FROM (SELECT COUNT(*) num
	  FROM Richieste
      WHERE DataOra BETWEEN '2025-05-21 14:30:00' AND '2025-05-22 18:00:00'
      AND Esito IS NOT NULL) crt,
	 (SELECT COUNT(*) num
      FROM Richieste_accettate
      WHERE DataOra BETWEEN '2025-05-21 14:30:00' AND '2025-05-22 18:00:00') cra,
     (SELECT COUNT(*) num
      FROM Richieste_rifiutate
      WHERE DataOra BETWEEN '2025-05-21 14:30:00' AND '2025-05-22 18:00:00') crr;
                    
-- Oppure

WITH RichiesteInIntervallo AS (
	SELECT *
    FROM Richieste
    WHERE DataOra BETWEEN '2025-05-21 14:30:00' AND '2025-05-22 18:00:00'
    AND Esito IS NOT NULL
)
SELECT
	ROUND(SUM(CASE WHEN Esito = 'A' THEN 1 ELSE 0 END) * 100.0 / COUNT(*), 2) AS `% Accettate`,
    ROUND(SUM(CASE WHEN Esito = 'R' THEN 1 ELSE 0 END) * 100.0 / COUNT(*), 2) AS `% Rifiutate`
FROM RichiesteInIntervallo;

/* Java
WITH RichiesteInIntervallo AS (
	SELECT *
    FROM Richieste
    WHERE DataOra BETWEEN '2025-05-21 14:30:00' AND '2025-05-22 18:00:00'
    AND Esito IS NOT NULL
)
SELECT
	ROUND(SUM(CASE WHEN Esito = 'A' THEN 1 ELSE 0 END) * 100.0 / COUNT(*), 2) AS `% Accettate`,
    ROUND(SUM(CASE WHEN Esito = 'R' THEN 1 ELSE 0 END) * 100.0 / COUNT(*), 2) AS `% Rifiutate`
FROM RichiesteInIntervallo;
*/

-- _____________________________________________
/*  
	A6 -- Visualizzare posteggi liberi attualmente
*/
SELECT p.*
FROM posteggi p 
WHERE (p.codArea, p.numeroPosto) NOT IN (SELECT a.codArea, a.numeroPosto
										 FROM astronavi a);

-- _____________________________________________
/*
	A7 -- Visualizzare le 50 astronavi che hanno trasportato più merce.
*/
SELECT r.TargaAstronave, SUM(c.Quantita) QtaTot
FROM Richieste r, Carichi c
WHERE c.CodRichiesta = r.CodRichiesta
GROUP BY r.TargaAstronave
ORDER BY QtaTot DESC
LIMIT 50;
