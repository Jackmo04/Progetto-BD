package porto.data.api;

/**
 * Interface representing a Cell in the Porto data model.
 * This interface can be extended to define specific behaviors or properties of a Cell.
 */
import java.util.Optional;

/**
 * Interface representing a Person in the Porto data model.
 * This interface defines methods to access various attributes of a Person.
 */
public interface Person {

    /**
     * Retrieves the unique identifier (CUI) of the person.
     *
     * @return the CUI of the person
     */
    public String getCUI();

    /**
     * Retrieves the name of the person.
     *
     * @return the name of the person
     */
    public String getName();

    /**
     * Retrieves the username of the person.
     *
     * @return the username of the person
     */
    public String getUsername();

    /**
     * Retrieves the password of the person.
     *
     * @return the password of the person
     */
    public String getPassword();

    /**
     * Retrieves the surname of the person.
     *
     * @return the surname of the person
     */
    public String getSurname();

    /**
     * Retrieves the razza of person
     * 
     * @return the razza of the person
     */
    public String getRazza();

    /**
     * Retrieves the date of birth of the person.
     *
     * @return the date of birth of the person
     */
    public String getBornDate();

    /**
     * Retrieves the place of birth of the person.
     *
     * @return the Planet where the person was born
     */
    public Planet getBornPlace();

    /**
     * Retrieves the place of death of the person.
     *
     * @return the Planet where the person died, or null if not applicable
     */
    public Boolean isWanted();

    /**
     * Sets the wanted status of the person.
     *
     * @param wanted true if the person is wanted, false otherwise
     */
    public void setWanted(Boolean wanted);

    /**
     * Retrieves the ideology of the person.
     *
     * @return the ideology of the person
     */
    public String getIdeology();

    /**
     * Retrieves the role of the person.
     *
     * @return the role of the person
     */
    public String getRole();

    /**
     * Retrieves the cell associated with the person.
     *
     * @return an Optional containing the Cell if present, or empty if not
     */
    public Optional<Cell> getCell();

    /**
     * Sets the cell associated with the person.
     *
     * @param cell the Cell to be associated with the person
     */
    public void setCell(Cell cell);

}
