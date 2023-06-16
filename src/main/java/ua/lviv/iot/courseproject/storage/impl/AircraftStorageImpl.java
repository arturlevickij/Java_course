package ua.lviv.iot.courseproject.storage.impl;

import ua.lviv.iot.courseproject.model.Aircraft;
import ua.lviv.iot.courseproject.storage.AircraftStorage;
import ua.lviv.iot.courseproject.manager.FileManager;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AircraftStorageImpl implements AircraftStorage {
	private final FileManager manager = new FileManager();

	@Override
	public void writeToCSV(final Aircraft aircraft, final String pathToFiles) throws IOException {
		if (aircraft == null) {
			return;
		}

		File directory = new File(pathToFiles).getParentFile();
		if (!directory.exists() && !directory.mkdirs()) {
			System.err.println("Не вдалося створити директорію: " + directory.getAbsolutePath());
			return;
		}

		boolean fileExists = manager.fileExists(pathToFiles);

		try (BufferedWriter writer = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(pathToFiles, true), StandardCharsets.UTF_8))) {
			if (!fileExists) {
				writer.write(aircraft.getHeaders());
			}
			writer.write(aircraft.toCSV());
			writer.write("\n");
		}
	}

	@Override
	public void deleteFromCSV(final Integer id, final File[] files) throws IOException {
		if (files != null) {
			for (File file : files) {
				List<String> lines = manager.readLinesFromFile(file);
				for (String line : lines) {
					Aircraft entity = parseCSV(line);
					if (entity.getId().equals(id)) {
						lines.remove(line);
						manager.writeLinesToFile(file, lines, entity);
						break;
					}
				}
			}
		}
	}

	@Override
	public void changeEntityByID(final Integer id, final Aircraft entity, final File[] files) throws IOException {
		if (files != null) {
			for (File file : files) {
				List<String> lines = manager.readLinesFromFile(file);
				for (int i = 0; i < lines.size(); i++) {
					String line = lines.get(i);
					Aircraft car = parseCSV(line);
					if (car.getId().equals(id)) {
						entity.setId(id);
						lines.set(i, entity.toCSV());
						manager.writeLinesToFile(file, lines, entity);
						break;
					}
				}
			}
		}
	}

	@Override
	public Map<Integer, Aircraft> readFromCSV(final File monthDirectory) {
		Map<Integer, Aircraft> aircraftMap = new HashMap<>();

		if (monthDirectory.exists() && monthDirectory.isDirectory()) {
			File[] files = monthDirectory.listFiles();
			if (files != null) {
				for (File file : files) {
					try (BufferedReader reader = new BufferedReader(
							new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
						String line;
						reader.readLine();
						while ((line = reader.readLine()) != null) {
							Aircraft aircraft = parseCSV(line);
							aircraftMap.put(aircraft.getId(), aircraft);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

		return aircraftMap;
	}

	private Aircraft parseCSV(final String csvLine) {

		String[] values = csvLine.split(",");

		Integer id = Integer.valueOf(values[0]);
		String flight = values[1];
		String route = values[2];
		Integer speed = Integer.valueOf(values[3]);
		String airline = values[4];
		String previousFlights = values[5];
		Integer totalFlightTime = Integer.valueOf(values[6]);
		String registrationInfo = values[7];

		return new Aircraft(id, flight, route, speed, airline, previousFlights, totalFlightTime, registrationInfo);
	}

	@Override
	public Integer getLastId(final Map<Integer, Aircraft> aircrafts) {
		return aircrafts.values().stream().mapToInt(Aircraft::getId).max().orElse(0);
	}
}
