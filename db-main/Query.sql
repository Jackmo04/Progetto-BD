USE PortoMorteNera;

-- _____________________________________________
/*
	S1 -- Creare un nuovo account persona registrando tutti i propri dati nell’applicazione.
    {Person} [MATTIA] Fatto
*/
INSERT INTO PERSONE (CUI, Username,Password , Nome, Cognome, Razza, DataNascita, Ideologia, Ruolo, PianetaNascita) VALUES
('PROVA', 'L.Skywalker', '' , 'Luke', 'Skywalker', 'Umano', '1951-09-25', 'Neutrale', 'Astronauta', 'TATO002');

/* Java [->Persona]
INSERT INTO PERSONE (CUI, Username, Password , Nome, Cognome, Razza, DataNascita, Ideologia, Ruolo, NumCella, PianetaNascita) VALUES
(?, ?, ?, ?, ?, ?, ?, ?, ?,?);
*/

-- _____________________________________________
/*
	S2a -- Accedere al proprio account tramite il CUI o il proprio username
    {Person} [Mattia] Fatto
*/
SELECT p.*
FROM persone p
WHERE 'Trooper1' IN (p.CUI, p.Username)
AND p.Password = 'pippo';

/* Java [Optional<Persona>]
SELECT p.*
FROM persone p
WHERE ? IN (p.CUI, p.Username)
AND p.Password = ?;
*/

/*
	S2b -- Visualizzare le astronavi a cui appartiene una persona
    {Starship} [Matteo] Fatto
*/
SELECT DISTINCT n.*
FROM astronavi n, persone p, equipaggi e
WHERE ((p.CUI = e.CUIAstronauta AND e.TargaAstronave = n.Targa) OR (p.CUI = n.CUICapitano))
AND p.CUI = 'STRMTR0000001';


/* Java [Set<Astronave>]
SELECT DISTINCT n.*
FROM astronavi n, persone p, equipaggi e
WHERE ((p.CUI = e.CUIAstronauta AND e.TargaAstronave = n.Targa) OR (p.CUI = n.CUICapitano))
AND p.CUI = ?;
*/

-- _____________________________________________
/*
	S3 -- Visualizzare il posteggio della propria nave e l’area di attracco all’interno del porto
    {Starship} [Matteo] Fatto
*/
SELECT ast.CodArea, ar.Nome AS 'Nome Area', ast.NumeroPosto
FROM astronavi ast, aree_attracco ar
WHERE ast.CodArea = ar.CodArea
AND Targa = 'TIEF0005';

/* Java [Optional<Posteggio>]
SELECT CodArea, NumeroPosto
FROM astronavi
WHERE Targa = ?;
*/

-- _____________________________________________
/*
	S4 -- Visualizzare l’ultima richiesta effettuata da una nave
    {Request} [Mattia] Da fare
*/
SELECT r.*
FROM richieste r, astronavi n
WHERE r.TargaAstronave = n.Targa
AND n.Targa = 'MFALC001'
ORDER BY r.DataOra DESC
LIMIT 1;

/* Java [Optional<Richiesta>]
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
    {Request} [Mattia] fatta
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

/* Java [Optional<Request>]
SELECT r.*
FROM richeste
WHERE CodRichiesta = ?;
*/

-- _____________________________________________
/*
	C1 -- Registrare la propria nave all'applicazione.
    {Starship} [Matteo] Fatto
*/
-- Visualizz. scelta modello {ShipModel} [Matteo] Fatto
SELECT CodModello, Nome
FROM Modelli;

/* Java [List<Modello>]
SELECT CodModello, Nome
FROM Modelli;
*/

INSERT INTO ASTRONAVI (Targa, Nome, CodModello, CUICapitano) VALUES
('MFALC001', 'Millennium Falcon', 'MF0002', 'SLOHAN420713C');

/* Java [Astronave->null]
INSERT INTO ASTRONAVI (Targa, Nome, CodModello, CUICapitano) VALUES
(?, ?, ?, ?);
*/

-- _____________________________________________
/*
	C2 -- Aggiungere o rimuovere membri all’equipaggio di una nave
    {Starship} [Matteo] Fatto
*/
-- Aggiungere
INSERT INTO equipaggi (TargaAstronave, CUIAstronauta) VALUES
('XWING002', 'STRMTR0000003');

/* Java [Equipaggio->null]
INSERT INTO equipaggi (TargaAstronave, CUIAstronauta) VALUES
(?, ?);
*/

-- Rimuovere {Starship} [Matteo] Fatto
DELETE FROM equipaggi e
WHERE e.TargaAstronave = 'XWING002'
AND e.CUIAstronauta = 'STRMTR0000003';

/* Java [Equipaggio->null]
DELETE FROM equipaggi e
WHERE e.TargaAstronave = ?
AND e.CUIAstronauta = ?;
*/

-- _____________________________________________
/*
	C3 -- Visualizzare tutti i dati dei membri dell'equipaggio di una propria nave
    {Person} [Mattia] Da fare
*/
SELECT p.CUI
FROM persone p, equipaggi e, pianeti pn
WHERE p.CUI = e.CUIAstronauta
AND p.PianetaNascita = pn.CodPianeta
AND e.TargaAstronave = 'MFALC001';

/* Java [List<Person>]
SELECT p.CUI
FROM persone p, equipaggi e, pianeti pn
WHERE p.CUI = e.CUIAstronauta
AND e.TargaAstronave = ?;
*/

-- _____________________________________________
/*
	C4 -- Richiedere accesso al porto
    {Request} [Matteo] Da fare
*/
-- !! SELEZIONI SCOPO E PIANETI [Matteo] Fatto

