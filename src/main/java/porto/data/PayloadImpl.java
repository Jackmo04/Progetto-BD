package porto.data;

import porto.data.api.Payload;
import porto.data.api.PayloadType;

public record PayloadImpl(
    PayloadType type,
    int quantity,
    double totalPrice
) implements Payload {

    public PayloadImpl(PayloadType type, int quantity) {
        this(type, quantity, quantity * type.unitPrice());
    }
}
