package porto.model;

import java.sql.Connection;
import java.util.Objects;
import java.util.Optional;

import porto.data.api.Person;
import porto.data.api.dao.PersonDAO;
import porto.data.api.dao.RequestDAO;
import porto.data.api.dao.StarshipDAO;
import porto.data.dao.PersonDAOImpl;
import porto.data.dao.RequestDAOImpl;
import porto.data.dao.StarshipDAOImpl;

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
    private Optional<Person> loggedUser = Optional.empty();

    public DBModel(Connection connection) {
        Objects.requireNonNull(connection, "Model created with null connection");
        this.connection = connection;
        this.personDAO = new PersonDAOImpl(connection);
        this.starshipDAO = new StarshipDAOImpl(connection);
        this.requestDAO = new RequestDAOImpl(connection);
    }

    public boolean login(String username, String password) {
        Objects.requireNonNull(username, "Username cannot be null");
        Objects.requireNonNull(password, "Password cannot be null");
        this.loggedUser = personDAO.loginAndGetUser(username, password);
        return this.loggedUser.isPresent();
    }

    public Person getLoggedUser() {
        return this.loggedUser.orElseThrow(() -> new IllegalStateException("No user is logged in"));
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
