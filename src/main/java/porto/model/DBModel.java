package porto.model;

import java.sql.Connection;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import porto.data.api.Ideology;
import porto.data.api.Person;
import porto.data.api.Planet;
import porto.data.api.PersonRole;
import porto.data.api.dao.PersonDAO;
import porto.data.api.dao.PlanetDAO;
import porto.data.api.dao.RequestDAO;
import porto.data.api.dao.StarshipDAO;
import porto.data.dao.PersonDAOImpl;
import porto.data.dao.PlanetDAOImpl;
import porto.data.dao.RequestDAOImpl;
import porto.data.dao.StarshipDAOImpl;
import porto.data.utils.DAOException;

// This is the real model implementation that uses the DAOs we've defined to
// actually load data from the underlying database.
//
// As you can see this model doesn't do too much except loading data from the
// database and keeping a cache of the loaded previews.
// A real model might be doing much more, but for the sake of the example we're
// keeping it simple.
//
public final class DBModel implements Model {

    private final Connection connection;
    private final PersonDAO personDAO;
    private final StarshipDAO starshipDAO;
    private final RequestDAO requestDAO;
    private final PlanetDAO planetDAO;
    private Optional<Person> loggedUser = Optional.empty();

    public DBModel(Connection connection) {
        Objects.requireNonNull(connection, "Model created with null connection");
        this.connection = connection;
        this.personDAO = new PersonDAOImpl(connection);
        this.starshipDAO = new StarshipDAOImpl(connection);
        this.requestDAO = new RequestDAOImpl(connection);
        this.planetDAO = new PlanetDAOImpl(connection);
    }

    @Override
    public boolean login(String username, String password) {
        Objects.requireNonNull(username, "Username cannot be null");
        Objects.requireNonNull(password, "Password cannot be null");
        this.loggedUser = personDAO.loginAndGetUser(username, password);
        return this.loggedUser.isPresent();
    }

    @Override
    public Person getLoggedUser() {
        return this.loggedUser.orElseThrow(() -> new IllegalStateException("No user is logged in"));
    }

    @Override
    public Set<Planet> getAllPlanets() {
        try {
            return planetDAO.getAll();
        } catch (DAOException e) {
            throw new RuntimeException("Error retrieving planets", e);
        }
    }

    @Override
    public boolean isUserRegistered(String cui, String username) {
        Objects.requireNonNull(cui, "CUI cannot be null");
        Objects.requireNonNull(username, "Username cannot be null");
        try {
            return personDAO.getFromCUI(cui).isPresent() || personDAO.getFromUsername(username).isPresent();
        } catch (DAOException e) {
            throw new RuntimeException("Error checking if user is already registered", e);
        }
    }

    @Override
    public void registerUser(String cui, String username, String password, String name, String surname, String race,
            String dob, boolean wanted, String ideology, boolean isCaptain, String planetName) {
        Objects.requireNonNull(cui, "CUI cannot be null");
        Objects.requireNonNull(username, "Username cannot be null");
        Objects.requireNonNull(password, "Password cannot be null");
        Objects.requireNonNull(name, "Name cannot be null");
        Objects.requireNonNull(surname, "Surname cannot be null");
        Objects.requireNonNull(race, "Race cannot be null");
        Objects.requireNonNull(dob, "Date of birth cannot be null");
        Objects.requireNonNull(ideology, "Ideology cannot be null");
        Objects.requireNonNull(planetName, "Planet cannot be null");
        var ideologyObj = Ideology.fromString(ideology);
        var role = isCaptain ? PersonRole.CAPTAIN : PersonRole.CREW_MEMBER;
        var planet = planetDAO.getFromName(planetName).orElseThrow();
        try {
            // Check if the CUI already exists
            if (personDAO.getFromCUI(cui).isPresent()) {
                throw new IllegalArgumentException("A user with this CUI already exists");
            }
            personDAO.addPerson(cui, username, password, name, surname, race, dob, wanted, ideologyObj, role, planet);
        } catch (DAOException e) {
            throw new RuntimeException("Error registering user", e);
        }
    }

    // @Override
    // public Optional<Product> find(int productCode) {
    //     return Product.DAO.find(connection, productCode);
    // }

    // @Override
    // public List<ProductPreview> previews() {
    //     return this.previews.orElse(List.of());
    // }

    // @Override
    // public boolean loadedPreviews() {
    //     return this.previews.isPresent();
    // }

    // @Override
    // public List<ProductPreview> loadPreviews() {
    //     var previews = ProductPreview.DAO.list(this.connection);
    //     this.previews = Optional.of(previews);
    //     return previews;
    // }
}
