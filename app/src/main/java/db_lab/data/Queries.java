package db_lab.data;

public final class Queries {

    public static final String TAGS_FOR_PRODUCT =
        """
        SELECT t.tag_name
        FROM tagged t
        WHERE t.product_code = ?;
        """;

    public static final String LIST_PRODUCTS =
        """
        SELECT p.code, p.name
        FROM product p;
        """;

    public static final String PRODUCT_COMPOSITION =
        """
        SELECT m.code, m.description, c.percent
        FROM material m, composition c
        WHERE c.material_code = m.code
        AND c.product_code = ?;
        """;

    public static final String FIND_PRODUCT =
        """
        SELECT *
        FROM product p
        WHERE p.code = ?;
        """;
}
