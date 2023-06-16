package ua.lviv.iot.courseproject.controller;

import org.springframework.http.ResponseEntity;
import ua.lviv.iot.courseproject.model.Aircraft;

import java.util.Collection;

public interface AircraftController extends TemplateController<Aircraft, Integer> {
	ResponseEntity<Collection<Aircraft>> getTotalFlightTime();
}
