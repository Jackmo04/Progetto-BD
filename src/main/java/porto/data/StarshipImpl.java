package porto.data;

import porto.data.api.Person;
import porto.data.api.PersonRole;
import porto.data.api.ShipModel;
import porto.data.api.Starship;

/**
 * Implementation of the Starship interface.
 */
public record StarshipImpl(
    String plateNumber,
    String name,
    ShipModel model,
    Person capitan
) implements Starship {

    /**
     * Constructor for StarshipImpl.
     *
     * @param plateNumber the plate number of the starship
     * @param name the name of the starship
     * @param model the model of the starship
     * @param capitan the captain of the starship
     */
    public StarshipImpl {
        if (plateNumber == null || plateNumber.isBlank()) {
            throw new IllegalArgumentException("Plate number cannot be null or blank");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or blank");
        }
        if (model == null) {
            throw new IllegalArgumentException("Model cannot be null");
        }
        if (capitan == null) {
            throw new IllegalArgumentException("Capitan cannot be null");
        }
        if (!capitan.role().equals(PersonRole.CAPTAIN)) {
            throw new IllegalArgumentException("Capitan must have the role of CAPTAIN");
        }
    }

}
