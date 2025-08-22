# Progetto corso Basi di Dati
[*English version*](#database-class-project)
# Istruzioni sull'avvio dell'applicativo

## Requisiti:
- Java 21+
- MySQL 8.0+
- Scaricare i file `PortoMorteNera.sql` e `PortoMorteNera.jar` dal più recente [Release](https://github.com/Jackmo04/Progetto-BD/releases)

## Creazione del database MySQL in locale:
- Avviare lo script `PortoMorteNera.sql` su una connessione localhost di MySQL
- L'applicazione accede come utente root, senza necessitare di alcuna password

## Avvio dell'applicazione:
Se si ha Java per desktop installato:
- Fare doppio clic sul file `PortoMorteNera.jar`

Altrimenti, eseguire da terminale:
- Windows: `java -jar .\PortoMorteNera.jar`
- Linux/Mac: `java -jar ./PortoMorteNera.jar`

In caso di problemi, clonare il repo e ricompilare il progetto eseguendo da terminale:
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

# Database class project
# Instructions

## Requirements:
- Java 21+
- MySQL 8.0+
- Download the files `PortoMorteNera.sql` and `PortoMorteNera.jar` from the latest [Release](https://github.com/Jackmo04/Progetto-BD/releases)

## Creating the local MySQL database:
- Launch `PortoMorteNera.sql` on a localhost MySQL connection
- The app logs in as root with no password

## Launching the application:
If you have Java Desktop installed:
- Double-click `PortoMorteNera.jar`

Otherwise, type in a terminal shell:
- Windows: `java -jar .\PortoMorteNera.jar`
- Linux/Mac: `java -jar ./PortoMorteNera.jar`

If the app doesn't start, try cloning this repository and running:
- Windows: `.\gradlew.bat clean run`
- Linux/Mac: `./gradlew clean run`

## Using tha app
Once the app launches you'll need to login or register.
Here are some pre-made credentials to test the application:

#### Astronaut

| CUI           | Username     | Password |
| ------------- | ------------ | -------- |
| STRMTR0000001 | Trooper1     | 12345    |
| SKWLKE510925T | L.Skywalker  | 12345    |

#### Captain

| CUI           | Username     | Password |
| ------------- | ------------ | -------- |
| SLOHAN420713C | H.Solo       | pippo    |
| MULDRT600322D | D.Maul       | pippo    |

#### Admin

| CUI           | Username     | Password |
| ------------- | ------------ | -------- |
| PLPSHV201204N | E.Palpatine  | admin    |
| TRKMFF220306M | M.Tarkin     | admin    |
