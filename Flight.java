import java.time.LocalDateTime;
import java.util.Objects;

public class Flight {

    private final String flightNumber;
    private final String destination;
    private final LocalDateTime departureTime;
    private int availableSeats;

    public Flight(String flightNumber, String destination, LocalDateTime departureTime, int availableSeats) {
        if (availableSeats < 0) {
            throw new IllegalArgumentException("availableSeats cannot be negative");
        }
        this.flightNumber = Objects.requireNonNull(flightNumber, "flightNumber cannot be null");
        this.destination = Objects.requireNonNull(destination, "destination cannot be null");
        this.departureTime = Objects.requireNonNull(departureTime, "departureTime cannot be null");
        this.availableSeats = availableSeats;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getDestination() {
        return destination;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void reduceAvailableSeats(int seats) {
        if (seats <= 0) {
            throw new IllegalArgumentException("seats must be positive");
        }
        if (availableSeats - seats < 0) {
            throw new IllegalStateException("Not enough seats available");
        }
        this.availableSeats -= seats;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "flightNumber='" + flightNumber + '\'' +
                ", destination='" + destination + '\'' +
                ", departureTime=" + departureTime +
                ", availableSeats=" + availableSeats +
                '}';
    }
}
