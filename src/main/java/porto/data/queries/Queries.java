package porto.data.queries;

public final class Queries {

    public static final String SHIPS_FROM_PERSON = 
    """
        SELECT DISTINCT n.*
        FROM astronavi n, persone p, equipaggi e
        WHERE ((p.CUI = e.CUIAstronauta AND e.TargaAstronave = n.Targa) OR (p.CUI = n.CUICapitano))
        AND p.CUI = ?;
    """;
}
