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

}
