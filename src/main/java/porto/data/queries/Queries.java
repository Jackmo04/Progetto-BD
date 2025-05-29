package porto.data.queries;

public final class Queries {

    public static final String SHIPS_FROM_PERSON = 
    """
        SELECT DISTINCT n.*
        FROM astronavi n, persone p, equipaggi e
        WHERE ((p.CUI = e.CUIAstronauta AND e.TargaAstronave = n.Targa) OR (p.CUI = n.CUICapitano))
        AND p.CUI = ?;
    """;

    public static final String PLANET_FROM_CODPLANET = 
    """
        SELECT p.*
        FROM pianeta p
        WHERE p.CodPianeta = ?;
    """;


}
