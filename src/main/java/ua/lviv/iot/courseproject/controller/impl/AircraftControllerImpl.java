package ua.lviv.iot.courseproject.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.lviv.iot.courseproject.controller.AircraftController;
import ua.lviv.iot.courseproject.model.Aircraft;
import ua.lviv.iot.courseproject.service.AircraftService;

import java.io.IOException;
import java.util.Collection;

@RestController
@RequestMapping("/aircrafts")
public class AircraftControllerImpl implements AircraftController {
	private final AircraftService aircraftService;

	@Autowired
	public AircraftControllerImpl(final AircraftService aircraftService) {
		this.aircraftService = aircraftService;
	}

	@Override
	@GetMapping("/{id}")
	public ResponseEntity<Aircraft> getById(@PathVariable final Integer id) {
		Aircraft aircraft = aircraftService.getById(id);
		if (aircraft != null) {
			return ResponseEntity.ok(aircraft);
		}
		return ResponseEntity.notFound().build();
	}

	@Override
	@GetMapping
	public ResponseEntity<Collection<Aircraft>> getAll() {
		Collection<Aircraft> aircrafts = aircraftService.getAll();
		if (aircrafts.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(aircrafts);
	}

	@Override
	@PostMapping
	public ResponseEntity<Aircraft> create(@RequestBody final Aircraft entity) throws IOException {
		Aircraft aircraft = aircraftService.create(entity);
		return ResponseEntity.status(HttpStatus.CREATED).body(aircraft);
	}

	@Override
	@PutMapping("/{id}")
	public ResponseEntity<Aircraft> update(@PathVariable final Integer id, @RequestBody final Aircraft entity)
			throws IOException {
		if (!aircraftService.getAircrafts().containsKey(id)) {
			return ResponseEntity.noContent().build();
		}
		Aircraft aircraft = aircraftService.update(id, entity);
		return ResponseEntity.ok(aircraft);
	}

	@Override
	@DeleteMapping("/{id}")
	public ResponseEntity<Aircraft> delete(@PathVariable final Integer id) throws IOException {
		if (!aircraftService.getAircrafts().containsKey(id)) {
			return ResponseEntity.notFound().build();
		}
		aircraftService.delete(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/totalFlightTime")
	public ResponseEntity<Collection<Aircraft>> getTotalFlightTime() {
		Collection<Aircraft> totalFlightTime = aircraftService.getTotalFlightTime();
		if (totalFlightTime.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(totalFlightTime);
	}

}