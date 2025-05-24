USE PortoMorteNera;

-- _____________________________________________
/*
	S1 -- Creare un nuovo account persona registrando tutti i propri dati nell’applicazione.
*/
INSERT INTO PERSONE (CUI, Username, Nome, Cognome, Razza, DataNascita, Ricercato, Ideologia, Ruolo, NumCella, PianetaNascita) VALUES
('SKWLKE510925T', 'L.Skywalker', 'Luke', 'Skywalker', 'Umano', '1951-09-25', FALSE, 'Neutrale', 'Astronauta', NULL, 'TATO002');

/* Java
INSERT INTO PERSONE (CUI, Username, Nome, Cognome, Razza, DataNascita, Ricercato, Ideologia, Ruolo, NumCella, PianetaNascita) VALUES
(?, ?, ?, ?, ?, ?, FALSE, 'Neutrale', 'Astronauta', NULL, ?);
*/

-- _____________________________________________
/*
	S2 -- Accedere al proprio account tramite il CUI o il proprio username e visualizzare le astronavi a cui si appartiene
*/
select distinct n.targa, n.nome
from astronavi n, persone p, equipaggi e
where ((p.CUI = e.CUIAstronauta and e.TargaAstronave = n.Targa) or (p.CUI = n.CUICapitano))
and p.CUI = 'STRMTR0000001';

/* Java
select distinct n.targa, n.nome
from astronavi n, persone p, equipaggi e
where ((p.CUI = e.CUIAstronauta and e.TargaAstronave = n.Targa) or (p.CUI = n.CUICapitano))
and p.CUI = ?;
*/

-- _____________________________________________
/*
	S3 -- Visualizzare il posteggio della propria nave e l’area di attracco all’interno del porto,
	operazione effettuabile solo se la nave è attraccata
*/
SELECT ast.NumeroPosto , ar.Nome AS 'Nome Area'
FROM astronavi ast , aree_attracco ar
WHERE ast.CodArea = ar.CodArea
AND Targa = 'TIEF0005';

/* Java
SELECT ast.NumeroPosto , ar.Nome as 'Nome Area'
from astronavi ast , aree_attracco ar
where ast.CodArea = ar.CodArea
and Targa = ?;
*/

-- _____________________________________________
/*
	S4 -- Visualizzare l’ultima richiesta effettuata dalla nave a cui siamo registrati
*/
select r.*
from richieste r, persone p, equipaggi e, astronavi n
where ((p.CUI = e.CUIAstronauta and e.TargaAstronave = n.Targa) or (p.CUI = n.CUICapitano))
and r.TargaAstronave = n.Targa
and p.CUI = 'SLOHAN420713C'
and n.Targa = 'MFALC001'
order by r.DataOra desc
limit 1;

/* Java
select r.*
from richieste r, persone p, equipaggi e, astronavi n
where ((p.CUI = e.CUIAstronauta and e.TargaAstronave = n.Targa) or (p.CUI = n.CUICapitano))
and r.TargaAstronave = n.Targa
and p.CUI = ?
and n.Targa = ?
order by r.DataOra desc
limit 1;
*/

-- _____________________________________________
/*
	S5 -- Visualizzare le informazioni dettagliate di una richiesta.
*/
select distinct r.CodRichiesta , r.EntrataUscita , r.DataOra , r.Descrizione , r.CostoTotale
, r.Esito , r.TargaAstronave ,  tv.Nome as TipologiaViaggio,
pnt1.Nome as PianetaDestinazione , pnt2.Nome as PianetaDestinazione,
c.Quantita , tc.Nome , tc.Descrizione
from richieste r , persone p , tipologie_viaggio tv ,
pianeti pnt1 , pianeti pnt2 , carichi c , tipologie_carico tc
where pnt1.CodPianeta = r.PianetaDestinazione
and pnt2.CodPianeta = r.PianetaProvenienza
and r.Scopo = tv.CodTipoViaggio
and r.CodRichiesta = c.CodRichiesta
and c.Tipologia = tc.CodTipoCarico
and r.CodRichiesta = 1;

/* Java
select distinct r.CodRichiesta , r.EntrataUscita , r.DataOra , r.Descrizione , r.CostoTotale
, r.Esito , r.TargaAstronave ,  tv.Nome as TipologiaViaggio,
pnt1.Nome as PianetaDestinazione , pnt2.Nome as PianetaDestinazione,
c.Quantita , tc.Nome , tc.Descrizione
from richieste r , persone p , tipologie_viaggio tv ,
pianeti pnt1 , pianeti pnt2 , carichi c , tipologie_carico tc
where pnt1.CodPianeta = r.PianetaDestinazione
and pnt2.CodPianeta = r.PianetaProvenienza
and r.Scopo = tv.CodTipoViaggio
and r.CodRichiesta = c.CodRichiesta
and c.Tipologia = tc.CodTipoCarico
and r.CodRichiesta = ? ; */

