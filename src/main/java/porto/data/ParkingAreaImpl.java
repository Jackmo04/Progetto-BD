package porto.data;

import porto.data.api.ParkingArea;

public record ParkingAreaImpl(
    int codArea,
    String name
) implements ParkingArea {

}