INSERT INTO Richieste (EntrataUscita, Descrizione, CostoTotale, TargaAstronave, Scopo, PianetaProvenienza, PianetaDestinazione) VALUES
('E', 'PippoPluto2', 0, 'TIEF0005', 3, 'NABO004', 'DTHSTR0');

-- C45a Se ci sono dei carichi {Payload} [?] Da fare
INSERT INTO Carichi (Tipologia, Quantita, CodRichiesta) VALUES
(1, 20, (SELECT CodRichiesta FROM Ultima_richiesta)),
(2, 5, (SELECT CodRichiesta FROM Ultima_richiesta ));

UPDATE richieste
SET CostoTotale = (SELECT Costo FROM Costo_ultima_richiesta)
WHERE CodRichiesta = (SELECT CodRichiesta FROM Ultima_richiesta);

/* Java [Richiesta->null]
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
    {Request} [Matteo] Da fare
*/
-- Inseriamo la richiesta
INSERT INTO Richieste (EntrataUscita, Descrizione, CostoTotale, TargaAstronave, Scopo, PianetaProvenienza, PianetaDestinazione) VALUES
('U', 'PippoPluto2', 0, 'TIEF0005', 3, 'DTHSTR0', 'NABO004');

-- Inseriamo il carico all'ultima richiesta aggiunta {Payload} [Matteo] Da fare
INSERT INTO Carichi (Tipologia, Quantita, CodRichiesta) VALUES
(1, 20, (SELECT CodRichiesta FROM Ultima_richiesta));

-- Inseriamo il costo totoale calcolato all'ultima richiesta
UPDATE richieste
SET CostoTotale = (SELECT Costo FROM Costo_ultima_richiesta)
WHERE CodRichiesta = (SELECT CodRichiesta FROM Ultima_richiesta);

/* Java [Richiesta->null]
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
    {Request} [Mattia] Da fare
*/
SELECT DISTINCT r.*
FROM richieste r, astronavi n
WHERE r.TargaAstronave = n.Targa
AND n.Targa = 'MFALC001';

/* Java [List<Richiesta>]
SELECT DISTINCT r.*
FROM richieste r, astronavi n
WHERE r.TargaAstronave = n.Targa
AND n.Targa = ?;
*/

-- _____________________________________________
/*
	C7 -- Visualizzare il costo di una richiesta di accesso o uscita.
    {Request} [?] Da fare
*/


-- _____________________________________________
/*
	A1 -- Visualizzare tutte le richieste pendenti
    {Request} [?] Da fare
*/
SELECT * FROM Richieste_pendenti;

/* Java [Set<Richiesta> getPending()]
SELECT * FROM Richieste_pendenti;
*/

-- _____________________________________________
/*
	A2 -- Valutare una richiesta pendente
    {Request} [?] Da fare
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

/* Java [Richiesta]
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
	A3a -- Visualizza le celle disponibili
    {Cell} [Mattia] Fatto
*/
/* old
SELECT c.NumCella
FROM celle c
WHERE c.NumCella NOT IN (SELECT c1.NumCella
						 FROM celle c1
                         WHERE c1.Capienza <= (SELECT COUNT(p.NumCella)
											  FROM persone p
                                              WHERE p.NumCella = c1.NumCella
                                              GROUP BY p.NumCella));                         
*/

SELECT c.*
FROM celle c
LEFT JOIN persone p ON c.NumCella = p.NumCella
GROUP BY c.NumCella, c.Capienza
HAVING COUNT(p.CUI) < c.Capienza;

/* Java [List<Cella>] */

-- _____________________________________________
/*
	A3b -- Arrestare un astronauta rimuovendolo dall’equipaggio della propria astronave
    {Person} [Mattia] Fatto
*/

UPDATE Persone
SET NumCella = 4
WHERE CUI = 'KNBOBI370825C';

DELETE FROM Equipaggi
WHERE CUIAstronauta = 'KNBOBI370825C';

/* Java [Persona non static]
UPDATE Persone
SET NumCella = ?
WHERE CUI = ?;

DELETE FROM Equipaggi
WHERE CUIAstronauta = ?;
*/

-- _____________________________________________
/*
	A4 -- Visualizzare il numero di persone presenti nel porto attualmente
    {ParkingSpace} [Matteo] Fatto
*/
SELECT COUNT(DISTINCT p.CUI) AS `Astronauti in porto`
FROM astronavi a, equipaggi e, persone p
WHERE ((p.CUI = e.CUIAstronauta AND e.TargaAstronave = a.Targa) OR (p.CUI = a.CUICapitano))
AND a.numeroPosto IS NOT NULL;

/* Java [->int] */

-- _____________________________________________
/*
	A5 -- Visualizzare la percentuale di richieste accettate e rifiutate in un dato intervallo di tempo
    {Request} [?] Da fare
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

/* Java [->Pair<Double,Double>]
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
    {ParkingSpace} [Matteo] Fatto
*/
SELECT p.*
FROM posteggi p 
WHERE (p.codArea, p.numeroPosto) NOT IN (SELECT a.codArea, a.numeroPosto
										 FROM astronavi a
                                         WHERE a.numeroPosto IS NOT NULL);
						
/* Java [List<Posteggio>] */

-- _____________________________________________
/*
	A7 -- Visualizzare le 50 astronavi che hanno trasportato più merce.
    {Starship} [Matteo] Fatto
*/
SELECT r.TargaAstronave, SUM(c.Quantita) QtaTot
FROM Richieste r, Carichi c
WHERE c.CodRichiesta = r.CodRichiesta
GROUP BY r.TargaAstronave
ORDER BY QtaTot DESC
LIMIT 50;

/* Java [Map<Astronave, Integer>] */
