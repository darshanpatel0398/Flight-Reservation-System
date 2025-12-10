import java.time.LocalDateTime;
import java.util.List;

public class FlightServiceManualTest {

    public static void main(String[] args) {

        System.out.println("=== RUNNING MANUAL TESTS ===");

        FlightService service = new FlightService();

        Flight f1 = new Flight("F100", "New York",
                LocalDateTime.of(2025, 1, 10, 10, 0), 10);

        Flight f2 = new Flight("F200", "London",
                LocalDateTime.of(2025, 1, 11, 15, 0), 5);

        service.addFlight(f1);
        service.addFlight(f2);

        // ✅ TEST 1: Search Flights
        List<Flight> searchResults =
                service.searchFlights("New York",
                        LocalDateTime.of(2025, 1, 10, 0, 0));

        System.out.println("Search Results Count (Expected 1): " + searchResults.size());

        // ✅ TEST 2: Successful Booking
        Reservation r1 = service.bookFlight("Alice", f1, 3);
        System.out.println("Reservation Created: " + r1);
        System.out.println("Remaining Seats (Expected 7): " + f1.getAvailableSeats());

        // ✅ TEST 3: Overbooking (Should Fail)
        try {
            service.bookFlight("Bob", f2, 10);
            System.out.println("ERROR: Overbooking should have failed");
        } catch (IllegalStateException ex) {
            System.out.println("Overbooking prevented successfully ✅");
        }

        // ✅ TEST 4: View Reservations
        List<Reservation> aliceReservations = service.getReservationsForCustomer("Alice");
        System.out.println("Alice Reservation Count (Expected 1): " + aliceReservations.size());

        System.out.println("=== MANUAL TESTS COMPLETE ===");
    }
}
