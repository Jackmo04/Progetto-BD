package porto.data.queries;

public final class QueryAction {

        public static final String S1_ADD_PERSON =
        """
                INSERT INTO PERSONE (CUI, Username,Password , Nome, Cognome,
                Razza, DataNascita, Ideologia, Ruolo, PianetaNascita) VALUES
                (?, ?, ? , ?, ?, ?, ?, ?, ?, ?);
            """;
        
            public static final String S2A_ACCESS_DB_REQUEST =
        """
                SELECT p.*
                FROM persone p
                WHERE ? IN (p.CUI, p.Username)
                AND p.Password = ?;
            """;

        public static final String A3A_FREE_CELL=
         """
                SELECT c.*
                FROM celle c
                LEFT JOIN persone p ON c.NumCella = p.NumCella
                GROUP BY c.NumCella, c.Capienza
                HAVING COUNT(p.CUI) < c.Capienza;
            """;

            public static final String A3B_ARREST_PERSON =
        """
                UPDATE Persone
                 SET NumCella = ?
                 WHERE CUI = ?;


            """;
            public static final String A3C_DELETE_FROM_EQUIPE =
        """
                DELETE FROM Equipaggi
                WHERE CUIAstronauta = ?;
            """;

                        public static final String C3_SHOW_EQUIPE_STARSHIP =
        """
                SELECT p.CUI
                FROM persone p, equipaggi e
                WHERE p.CUI = e.CUIAstronauta
                AND e.TargaAstronave = ?;
            """;

        public static final String S4_LAST_REQUEST =
        """
                SELECT r.CodRichiesta
                FROM richieste r, astronavi n
                WHERE r.TargaAstronave = n.Targa
                AND n.Targa = ?
                ORDER BY r.DataOra DESC
                LIMIT 1;
            """;
        
        
    
}
