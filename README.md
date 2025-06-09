# Progetto corso Basi di Dati
# Istruzioni sull'avvio dell'applicativo

## Requisiti:
- Java 21+
- MySQL 8.0+

## Creazione del database MySQL in locale:
- Avviare lo script `./DB/PortoMorteNera.sql` su una connessione localhost di MySQL
- L'applicazione accede come utente root, senza necessitare di alcuna password

## Avvio dell'applicazione:
Se si ha Java per desktop installato:
- Fare doppio clic sul file `./PortoMorteNera.jar`

Altrimenti:
- Da terminale digitare `java -jar .\PortoMorteNera.jar`

Se non dovesse funzionare, digitare su terminale:
Windows:
    `.\gradlew.bat run`
Linux/Mac:
    `./gradlew run`

## Utilizzo applicazione
Una volta avviata l'applicazione è necessario effettuare il login.
È data la possibilità di registrarsi, perciò non servono credenziali apposite;
tuttavia proponiamo alcuni utenti già registrati per testare l'applicazione in base a ruoli diversi:

- Astronauta semplice:
    Username: Trooper1 | Password: 12345
    Username: L.Skywalker | Password: 12345

- Capitano:
    Username: H.Solo | Password: pippo
    Username: D.Maul | Password: pippo

- Admin:
    Username: E.Palpatine | Password: admin
    Username: M.Tarkin | Password: admin
