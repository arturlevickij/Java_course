package ua.lviv.iot.courseproject.service.impl;

import org.springframework.stereotype.Service;
import ua.lviv.iot.courseproject.manager.FileManager;
import ua.lviv.iot.courseproject.model.Aircraft;
import ua.lviv.iot.courseproject.service.AircraftService;
import ua.lviv.iot.courseproject.storage.AircraftStorage;
import ua.lviv.iot.courseproject.storage.impl.AircraftStorageImpl;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AircraftServiceImpl implements AircraftService {
	private final Aircraft entityInstance = new Aircraft();

	private final AircraftStorage storage = new AircraftStorageImpl();
	private final FileManager manager = new FileManager();
	private final File[] files = manager.getFileFromCurrentMonth(entityInstance);

	private final Map<Integer, Aircraft> aircrafts;
	private Integer nextAvailableID;

	public AircraftServiceImpl() {
		this.aircrafts = storage.readFromCSV(new File(manager.getMonthDirectoryPath(entityInstance)));
		this.nextAvailableID = storage.getLastId(aircrafts) + 1;
	}

	@Override
	public Map<Integer, Aircraft> getAircrafts() {
		return new HashMap<>(aircrafts);
	}

	@Override
	public Aircraft getById(final Integer id) {
		return aircrafts.get(id);
	}

	@Override
	public Collection<Aircraft> getAll() {
		return aircrafts.values();
	}

	@Override
	public Aircraft create(final Aircraft entity) throws IOException {
		String fileName = manager.getFilePath(entity);
		entity.setId(nextAvailableID++);
		aircrafts.put(entity.getId(), entity);
		storage.writeToCSV(entity, fileName);
		return entity;
	}

	public Collection<Aircraft> getTotalFlightTime() {
		return aircrafts.values().stream().filter(aircraft -> aircraft.getTotalFlightTime() > 10)
				.collect(Collectors.toList());
	}

	@Override
	public Aircraft update(final Integer key, final Aircraft entity) throws IOException {
		if (aircrafts.containsKey(key)) {
			entity.setId(key);
			storage.changeEntityByID(key, entity, files);
			aircrafts.put(key, entity);
			return entity;
		}
		return null;
	}

	@Override
	public void delete(final Integer key) throws IOException {
		storage.deleteFromCSV(key, files);
		aircrafts.remove(key);
	}

}
