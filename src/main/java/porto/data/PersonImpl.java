package porto.data;

import java.util.Optional;

import porto.data.api.Cell;
import porto.data.api.Ideology;
import porto.data.api.Person;
import porto.data.api.Planet;
import porto.data.api.Role;

/**
 * Implementation of the Person interface.
 * This class represents a person with various attributes such as CUI, name,
 * username, password, surname,
 * razza, bornDate, bornPlace, wanted status, ideology, role, and number of
 * cells.
 * It implements the Person interface and provides a record-based structure for
 * easy data handling.
 * 
 * @param CUI       Unique identifier for the person.
 * @param name      Full name of the person.
 * @param username  Username for the person.
 * @param password  Password for the person.
 * @param surname   Surname of the person
 * @param razza
 * @param bornDate  Date of birth of the person.
 * @param bornPlace Place of birth represented as a Planet object.
 * @param wanted    Indicates if the person is wanted (Boolean).
 * @param ideology  Ideological beliefs of the person.
 * @param role      Role of the person in the context of the application.
 * @param numCel    Number of cells associated with the person.
 */
public record PersonImpl(
    String CUI,
    String username,
    String password,
    String name,
    String surname,
    String razza,
    String bornDate,
    Boolean wanted,
    Ideology ideology,
    Role role,
    Optional<Cell> cell,
    Planet bornPlace
) implements Person {

}
