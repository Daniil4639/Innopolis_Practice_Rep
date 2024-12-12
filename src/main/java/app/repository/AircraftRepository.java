package app.repository;

import app.dto.DTO_AircraftAndFlightsCount;
import app.dto.DTO_AirportData;
import app.dto.DTO_Customer;
import app.dto.DTO_Ticket;
import org.apache.commons.io.FileUtils;
import org.postgresql.util.PGobject;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

public class AircraftRepository {

    private final JdbcTemplate jdbcTemplate;

    public AircraftRepository(Properties properties) {
        jdbcTemplate = new JdbcTemplate(new DriverManagerDataSource(
                properties.getProperty("db.url"),
                properties.getProperty("db.user"),
                properties.getProperty("db.password")
        ));
    }

    public List<DTO_AircraftAndFlightsCount> getAircraftOrderedByFlightsCount() throws Exception {
        return jdbcTemplate.query(
                FileUtils.readFileToString(new File(Objects.requireNonNull(getClass()
                        .getResource("/queries/getAircraftOrderedByFlightsCountQuery.sql")).getFile()), "utf-8"),
                BeanPropertyRowMapper.newInstance(DTO_AircraftAndFlightsCount.class)
        );
    }

    public List<DTO_Customer> getTop5Customers() throws Exception {
        return jdbcTemplate.query(
                FileUtils.readFileToString(new File(Objects.requireNonNull(getClass()
                        .getResource("/queries/getTop5CustomersQuery.sql")).getFile()), "utf-8"),
                BeanPropertyRowMapper.newInstance(DTO_Customer.class)
        );
    }

    public DTO_AirportData getAirportDataByCode(String airportCode) throws Exception {
        return jdbcTemplate.queryForObject(
                String.format(FileUtils.readFileToString(new File(Objects.requireNonNull(getClass()
                        .getResource("/queries/getAirportDataQuery.sql")).getFile()), "utf-8"), airportCode),
                BeanPropertyRowMapper.newInstance(DTO_AirportData.class)
        );
    }

    public Integer updateSeatsFare(List<List<String>> params) throws Exception {
        Integer successQueries = 0;
        String query = FileUtils.readFileToString(new File(Objects.requireNonNull(getClass()
                .getResource("/queries/updateSeatFareQuery.sql")).getFile()));

        for (List<String> change: params) {
            try {
                jdbcTemplate.execute(String.format(query,
                        change.get(0), change.get(1), change.get(2)));

                successQueries++;
            } catch (DataAccessException ignored) {}
        }

        return successQueries;
    }

    public Integer updateAircraftRange(List<Map.Entry<Integer, String>> params) throws Exception {
        Integer successQueries = 0;
        String query = FileUtils.readFileToString(new File(Objects.requireNonNull(getClass()
                .getResource("/queries/updateAircraftRangeQuery.sql")).getFile()));

        for (Map.Entry<Integer, String> change: params) {
            try {
                jdbcTemplate.execute(String.format(query,
                        change.getKey(), change.getValue()));

                successQueries++;
            } catch (DataAccessException ignored) {}
        }

        return successQueries;
    }

    public Integer updateTicketAmount(List<DTO_Ticket> params) throws Exception {
        Integer successQueries = 0;
        String query = FileUtils.readFileToString(new File(Objects.requireNonNull(getClass()
                .getResource("/queries/updateSeatFareQuery.sql")).getFile()));

        for (DTO_Ticket ticket: params) {
            try {
                jdbcTemplate.execute(String.format(query,
                        ticket.getAmount(), ticket.getFlightId(), ticket.getFareConditions()));

                successQueries++;
            } catch (DataAccessException ignored) {}
        }

        return successQueries;
    }

    public void deleteTicketByNumber(String number) throws Exception {
        jdbcTemplate.execute(String.format(FileUtils.readFileToString(new File(Objects.requireNonNull(getClass()
                .getResource("/queries/deleteTicketQuery.sql")).getFile()), "utf-8"), number, number));
    }

    public void deleteAircraftByCode(String aircraftCode) throws Exception {
        jdbcTemplate.execute(String.format(FileUtils.readFileToString(new File(Objects.requireNonNull(getClass()
                .getResource("/queries/deleteAircraftByCodeQuery.sql")).getFile()), "utf-8"),
                aircraftCode, aircraftCode, aircraftCode, aircraftCode, aircraftCode));
    }

    public void insertAircraftData(String code, String enName, String ruName, Long range) throws Exception {
        PGobject pGobject = new PGobject();
        pGobject.setType("jsonb");
        pGobject.setValue("{\"en\": \"" + enName + "\", \"ru\": \"" + ruName + "\"}");

        jdbcTemplate.update(FileUtils.readFileToString(new File(Objects.requireNonNull(getClass()
                .getResource("/queries/insertAircraftDataQuery.sql")).getFile())),
                code, pGobject, range
        );
    }

    public void insertSeatData(String aircraftCode, String seatNo, String fare) throws Exception {
        jdbcTemplate.update(FileUtils.readFileToString(new File(Objects.requireNonNull(getClass()
                .getResource("/queries/insertSeatDataQuery.sql")).getFile())),
                aircraftCode, seatNo, fare
        );
    }
}
