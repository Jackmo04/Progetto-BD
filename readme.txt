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

Altrimenti, eseguire da terminale:
- Windows: `java -jar .\PortoMorteNera.jar`
- Linux/Mac: `java -jar ./PortoMorteNera.jar`

In caso di problemi, ricompilare il progetto eseguendo da terminale:
- Windows: `.\gradlew.bat clean run`
- Linux/Mac: `./gradlew clean run`

## Utilizzo applicazione
Una volta avviata l'applicazione è necessario effettuare il login.
È data la possibilità di registrarsi, perciò non servono credenziali apposite;
tuttavia proponiamo alcuni utenti già registrati per testare l'applicazione in base a ruoli diversi:

#### Astronauta semplice

| CUI           | Username     | Password |
| ------------- | ------------ | -------- |
| STRMTR0000001 | Trooper1     | 12345    |
| SKWLKE510925T | L.Skywalker  | 12345    |

#### Capitano

| CUI           | Username     | Password |
| ------------- | ------------ | -------- |
| SLOHAN420713C | H.Solo       | pippo    |
| MULDRT600322D | D.Maul       | pippo    |

#### Admin

| CUI           | Username     | Password |
| ------------- | ------------ | -------- |
| PLPSHV201204N | E.Palpatine  | admin    |
| TRKMFF220306M | M.Tarkin     | admin    |
