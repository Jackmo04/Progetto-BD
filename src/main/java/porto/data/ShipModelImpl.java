package porto.data;

import porto.data.api.ShipModel;

public record ShipModelImpl(
    String codModel,
    String name,
    int size,
    double tax
) implements ShipModel {
}
