package porto.data;

import porto.data.api.FlightPurpose;

public record FlightPurposeImpl(Integer codFlightPurpose, String flightPurposeName) implements FlightPurpose {

}
