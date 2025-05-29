package porto.data;

import java.util.Optional;

import porto.data.api.ParkingSpace;
import porto.data.api.Person;
import porto.data.api.ShipModel;
import porto.data.api.Starship;

/**
 * Implementation of the Starship interface.
 */
public class StarshipImpl implements Starship {
    
    private final String plateNumber;
    private final String name;
    private final Optional<ParkingSpace> parkingSpace;
    private final ShipModel model;
    private final Person capitan;

    /**
     * Constructor for StarshipImpl.
     * @param plateNumber
     * @param name
     * @param parkingSpace
     * @param model
     * @param capitan
     */
    public StarshipImpl(String plateNumber, String name, ParkingSpace parkingSpace, ShipModel model, Person capitan) {
        this.plateNumber = plateNumber;
        this.name = name;
        this.parkingSpace = Optional.ofNullable(parkingSpace);
        this.model = model;
        this.capitan = capitan;
    }

    public String platenumber() {
        return this.plateNumber;
    }

    public String name() {
        return this.name;
    }

    public Optional<ParkingSpace> parkingSpace() {
        return this.parkingSpace;
    }

    public ShipModel model() {
        return this.model;
    }

    public Person capitan() {
        return this.capitan;
    }

}
