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
        
    
}
