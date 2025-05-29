package porto.data;

import porto.data.api.Planet;

/**
 * Implementation of the Planet interface representing a planet in the Porto data model.
 * This record contains the code and name of the planet.
 */
public record PlanetImpl(
    String codPlanet,
    String name
) implements Planet{
    
}
