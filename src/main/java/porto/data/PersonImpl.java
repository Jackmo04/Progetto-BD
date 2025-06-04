package porto.data;

import java.util.Optional;

import porto.data.api.Cell;
import porto.data.api.Ideology;
import porto.data.api.Person;
import porto.data.api.Planet;
import porto.data.api.Role;

/**
 * Implementation of the Person interface representing a person in the system.
 * This record encapsulates the properties of a person including their CUI,
 * username, name, surname, etc.
 * @param CUI the unique identifier for the person
 * @param username the username of the person
 * @param name the first name of the person
 * @param surname the last name of the person
 * @param race the race of the person
 * @param dateOfBirth the date of birth of the person
 * @param isWanted indicates if the person is wanted
 * @param ideology the ideology of the person
 * @param role the role of the person in the system
 * @param cell the cell where the person is located, if applicable
 * @param birthPlanet the planet where the person was born
*/
public record PersonImpl(
    String CUI,
    String username,
    String name,
    String surname,
    String race,
    String dateOfBirth,
    Boolean isWanted,
    Ideology ideology,
    Role role,
    Optional<Cell> cell,
    Planet birthPlanet
) implements Person {

}
