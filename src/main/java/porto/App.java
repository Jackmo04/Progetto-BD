package porto;

import java.sql.SQLException;

import porto.data.utils.DAOUtils;
import porto.model.Model;

public final class App {

    public static void main(String[] args) throws SQLException {
        var connection = DAOUtils.localMySQLConnection("PortoMorteNera", "root", "");
        var model = Model.fromConnection(connection);
        var view = new View(() -> {
            try {
                connection.close();
            } catch (Exception ignored) {}
        });
        var controller = new Controller(model, view);
        view.setController(controller);
        // controller.userRequestedInitialPage();
    }
}
