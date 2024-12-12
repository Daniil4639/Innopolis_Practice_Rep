with seats_to_delete as (
	select seat_no
	from demo.bookings.seats
	where aircraft_code = '%s'
),
flights_to_delete as (
	select flight_id
	from demo.bookings.flights
	where aircraft_code = '%s'
),
tickets_to_delete as (
	select ticket_no
	from demo.bookings.ticket_flights
	where flight_id in (select * from flights_to_delete)
),
boarding_passes_delete as (
	delete from demo.bookings.boarding_passes
	where flight_id in (select * from flights_to_delete)
),
ticket_flights_delete as (
	delete from demo.bookings.ticket_flights
	where ticket_no in (select * from tickets_to_delete)
	and flight_id in (select * from flights_to_delete)
),
flights_delete as (
	delete from demo.bookings.flights
	where aircraft_code = '%s'
),
seats_delete as (
	delete from demo.bookings.seats
	where aircraft_code = '%s'
)
delete from demo.bookings.aircrafts_data
where aircraft_code = '%s'