package porto.data.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import porto.data.CellImpl;
import porto.data.api.Cell;
import porto.data.api.dao.CellDAO;
import porto.data.queries.Queries;
import porto.data.utils.DAOException;
import porto.data.utils.DAOUtils;

/**
 * Implementation of the CellDAO interface for accessing Cell data from the
 * database.
 * This class provides methods to retrieve Cell information based on specific
 * criteria.
 */
public class CellDAOImpl implements CellDAO {

    private final Connection connection;

    /**
     * Constructor for StarshipDAOImpl.
     * 
     * @param connection the database connection
     */
    public CellDAOImpl(Connection connection) {
        this.connection = connection;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Cell> getFromNumCell(Integer numCell) throws DAOException {
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

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Cell> getAllFreeCell() throws DAOException {
        var listCell = new ArrayList<Cell>();
        try (
                var statement = DAOUtils.prepare(connection, Queries.FREE_CELL);
                var resultSet = statement.executeQuery();) {
            while (resultSet.next()) {
                var numCell = resultSet.getInt("NumCella");
                var capacity = resultSet.getInt("Capienza");
                var cell = new CellImpl(numCell, capacity);
                listCell.add(cell);
            }
        } catch (Exception e) {
            throw new DAOException(e);
        }
        return listCell;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Cell> getOfPerson(String CUI) throws DAOException {
        try (
                var statement = DAOUtils.prepare(connection, Queries.CELL_OF_PERSON, CUI);
                var resultSet = statement.executeQuery();) {
            if (resultSet.next()) {
                var numCell = resultSet.getInt("NumCella");
                var capacity = resultSet.getInt("Capienza");
                var cell = new CellImpl(numCell, capacity);
                return Optional.of(cell);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }
}
