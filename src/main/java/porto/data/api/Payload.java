package porto.data.api;

public interface Payload {

    /**
     * Gets the type of the payload.
     * @return the payload type
     */
    PayloadType type();

    /**
     * Gets the quantity of the payload.
     * @return the quantity
     */
    int quantity();

    /**
     * Gets the total price of the payload.
     * @return the total price
     */
    double totalPrice();
}
