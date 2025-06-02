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

}
