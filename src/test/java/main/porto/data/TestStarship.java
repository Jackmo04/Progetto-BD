package main.porto.data;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import porto.data.PersonImpl;
import porto.data.PlanetImpl;
import porto.data.ShipModelImpl;
import porto.data.StarshipImpl;
import porto.data.api.Ideology;
import porto.data.api.Role;

class TestStarship {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestStarship.class);

    @Test
    void testStarshipCreation() {
        LOGGER.info("Testing Starship creation...");

        // Vadid Starship creation
        try {
            new StarshipImpl(
                "NewShip",
                "FooBar",
                new ShipModelImpl("Model123", "Star Cruiser", 100, 5000.0),
                new PersonImpl(
                    "CAP123456",
                    "John Doe",
                    "JD",
                    "Captain",
                    "Doe",
                    "Human",
                    "1980-01-01",
                    false,
                    Ideology.NEUTRAL,
                    Role.CAPTAIN,
                    Optional.empty(),
                    new PlanetImpl("EARTH001", "Earth")
                )
            );
        } catch (Exception e) {
            fail("Starship creation failed with exception: " + e.getMessage());
        }

        // Invalid role for captain
        try {
            new StarshipImpl(
                "NewShip",
                "FooBar",
                new ShipModelImpl("Model123", "Star Cruiser", 100, 5000.0),
                new PersonImpl(
                    "CAP123456",
                    "John Doe",
                    "JD",
                    "Captain",
                    "Doe",
                    "Human",
                    "1980-01-01",
                    false,
                    Ideology.NEUTRAL,
                    Role.CREW_MEMBER, // Invalid role: has to be CAPTAIN
                    Optional.empty(),
                    new PlanetImpl("EARTH001", "Earth")
                )
            );
            fail("Expected IllegalArgumentException for invalid role, but none was thrown.");
        } catch (IllegalArgumentException iae) {
        } catch (Exception e) {
            fail("Unexpected exception type: " + e.getClass().getName());
        }
    }
}
