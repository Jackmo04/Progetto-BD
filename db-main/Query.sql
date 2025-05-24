USE PortoMorteNera;

/*3.2.1 – Principali operazioni Astronauti Semplici */
/*S1 Creare un nuovo account persona registrando tutti i propri dati nell’applicazione.*/
INSERT INTO PERSONA (CUI, Username, Nome, Cognome, Razza, DataNascita, Ricercato, Ideologia, Ruolo, NumCella, PianetaNascita) VALUES
('SKWLKE510925T', 'L.Skywalker', 'Luke', 'Skywalker', 'Umano', '1951-09-25', FALSE, 'Neutrale', 'Astronauta', NULL, 'TATO002');

/*Java mode
INSERT INTO PERSONA (CUI, Username, Nome, Cognome, Razza, DataNascita, Ricercato, Ideologia, Ruolo, NumCella, PianetaNascita) VALUES
(?, ?, ?, ?, ?, ?, FALSE, 'Neutrale', 'Astronauta', NULL, ?);
*/

/*S2 Accedere al proprio account tramite il CUI o il proprio username e visualizzare le astronavi a cui si appartiene*/
select distinct n.targa, n.nome
from astronave n, persona p, equipaggio e
where ((p.CUI = e.CUIAstronauta and e.TargaAstronave = n.Targa) or (p.CUI = n.CUICapitano))
and p.CUI = 'STRMTR0000001';

/* Java
select distinct n.targa, n.nome
from astronave n, persona p, equipaggio e
where ((p.CUI = e.CUIAstronauta and e.TargaAstronave = n.Targa) or (p.CUI = n.CUICapitano))
and p.CUI = ?;
*/

/*S3 Visualizzare il posteggio della propria nave e l’area di attracco all’interno del porto ,
operazione effettuabile solo se la nave è attraccata*/
SELECT ast.NumeroPosto , ar.Nome AS 'Nome Area'
FROM astronave ast , area_attracco ar
WHERE ast.CodArea = ar.CodArea
AND Targa = 'TIEF0005';

/*Java mode
SELECT ast.NumeroPosto , ar.Nome as 'Nome Area'
from astronave ast , area_attracco ar
where ast.CodArea = ar.CodArea
and Targa = ?;
*/

/*S4 Visualizzare l’ultima richiesta effettuata dalla nave a cui siamo registrati*/
select r.*
from richiesta r, persona p, equipaggio e, astronave n
where ((p.CUI = e.CUIAstronauta and e.TargaAstronave = n.Targa) or (p.CUI = n.CUICapitano))
and r.TargaAstronave = n.Targa
and p.CUI = 'SLOHAN420713C'
and n.Targa = 'MFALC001'
order by r.DataOra desc
limit 1;

/* Java
select r.*
from richiesta r, persona p, equipaggio e, astronave n
where ((p.CUI = e.CUIAstronauta and e.TargaAstronave = n.Targa) or (p.CUI = n.CUICapitano))
and r.TargaAstronave = n.Targa
and p.CUI = ?
and n.Targa = ?
order by r.DataOra desc
limit 1;
*/


/*S5 Visualizzare le informazioni dettagliate di una richiesta.*/

select distinct r.CodRichiesta , r.EntrataUscita , r.DataOra , r.Descrizione , r.CostoTotale
, r.Esito , r.TargaAstronave ,  tv.Nome as TipologiaViaggio,
pnt1.Nome as PianetaDestinazione , pnt2.Nome as PianetaDestinazione,
c.Quantita , tc.Nome , tc.Descrizione
from richiesta r , persona p , tipologia_viaggio tv ,
pianeta pnt1 , pianeta pnt2 , carico c , tipologia_carico tc
where pnt1.CodPianeta = r.PianetaDestinazione
and pnt2.CodPianeta = r.PianetaProvenienza
and r.Scopo = tv.CodTipoViaggio
and r.CodRichiesta = c.CodRichiesta
and c.Tipologia = tc.CodTipoCarico
and r.CodRichiesta = 1;

/* Java mode
select distinct r.CodRichiesta , r.EntrataUscita , r.DataOra , r.Descrizione , r.CostoTotale
, r.Esito , r.TargaAstronave ,  tv.Nome as TipologiaViaggio,
pnt1.Nome as PianetaDestinazione , pnt2.Nome as PianetaDestinazione,
c.Quantita , tc.Nome , tc.Descrizione
from richiesta r , persona p , tipologia_viaggio tv ,
pianeta pnt1 , pianeta pnt2 , carico c , tipologia_carico tc
where pnt1.CodPianeta = r.PianetaDestinazione
and pnt2.CodPianeta = r.PianetaProvenienza
and r.Scopo = tv.CodTipoViaggio
and r.CodRichiesta = c.CodRichiesta
and c.Tipologia = tc.CodTipoCarico
and r.CodRichiesta = ? ; */

/* Principali operazioni Capitani */

/*C1 Registrare la propria nave all'applicazione. */

INSERT INTO ASTRONAVE (Targa, Nome, CodArea, NumeroPosto, CodModello, CUICapitano) VALUES
('MFALC001', 'Millennium Falcon', 2, 1, 'MF0002', 'SLOHAN420713C');

/*
INSERT INTO ASTRONAVE (Targa, Nome, CodArea, NumeroPosto, CodModello, CUICapitano) VALUES
(?, ?, NULL, NULL, ? , ?);
*/

/*C2 Aggiungere o rimuovere membri all’equipaggio di una nave */

/*C3 Visualizzare tutti i dati dei membri del proprio equipaggio*/
select p.*
from persona p , equipaggio e
where p.CUI = e.CUIAstronauta
and e.TargaAstronave = 'MFALC001';

/*Java mode 
select p.*
from persona p , equipaggio e
where p.CUI = e.CUIAstronauta
and e.TargaAstronave = ? ;
*/

/*C4 Richiedere accesso al porto*/

/*C5 Richiedere uscita dal porto*/

/*C6 Visualizzare lo storico completo delle richieste effettuate da una astronave, il
capitano potrà visualizzare solo le proprie.*/

/*C7 Visualizzare il costo di una richiesta di accesso o uscita.*/


/*3.2.3 – Principali operazioni Amministratori*/

/*A1 Visualizzare tutte le richieste pendenti*/

/*A2 Valutare una richiesta pendente*/

/*A3 Arrestare un astronauta rimuovendolo dall’equipaggio della propria astronave*/

/*A4 Visualizzare il numero di persone presenti nel porto attualmente*/

/*A5 Visualizzare la percentuale di richieste accettante e rifiutate in un dato intervallo di tempo*/

/*A6 Visualizzare posteggi liberi attualmente*/

/*A7 Visualizzare le 50 astronavi che hanno trasportato più merce.*/
