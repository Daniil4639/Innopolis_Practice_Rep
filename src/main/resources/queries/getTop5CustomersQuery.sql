select t.passenger_name as name, sum(tf.amount) as sum_wastes
from tickets t
join ticket_flights tf on tf.ticket_no = t.ticket_no
group by t.passenger_name
order by sum_wastes desc
limit 5