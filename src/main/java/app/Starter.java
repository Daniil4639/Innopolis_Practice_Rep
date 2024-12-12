package app;

import app.dto.DTO_AircraftAndFlightsCount;
import app.dto.DTO_AirportData;
import app.dto.DTO_Customer;
import app.dto.DTO_Ticket;
import app.repository.AircraftRepository;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class Starter {

    public static void main(String[] args) {
        Properties properties = new Properties();

        try (FileInputStream fileInputStream = new FileInputStream("src/main/resources/db.properties")) {
            properties.load(fileInputStream);
        }
        catch (IOException ex) {
            System.out.println("properties not found!");
            System.out.println(ex.getMessage());
            return;
        }

        AircraftRepository repository = new AircraftRepository(properties);

        testFirstSelectQuery(repository);
        testSecondSelectQuery(repository);
        testThirdSelectQuery(repository);

        testFirstUpdateQuery(repository);
        testSecondUpdateQuery(repository);
        testThirdUpdateQuery(repository);

        testFirstDeleteQuery(repository);
        testSecondDeleteQuery(repository);

        testFirstInsertQuery(repository);
        testSecondInsertQuery(repository);
    }

    private static void testFirstSelectQuery(AircraftRepository repository) {
        try {
            List<DTO_AircraftAndFlightsCount> res = repository
                    .getAircraftOrderedByFlightsCount();

            printResult("1 - SELECT", res);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private static void testSecondSelectQuery(AircraftRepository repository) {
        try {
            List<DTO_Customer> res = repository
                    .getTop5Customers();

            printResult("2 - SELECT", res);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private static void testThirdSelectQuery(AircraftRepository repository) {
        try {
            DTO_AirportData res = repository
                    .getAirportDataByCode("SVX");

            printResult("3 - SELECT", res);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private static void testFirstUpdateQuery(AircraftRepository repository) {
        try {
            Integer res = repository.updateSeatsFare(List.of(
                    List.of("Business", "773", "13E"),
                    List.of("Economy", "320", "5F"),
                    List.of("Comfort", "SU9", "5E")
            ));

            printResult("4 - UPDATE", res.toString() + " / 3 queries are successful!");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private static void testSecondUpdateQuery(AircraftRepository repository) {
        try {
            Integer res = repository.updateAircraftRange(List.of(
                    Map.entry(3500, "SU9"),
                    Map.entry(4100, "733")
            ));

            printResult("5 - UPDATE", res.toString() + " / 2 queries are successful!");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private static void testThirdUpdateQuery(AircraftRepository repository) {
        try {
            Integer res = repository.updateTicketAmount(List.of(
                    new DTO_Ticket(30625, "Business", 10000L)
            ));

            printResult("6 - UPDATE", res.toString() + " / 1 queries are successful!");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private static void testFirstDeleteQuery(AircraftRepository repository) {
        try {
            repository.deleteTicketByNumber("0005432043100");

            printResult("7 - DELETE", "Ticket №0005432043100 was deleted successfully!");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private static void testSecondDeleteQuery(AircraftRepository repository) {
        try {
            repository.deleteAircraftByCode("319");

            printResult("8 - DELETE", "Aircraft '319' was deleted successfully!");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private static void testFirstInsertQuery(AircraftRepository repository) {
        try {
            repository.insertAircraftData("ABC", "some_plain",
                    "какой-то самолет", 15000L);

            printResult("9 - INSERT", "Aircraft 'ABC' was added successfully!");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private static void testSecondInsertQuery(AircraftRepository repository) {
        try {
            repository.insertSeatData("320", "1234", "Business");

            printResult("10 - INSERT", "Seat '1234' was added successfully!");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private static void printResult(String meta, Object res) {
        System.out.println("Results of query №" + meta + ":");
        System.out.println(res);
        System.out.println();
    }
}
