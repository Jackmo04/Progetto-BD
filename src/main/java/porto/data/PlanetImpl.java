package porto.data;

import porto.data.api.Planet;

public record PlanetImpl(
    String id,
    String name
) implements Planet{
    
}
