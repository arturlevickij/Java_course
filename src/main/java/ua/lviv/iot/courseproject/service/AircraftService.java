package ua.lviv.iot.courseproject.service;

import ua.lviv.iot.courseproject.model.Aircraft;

import java.util.Collection;
import java.util.Map;

public interface AircraftService extends TemplateService<Aircraft, Integer> {
	Collection<Aircraft> getTotalFlightTime();

	Map<Integer, Aircraft> getAircrafts();
}
