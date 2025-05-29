package porto.data.api;

public interface ParkingSpace {

    /**
     * Returns the parking area of this parking space.
     * @return the parking area
     */
    ParkingArea parkingArea();

    /**
     * Returns the number of this parking space.
     * @return the number
     */
    int spaceNumber();

}
