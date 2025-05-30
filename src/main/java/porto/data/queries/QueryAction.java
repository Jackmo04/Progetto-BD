package porto.data.queries;

public final class QueryAction {

        public static final String S1_ADD_PERSON =
        """
                INSERT INTO PERSONE (CUI, Username,Password , Nome, Cognome, Razza, DataNascita, Ideologia, Ruolo, PianetaNascita) VALUES
(?, ?, ? , ?, ?, ?, ?, ?, ?, ?);
            """;
    
}
