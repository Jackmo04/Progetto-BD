package porto.data;

import porto.data.api.PayloadType;

public record PayloadTypeImpl(
    int code,
    String name,
    String description,
    double unitPrice
) implements PayloadType {

}
