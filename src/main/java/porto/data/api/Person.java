package porto.data.api;

import java.util.Optional;

/**
 * Interface representing a Person in the Porto data model.
 * This interface defines methods to access various attributes of a Person.
 */
public interface Person {

    /**
     * Unique identifier for the person.
     * 
     * @return CUI (Codice Unico Identificativo).
     */
    String CUI();

    /**
     * Username for the person.
     * 
     * @return username.
     */
    String username();

    /**
     * Password for the person.
     * 
     * @return password.
     */
    String password();
    /**
     * Full name of the person.
     * 
     * @return name.
     */
    String name();
    /**
     * Surname of the person.
     * 
     * @return surname.
     */
    String surname();
    /**
     * 
     * @return razza of the person.
     */
    String razza();
    /**
     * Date of birth of the person.
     * 
     * @return bornDate in String format.
     */
    String bornDate();
    /**
     * Indicates if the person is wanted.
     * 
     * @return true if wanted, false otherwise.
     */
    Boolean wanted();
    /**
     * Ideological beliefs of the person.
     * 
     * @return ideology.
     */
    String ideology();
    /**
     * Role of the person in the context of the application.
     * 
     * @return role.
     */
    String role();
    /**
     * Optional cell associated with the person.
     * 
     * @return Optional containing Cell if present, otherwise empty.
     */
    Optional<Cell> cell();
    /**
     * Optional place of birth represented as a Planet object.
     * 
     * @return Optional containing Planet if present, otherwise empty.
     */
    Optional<Planet> bornPlace();

}
