package porto;

import java.sql.SQLException;

import porto.data.DAOException;
import porto.data.DAOUtils;
import porto.model.Model;

public final class App {

    public static void main(String[] args) throws SQLException {
        // If you want to get a feel of the application before having implemented
        // all methods, you can pass the controller a mocked model instead:
        //
        // var model = Model.mock();
        var connection = DAOUtils.localMySQLConnection("tessiland", "root", "");
        var model = Model.fromConnection(connection);
        var view = new View(() -> {
            // We want to make sure we close the connection when we're done
            // with our application.
            try {
                connection.close();
            } catch (Exception ignored) {}
        });
        var controller = new Controller(model, view);
        view.setController(controller);
        controller.userRequestedInitialPage();
    }
}
