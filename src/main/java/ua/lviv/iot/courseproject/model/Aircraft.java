package ua.lviv.iot.courseproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Aircraft implements Record {
	private Integer id;
	private String flight;
	private String route;
	private int speed;
	private String airline;
	private String previousFlights;
	private int totalFlightTime;
	private String registrationInfo;

	@JsonIgnore
	@Override
	public String getHeaders() {
		return "id, flight, route, speed, airline, previousFlights, totalFlightTime, registrationInfo\n";
	}

	@Override
	public String toCSV() {
		return String.join(",", String.valueOf(id), flight, route, String.valueOf(speed), airline,
				String.valueOf(totalFlightTime), registrationInfo);
	}
}
