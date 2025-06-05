package porto.data;

import porto.data.api.ParkingArea;
import porto.data.api.ParkingSpace;

public record ParkingSpaceImpl (
    ParkingArea parkingArea,
    int spaceNumber
) implements ParkingSpace {

    @Override
    public String toString() {
        return "Area " + parkingArea.name() + "" +
               " - Posteggio " + spaceNumber;
    }
}
