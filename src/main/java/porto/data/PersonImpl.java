package porto.data;

import java.util.Optional;

import porto.data.api.Cell;
import porto.data.api.Person;
import porto.data.api.Planet;

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
public class PersonImpl implements Person {

    private final String CUI;
    private final String name;
    private final String username;
    private final String password;
    private final String surname;
    private final String razza;
    private final String bornDate;
    private final Planet bornPlace;
    private Boolean wanted;
    private final String ideology;
    private final String role;
    private Optional<Cell> cell;

    public PersonImpl(
            String CUI,
            String username,
            String password,
            String name,
            String surname,
            String razza,
            String bornDate,
            Boolean wanted,
            String ideology,
            String role,
            Cell cell,
            Planet bornPlace) {
        this.CUI = CUI;
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.razza = razza;
        this.bornDate = bornDate;
        this.wanted = wanted;
        this.ideology = ideology;
        this.role = role;
        this.cell = Optional.ofNullable(cell);
        this.bornPlace = bornPlace;
    }

    @Override
    public String getCUI() {
        return CUI;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getSurname() {
        return surname;
    }

    @Override
    public String getRazza() {
        return razza;
    }

    @Override
    public String getBornDate() {
        return bornDate;
    }

    @Override
    public Planet getBornPlace() {
        return bornPlace;
    }

    @Override
    public Boolean isWanted() {
        return wanted;
    }

    @Override
    public String getIdeology() {
        return ideology;
    }

    @Override
    public String getRole() {
        return role;
    }

    @Override
    public Optional<Cell> getCell() {
        return cell;
    }

    @Override
    public void setWanted(Boolean wanted) {
        this.wanted = wanted;
    }

    @Override
    public void setCell(Cell cell) {
        this.cell = Optional.of(cell);
    }

}
