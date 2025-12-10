import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

public class ConsoleApp {

    public static void main(String[] args) {
        FlightService flightService = new FlightService();
        seedSampleData(flightService);

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("=== Flight Reservation System ===");

        while (running) {
            System.out.println();
            System.out.println("1. Search flights");
            System.out.println("2. Book a flight");
            System.out.println("3. View my reservations");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" : handleSearchFlights(scanner, flightService);
                case "2" : handleBookFlight(scanner, flightService);
                case "3" : handleViewReservations(scanner, flightService);
                case "4" : {
                    running = false;
                    System.out.println("Exiting. Goodbye!");
                }
                default : System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();
    }

    private static void handleSearchFlights(Scanner scanner, FlightService flightService) {
        System.out.print("Enter destination: ");
        String destination = scanner.nextLine().trim();

        System.out.print("Enter departure date (YYYY-MM-DD): ");
        String dateInput = scanner.nextLine().trim();

        try {
            LocalDate date = LocalDate.parse(dateInput);
            // search at midnight to match by date only
            LocalDateTime dateTime = LocalDateTime.of(date, LocalTime.MIDNIGHT);

            List<Flight> results = flightService.searchFlights(destination, dateTime);

            if (results.isEmpty()) {
                System.out.println("No available flights found.");
            } else {
                System.out.println("Available flights:");
                for (int i = 0; i < results.size(); i++) {
                    Flight f = results.get(i);
                    System.out.printf("%d) %s to %s at %s (seats: %d)%n",
                            i + 1,
                            f.getFlightNumber(),
                            f.getDestination(),
                            f.getDepartureTime(),
                            f.getAvailableSeats());
                }
            }
        } catch (Exception e) {
            System.out.println("Invalid date format. Please use YYYY-MM-DD.");
        }
    }

    private static void handleBookFlight(Scanner scanner, FlightService flightService) {
        System.out.print("Enter your name: ");
        String customerName = scanner.nextLine().trim();

        System.out.print("Enter destination: ");
        String destination = scanner.nextLine().trim();

        System.out.print("Enter departure date (YYYY-MM-DD): ");
        String dateInput = scanner.nextLine().trim();

        try {
            LocalDate date = LocalDate.parse(dateInput);
            LocalDateTime dateTime = LocalDateTime.of(date, LocalTime.MIDNIGHT);

            List<Flight> results = flightService.searchFlights(destination, dateTime);

            if (results.isEmpty()) {
                System.out.println("No available flights found.");
                return;
            }

            System.out.println("Available flights:");
            for (int i = 0; i < results.size(); i++) {
                Flight f = results.get(i);
                System.out.printf("%d) %s to %s at %s (seats: %d)%n",
                        i + 1,
                        f.getFlightNumber(),
                        f.getDestination(),
                        f.getDepartureTime(),
                        f.getAvailableSeats());
            }

            System.out.print("Select a flight by number: ");
            String flightChoice = scanner.nextLine().trim();
            int index = Integer.parseInt(flightChoice) - 1;

            if (index < 0 || index >= results.size()) {
                System.out.println("Invalid selection.");
                return;
            }

            Flight selectedFlight = results.get(index);

            System.out.print("How many seats would you like to book? ");
            int seats = Integer.parseInt(scanner.nextLine().trim());

            try {
                Reservation reservation = flightService.bookFlight(customerName, selectedFlight, seats);
                System.out.println("Booking successful: " + reservation);
            } catch (IllegalStateException ex) {
                System.out.println("Booking failed: " + ex.getMessage());
            } catch (IllegalArgumentException ex) {
                System.out.println("Invalid input: " + ex.getMessage());
            }

        } catch (Exception e) {
            System.out.println("Invalid input. Please check your date and numbers.");
        }
    }

    private static void handleViewReservations(Scanner scanner, FlightService flightService) {
        System.out.print("Enter your name: ");
        String customerName = scanner.nextLine().trim();

        List<Reservation> reservations = flightService.getReservationsForCustomer(customerName);

        if (reservations.isEmpty()) {
            System.out.println("No reservations found for " + customerName + ".");
        } else {
            System.out.println("Reservations for " + customerName + ":");
            for (Reservation r : reservations) {
                System.out.println(r);
            }
        }
    }

    private static void seedSampleData(FlightService flightService) {
        LocalDateTime now = LocalDateTime.now();

        flightService.addFlight(new Flight("AA101", "New York", now.plusDays(1).withHour(9).withMinute(0), 10));
        flightService.addFlight(new Flight("AA102", "New York", now.plusDays(1).withHour(15).withMinute(30), 5));
        flightService.addFlight(new Flight("BA201", "London", now.plusDays(2).withHour(11).withMinute(15), 8));
        flightService.addFlight(new Flight("CA301", "Paris", now.plusDays(1).withHour(13).withMinute(45), 2));
    }
}
