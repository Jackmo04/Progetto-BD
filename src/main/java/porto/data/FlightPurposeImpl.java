package porto.data;

import porto.data.api.FlightPurpose;

/**
 * Implementation of the FlightPurpose interface.
 * Represents a flight purpose with a code and name.
 */
public record FlightPurposeImpl(Integer codFlightPurpose, String flightPurposeName) implements FlightPurpose {

}
