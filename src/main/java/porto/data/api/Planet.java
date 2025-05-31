package porto.data.api;

/**
 * Interface representing a Planet in the Porto data model.
 * This interface can be extended to define specific behaviors or properties of
 * a Planet.
 */
public interface Planet {

    /**
     * Unique code for the planet.
     * 
     * @return codPlanet (Codice Unico del Pianeta).
     */
    String codPlanet();

    /**
     * Name of the planet.
     * 
     * @return name of the planet.
     */
    String name();

}
