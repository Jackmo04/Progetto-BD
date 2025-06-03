package porto.data.queries;

public final class Queries {

    public static final String SHIP_FROM_PLATE = """
                SELECT *
                FROM astronavi
                WHERE Targa = ?;
            """;

    public static final String SHIPS_FROM_PERSON = """
                SELECT DISTINCT n.*
                FROM astronavi n, persone p, equipaggi e
                WHERE ((p.CUI = e.CUIAstronauta AND e.TargaAstronave = n.Targa) OR (p.CUI = n.CUICapitano))
                AND p.CUI = ?;
            """;

    public static final String AREA_FROM_CODE = """
                SELECT *
                FROM aree_attracco
                WHERE CodArea = ?;
            """;

    public static final String MODEL_FROM_CODE = """
                SELECT m.*, dp.Prezzo
                FROM modelli m, dimensioni_prezzi dp
                WHERE m.DimensioneArea = dp.Superficie
                AND CodModello = ?;
            """;

    public static final String MODELS_ALL = """
                SELECT m.*, dp.Prezzo
                FROM modelli m, dimensioni_prezzi dp
                WHERE m.DimensioneArea = dp.Superficie;
            """;

    public static final String PLANET_FROM_CODPLANET = """
                SELECT p.*
                FROM pianeti p
                WHERE p.CodPianeta = ?;
            """;

    public static final String CELL_FROM_NUMCELL = """
                SELECT c.*
                FROM celle c
                WHERE c.NumCella = ?;
            """;

    public static final String PERSON_FROM_CUI = """
                SELECT p.*
                FROM persone p
                WHERE p.CUI = ?;
            """;

    public static final String SPACE_FROM_PLATE = """
                SELECT CodArea, NumeroPosto
                FROM astronavi
                WHERE Targa = ?;
            """;

    public static final String REQUEST_FROM_COD = """
                SELECT r.*
                FROM richieste r
                WHERE r.CodRichiesta = ?;
            """;

    public static final String ALL_FLIGHT_PURPOSE = """
                SELECT t.*
                FROM tipologie_viaggio t;
            """;

    public static final String INSERT_STARSHIP = """
                INSERT INTO astronavi (Targa, Nome, CodModello, CUICapitano)
                VALUES (?, ?, ?, ?);
            """;

    public static final String INSERT_CREW_MEMBER = """
                INSERT INTO equipaggi (TargaAstronave, CUIAstronauta)
                VALUES (?, ?);
            """;

    public static final String REMOVE_CREW_MEMBER = """
                DELETE FROM equipaggi
                WHERE TargaAstronave = ?
                AND CUIAstronauta = ?;
            """;

    public static final String PLANETS_ALL = """
                SELECT p.*
                FROM pianeti p;
            """;

    public static final String NUMBER_OF_PEOPLE_ON_STATION = """
                SELECT COUNT(DISTINCT p.CUI)
                FROM astronavi a, equipaggi e, persone p
                WHERE ((p.CUI = e.CUIAstronauta AND e.TargaAstronave = a.Targa) OR (p.CUI = a.CUICapitano))
                AND a.numeroPosto IS NOT NULL;
            """;

    public static final String FREE_PARKING_SPACES = """
                SELECT p.*
                FROM posteggi p
                WHERE (p.codArea, p.numeroPosto) NOT IN (SELECT a.codArea, a.numeroPosto
            						         FROM astronavi a
                                                         WHERE a.numeroPosto IS NOT NULL);
            """;

    public static final String STARSHIPS_TRANSPORTED_MOST = """
                SELECT r.TargaAstronave, SUM(c.Quantita) QtaTot
                FROM Richieste r, Carichi c
                WHERE c.CodRichiesta = r.CodRichiesta
                GROUP BY r.TargaAstronave
                ORDER BY QtaTot DESC
                LIMIT 50;
            """;

    public static final String ADD_PAYLOAD = """
                INSERT INTO Carichi (Tipologia, Quantita, CodRichiesta)
                VALUES (?, ?, ?);
            """;

    public static final String PAYLOAD_TYPE_ALL = """
                SELECT t.*
                FROM tipologie_carico t;
            """;

