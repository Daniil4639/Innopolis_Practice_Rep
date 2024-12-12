select ad.aircraft_code as code, count(*) as flights_count
from aircrafts_data ad
join flights f on ad.aircraft_code = f.aircraft_code
group by ad.aircraft_code
order by flights_count desc