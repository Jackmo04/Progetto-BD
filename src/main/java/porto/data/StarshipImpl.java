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
    private final ShipModel model;
    private final Person capitan;
    private Optional<ParkingSpace> parkingSpace;

    public StarshipImpl(
        String plateNumber,
        String name,
        Optional<ParkingSpace> parkingSpace,
        ShipModel model,
        Person capitan
    ) {
        this.plateNumber = plateNumber;
        this.name = name;
        this.parkingSpace = parkingSpace;
        this.model = model;
        this.capitan = capitan;
    }

    @Override
    public String plateNumber() {
        return this.plateNumber;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public Optional<ParkingSpace> parkingSpace() {
        return this.parkingSpace;
    }

    @Override
    public ShipModel model() {
        return this.model;
    }

    @Override
    public Person capitan() {
        return this.capitan;
    }

}
