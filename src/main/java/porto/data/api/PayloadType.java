package porto.data.api;

public interface PayloadType {

    /**
     * Gets the code of the payload type.
     * @return the code
     */
    int code();

    /**
     * Gets the name of the payload type.
     * @return the name
     */
    String name();

    /**
     * Gets the description of the payload type.
     * @return the description
     */
    String description();

    /**
     * Gets the unit price of the payload type.
     * @return the unit price
     */
    double unitPrice();
}
