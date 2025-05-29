package porto.data.queries;

public final class Queries {

    public static final String SHIPS_FROM_PERSON = 
    """
        SELECT DISTINCT n.*
        FROM astronavi n, persone p, equipaggi e
        WHERE ((p.CUI = e.CUIAstronauta AND e.TargaAstronave = n.Targa) OR (p.CUI = n.CUICapitano))
        AND p.CUI = ?;
    """;

    public static final String AREA_FROM_CODE = 
    """
        SELECT *
        FROM aree_attracco
        WHERE CodArea = ?;
    """;

    public static final String MODEL_FROM_CODE = 
    """
        SELECT m.*, dp.Prezzo
        FROM modelli m, dimensioni_prezzi dp
        WHERE m.DimensioneArea = dp.Superficie
        AND CodModello = ?;
    """;
}
