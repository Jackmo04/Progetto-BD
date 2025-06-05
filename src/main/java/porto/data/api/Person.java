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
     * Full name of the person, combining name and surname.
     * 
     * @return full name in the format "name surname".
     */
    default String fullName() {
        return name() + " " + surname();
    }   

    /**
     * 
     * @return race of the person.
     */
    String race();
    /**
     * Date of birth of the person.
     * 
     * @return bornDate in String format.
     */
    String dateOfBirth();
    /**
     * Indicates if the person is wanted.
     * 
     * @return true if wanted, false otherwise.
     */
    Boolean isWanted();
    /**
     * Ideological beliefs of the person.
     * 
     * @return ideology.
     */
    Ideology ideology();
    /**
     * Role of the person in the context of the application.
     * 
     * @return role.
     */
    PersonRole role();
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
    Planet birthPlanet();

}
