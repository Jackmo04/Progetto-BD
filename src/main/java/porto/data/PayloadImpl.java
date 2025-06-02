package porto.data;

import porto.data.api.Payload;
import porto.data.api.PayloadType;

public record PayloadImpl(
    PayloadType type,
    int quantity,
    int associatedRequestCode
) implements Payload {

}
