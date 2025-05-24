USE Porto;
/*3.2.1 – Principali operazioni Astronauti Semplici */
/*S1 Creare un nuovo account persona registrando tutti i propri dati nell’applicazione.*/
INSERT INTO PERSONA (CUI, Username, Nome, Cognome, Razza, DataNascita, Ricercato, Ideologia, Ruolo, NumCella, PianetaNascita) VALUES
('SKWLKE510925T', 'L.Skywalker', 'Luke', 'Skywalker', 'Umano', '1951-09-25', FALSE, 'Neutrale', 'Astronauta', NULL, 'TATO002');
/*Java mode
INSERT INTO PERSONA (CUI, Username, Nome, Cognome, Razza, DataNascita, Ricercato, Ideologia, Ruolo, NumCella, PianetaNascita) VALUES
(?, ?, ?, ?, ?, ?, FALSE, 'Neutrale', 'Astronauta', NULL, ?);
*/
/*S2 Accedere al proprio account tramite il CUI o il proprio username e visualizzare le astronavi a cui si appartiene*/

/*S3 Visualizzare il posteggio della propria nave e l’area di attracco all’interno del porto ,
operazione effettuabile solo se la nave è attraccata*/
SELECT ast.NumeroPosto , ar.Nome as 'Nome Area'
from astronave ast , area_attracco ar
where ast.CodArea = ar.CodArea
and Targa = 'TIEF0005';

/*Java mode
SELECT ast.NumeroPosto , ar.Nome as 'Nome Area'
from astronave ast , area_attracco ar
where ast.CodArea = ar.CodArea
and Targa = ?;
*/

/*S4 Visualizzare l’ultima richiesta effettuata dalla nave a cui siamo registrati*/

/*S5 Visualizzare le informazioni dettagliate di una richiesta.*/

/* Principali operazioni Capitani */

/*C1 Registrare la propria nave all'applicazione. */

/*C2 Aggiungere o rimuovere membri all’equipaggio di una nave */

/*C3 Visualizzare tutti i dati dei membri del proprio equipaggio*/

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
