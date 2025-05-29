package porto.data.dao;

import java.sql.Connection;
import java.util.Optional;

import porto.data.CellImpl;
import porto.data.api.Cell;
import porto.data.api.dao.CellDAO;
import porto.data.queries.Queries;
import porto.data.utils.DAOException;
import porto.data.utils.DAOUtils;

public class CellDAOImpl implements CellDAO{

    @Override
    public Optional<Cell> getFromNumCell(Connection connection, Integer numCell) throws DAOException {
        try (
                var statement = DAOUtils.prepare(connection, Queries.CELL_FROM_NUMCELL, numCell);
                var resultSet = statement.executeQuery();) {
            if (resultSet.next()) {
                var idCell = resultSet.getInt("NumCella");
                var capacity = resultSet.getInt("Capienza");
                var cell = new CellImpl(idCell, capacity);
                return Optional.of(cell);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new DAOException(e);
        }
    
}
}
