package porto;

import java.sql.Connection;
import java.sql.SQLException;

import porto.data.utils.DAOException;
import porto.data.utils.DAOUtils;
import porto.model.Model;
import porto.view.View;
import porto.view.utils.ConnectionFailureView;

public final class App {

    public static void main(String[] args) throws SQLException {
        Connection connection;
        try {
            connection = DAOUtils.localMySQLConnection("PortoMorteNera", "root", "");
        } catch (DAOException e) {
            new ConnectionFailureView();
            return;
        }
        var model = new Model(connection);
        var view = new View(() -> {
            try {
                connection.close();
            } catch (Exception ignored) {}
        });
        var controller = new Controller(model, view);
        view.setController(controller);
        controller.initialScene();
    }
}