    public static final String INSERT_ENTRY_REQUEST = """
                INSERT INTO Richieste (EntrataUscita, Descrizione, CostoTotale, TargaAstronave, Scopo, PianetaProvenienza, PianetaDestinazione)
                VALUES ('E', ?, 0, ?, ?, ?, 'DTHSTR0');
            """;

    public static final String INSERT_EXIT_REQUEST = """
                INSERT INTO Richieste (EntrataUscita, Descrizione, CostoTotale, TargaAstronave, Scopo, PianetaProvenienza, PianetaDestinazione)
                VALUES ('U', ?, 0, ?, ?, 'DTHSTR0', ?);
            """;

    public static final String UPDATE_REQUEST_TOTAL_PRICE = """
                UPDATE richieste
                SET CostoTotale = ?
                WHERE CodRichiesta = ?;
            """;

    public static final String REQUEST_STATE_FROM_COD = """
                SELECT r.Esito
                FROM richieste r
                WHERE r.CodRichiesta = ?;
            """;

    public static final String REQUEST_MANAGED_DATE_FROM_COD = """
                SELECT r.DataEsito
                FROM richieste r
                WHERE r.CodRichiesta = ?;
            """;

    public static final String REQUEST_MANAGED_BY_FROM_COD = """
                SELECT r.GestitaDa
                FROM richieste r
                WHERE r.CodRichiesta = ?;
            """;

    public static final String ADD_PERSON = """
                INSERT INTO PERSONE (CUI, Username,Password , Nome, Cognome,
                Razza, DataNascita, Ideologia, Ruolo, PianetaNascita) VALUES
                (?, ?, ? , ?, ?, ?, ?, ?, ?, ?);
            """;

    public static final String ACCESS_DB_REQUEST = """
                SELECT p.*
                FROM persone p
                WHERE ? IN (p.CUI, p.Username)
                AND p.Password = ?;
            """;

    public static final String FREE_CELL = """
                SELECT c.*
                FROM celle c
                LEFT JOIN persone p ON c.NumCella = p.NumCella
                GROUP BY c.NumCella, c.Capienza
                HAVING COUNT(p.CUI) < c.Capienza;
            """;

    public static final String ARREST_PERSON = """
                UPDATE Persone
                 SET NumCella = ?
                 WHERE CUI = ?;


            """;
    public static final String DELETE_FROM_EQUIPE = """
                DELETE FROM Equipaggi
                WHERE CUIAstronauta = ?;
            """;

    public static final String SHOW_EQUIPE_STARSHIP = """
                SELECT p.CUI
                FROM persone p, equipaggi e
                WHERE p.CUI = e.CUIAstronauta
                AND e.TargaAstronave = ?;
            """;

    public static final String LAST_REQUEST = """
                SELECT *
                FROM richieste r, astronavi n
                WHERE r.TargaAstronave = n.Targa
                AND n.Targa = ?
                ORDER BY r.DataOra DESC
                LIMIT 1;
            """;
    public static final String REQUEST_HISTORY = """
            SELECT DISTINCT r.CodRichiesta
            FROM richieste r, astronavi n
            WHERE r.TargaAstronave = n.Targa
            AND n.Targa = ?;
            """;

    public static final String PENDING_REQUEST = """
                SELECT * FROM Richieste_pendenti;
            """;
    public static final String ACCEPT_REQUEST = """
                UPDATE richieste
                SET esito = 'A' , dataEsito = NOW(), gestitaDa = ?
                WHERE CodRichiesta = ?;

            """;
    public static final String ASSIGN_PARKING = """

                UPDATE astronavi
                SET codArea = ?, numeroPosto = ?
                WHERE targa = (SELECT targaAstronave
                       FROM richieste r
                                WHERE r.codRichiesta = ?
                                 AND r.esito = 'A');
            """;
    public static final String RELEASES_PARKING = """

                UPDATE astronavi
                SET codArea = NULL, numeroPosto = NULL
                WHERE targa = (SELECT targaAstronave
                       FROM richieste r
                                WHERE r.codRichiesta = ?
                                 AND r.esito = 'A');
            """;
    public static final String REJECT_REQUEST = """
                UPDATE richieste
                SET esito = 'R' , dataEsito = NOW(), gestitaDa = ?
                WHERE CodRichiesta = ?;
            """;
}
