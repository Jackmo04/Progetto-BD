package porto.data.deleteme;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import porto.data.utils.DAOException;
import porto.data.utils.DAOUtils;

public final class Material {

    public final int code;
    public final String description;

    public Material(int code, String description) {
        this.code = code;
        this.description = description == null ? "" : description;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (other == null) {
            return false;
        } else if (other instanceof Material) {
            var m = (Material) other;
            return (m.code == this.code && m.description.equals(this.description));
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.code, this.description);
    }

    @Override
    public String toString() {
        return Printer.stringify(
            "Material",
            List.of(Printer.field("code", this.code), Printer.field("description", this.description))
        );
    }

    public static final class DAO {

        public static Map<Material, Float> forProduct(Connection connection, int productId) {
            var compositions = new HashMap<Material, Float>();
            try (
                var statement = DAOUtils.prepare(connection, Queries.PRODUCT_COMPOSITION, productId);
                var resultSet = statement.executeQuery();
            ) {
                while (resultSet.next()) {
                    var code = resultSet.getInt("code");
                    var desc = resultSet.getString("description");
                    var percent = resultSet.getFloat("percent");
                    var material = new Material(code, desc);
                    compositions.put(material, percent);
                }
            } catch (Exception e) {
                throw new DAOException(e);
            }
            return compositions;
        }
    }
}
