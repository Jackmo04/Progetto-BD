package porto.model;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

import porto.data.deleteme.Product;
import porto.data.deleteme.ProductPreview;

public interface Model {
    Optional<Product> find(int productCode);

    List<ProductPreview> previews();

    boolean loadedPreviews();

    List<ProductPreview> loadPreviews();

    /**
     * Create a model from a database connection.
     * @param connection
     * @return
     */
    static Model fromConnection(Connection connection) {
        return new DBModel(connection);
    }
}
