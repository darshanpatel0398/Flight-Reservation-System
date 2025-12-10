import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FlightService {

    private final List<Flight> flights = new ArrayList<>();
    private final List<Reservation> reservations = new ArrayList<>();

    public void addFlight(Flight flight) {
        flights.add(flight);
    }

    public List<Flight> getAllFlights() {
        return Collections.unmodifiableList(flights);
    }

    public List<Reservation> getReservationsForCustomer(String customerName) {
        return reservations.stream()
                .filter(r -> r.getCustomerName().equalsIgnoreCase(customerName))
                .collect(Collectors.toList());
    }

    /**
     * Returns flights that match destination (case-insensitive)
     * and depart on the same calendar date.
     */
    public List<Flight> searchFlights(String destination, LocalDateTime date) {
        if (destination == null || date == null) {
            return List.of();
        }
        LocalDate targetDate = date.toLocalDate();

        return flights.stream()
                .filter(f -> f.getDestination().equalsIgnoreCase(destination))
                .filter(f -> f.getDepartureTime().toLocalDate().equals(targetDate))
                .filter(f -> f.getAvailableSeats() > 0)
                .collect(Collectors.toList());
    }

    /**
     * Books seats on a given flight for a customer.
     * Throws IllegalArgumentException/IllegalStateException on invalid input.
     */
    public Reservation bookFlight(String customerName, Flight flight, int seats) {
        if (customerName == null || flight == null) {
            throw new IllegalArgumentException("customerName and flight must not be null");
        }
        if (seats <= 0) {
            throw new IllegalArgumentException("seats must be positive");
        }

        if (flight.getAvailableSeats() < seats) {
            throw new IllegalStateException("Not enough seats available for this flight");
        }

        flight.reduceAvailableSeats(seats);
        Reservation reservation = new Reservation(customerName, flight, seats);
        reservations.add(reservation);
        return reservation;
    }
}