-- _____________________________________________
/*
	C1 -- Registrare la propria nave all'applicazione.
*/
INSERT INTO ASTRONAVI (Targa, Nome, CodArea, NumeroPosto, CodModello, CUICapitano) VALUES
('MFALC001', 'Millennium Falcon', 2, 1, 'MF0002', 'SLOHAN420713C');

/* Java
INSERT INTO ASTRONAVI (Targa, Nome, CodArea, NumeroPosto, CodModello, CUICapitano) VALUES
(?, ?, NULL, NULL, ? , ?);
*/

-- _____________________________________________
/*
	C2 -- Aggiungere o rimuovere membri all’equipaggio di una nave
*/
-- Aggiungere
insert into equipaggi (TargaAstronave, CUIAstronauta) values
('XWING002', 'STRMTR0000003');

/* Java
insert into equipaggi (TargaAstronave, CUIAstronauta) values
(?, ?);
*/

-- Rimuovere
delete from equipaggi e
where e.TargaAstronave = 'XWING002'
and e.CUIAstronauta = 'STRMTR0000003';

/* Java
delete from equipaggi e
where e.TargaAstronave = ?
and e.CUIAstronauta = ?;
*/

-- _____________________________________________
/*
	C3 -- Visualizzare tutti i dati dei membri del proprio equipaggio
*/
select p.*
from persone p , equipaggi e
where p.CUI = e.CUIAstronauta
and e.TargaAstronave = 'MFALC001';

/* Java 
select p.*
from persone p , equipaggi e
where p.CUI = e.CUIAstronauta
and e.TargaAstronave = ? ;
*/

-- _____________________________________________
/*
	C4 -- Richiedere accesso al porto
*/
insert into Richieste (EntrataUscita, Descrizione, CostoTotale, TargaAstronave, Scopo, PianetaProvenienza, PianetaDestinazione) values
('U', 'PippoPluto2', 1500.00, 'TIEF0005', 3, 'DTHSTR0', 'NABO004');

/* Java
insert into Richieste (EntrataUscita, Descrizione, CostoTotale, TargaAstronave, Scopo, PianetaProvenienza, PianetaDestinazione) values
('U', ?, ?, ?, ?, 'DTHSTR0', ?);
*/

-- _____________________________________________
/*
	C5 -- Richiedere uscita dal porto
*/


-- _____________________________________________
/*
	C6 -- Visualizzare lo storico completo delle richieste effettuate da una astronave, il
	capitano potrà visualizzare solo le proprie.
*/
select r.*
from richieste r, persone p, astronavi n
where p.CUI = n.CUICapitano
and r.TargaAstronave = n.Targa
and p.CUI = 'SLOHAN420713C'
and n.Targa = 'MFALC001'
order by r.DataOra desc;

/* Java
select r.*
from richieste r, persone p, astronavi n
where p.CUI = n.CUICapitano
and r.TargaAstronave = n.Targa
and p.CUI = ?
and n.Targa = ?
order by r.DataOra desc;
*/

-- _____________________________________________
/*
	C7 -- Visualizzare il costo di una richiesta di accesso o uscita.
*/


-- _____________________________________________
/*
	A1 -- Visualizzare tutte le richieste pendenti
*/


-- _____________________________________________
/*
	A2 -- Valutare una richiesta pendente
*/
update richieste
set esito = 'A', dataEsito = date(now()), gestitaDa = 'PLPSHV201204N'
where CodRichiesta = 3;

-- Se accettata:
update astronavi
set codArea = 4, numeroPosto = 2
where targa = (select targaAstronave
			   from richieste r
               where r.codRichiesta = 3
               and r.esito = 'A');

/* Java
update richieste
set esito = ?, dataEsito = date(now()), gestitaDa = ?
where CodRichiesta = ?;

update astronavi
set codArea = ?, numeroPosto = ?
where targa = (select targaAstronave
			   from richieste r
               where r.codRichiesta = ?
               and r.esito = 'A');
*/

-- _____________________________________________
/*
	A3 -- Arrestare un astronauta rimuovendolo dall’equipaggio della propria astronave
*/


-- _____________________________________________
/*
	A4 -- Visualizzare il numero di persone presenti nel porto attualmente
*/
select count(distinct p.CUI) as `Astronauti in porto`
from astronavi a, equipaggi e, persone p
where ((p.CUI = e.CUIAstronauta and e.TargaAstronave = a.Targa) or (p.CUI = a.CUICapitano))
and a.numeroPosto is not null;

-- _____________________________________________
/*
	A5 -- Visualizzare la percentuale di richieste accettante e rifiutate in un dato intervallo di tempo
*/


-- _____________________________________________
/*  
	A6 -- Visualizzare posteggi liberi attualmente
*/
select p.*
from posteggi p 
where (p.codArea, p.numeroPosto) not in (select a.codArea, a.numeroPosto
										 from astronavi a);

-- _____________________________________________
/*
	A7 -- Visualizzare le 50 astronavi che hanno trasportato più merce.
*/
