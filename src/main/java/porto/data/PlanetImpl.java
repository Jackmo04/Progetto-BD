package porto.data;

import porto.data.api.Planet;

public record PlanetImpl(
    String codPlanet,
    String name
) implements Planet{
    
}
