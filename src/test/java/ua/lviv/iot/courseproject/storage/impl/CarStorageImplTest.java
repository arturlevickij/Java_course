package ua.lviv.iot.courseproject.storage.impl;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.lviv.iot.courseproject.model.Aircraft;
import ua.lviv.iot.courseproject.storage.AircraftStorage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CarStorageImplTest {
    private final String PATH_TO_EXPECTED = String.join(File.separator,
            "src", "test", "resources", "aircraft", "expected", "expected.csv");
    final String PATH_TO_EXPECTED_DELETE = String.join(File.separator,
            "src", "test", "resources", "aircraft", "expected", "delete_expected.csv");
    private final String PATH_TO_EXPECTED_PUT = String.join(File.separator,
            "src", "test", "resources", "aircraft", "expected", "expected_put.csv");
    private final String PATH_TO_EMPTY = String.join(File.separator,
            "src", "test", "resources", "aircraft", "expected", "expected_empty.csv");

    private final String PATH_TO_ACTUAL = String.join(File.separator,
            "src", "test", "resources", "aircraft", "actual", "write", "actual.csv");
    private final String PATH_TO_ACTUAL_FILES = String.join(File.separator,
            "src", "test", "resources", "aircraft", "actual", "write") + File.separator;


    AircraftStorage storage;
    Aircraft aircraft;
    Map<Integer, Aircraft> aircrafts;

    @BeforeEach
    void setUp() throws IOException {
        storage = new AircraftStorageImpl();
        aircraft = new Aircraft(1, "USA", "Germany",600,"Airway","Ukraine",40,"71776");
        aircrafts = new HashMap<>();

        File directory = new File(PATH_TO_ACTUAL).getParentFile();
        if (!directory.exists() && !directory.mkdirs()) {
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(PATH_TO_ACTUAL),
                StandardCharsets.UTF_8))) {
            writer.write(aircraft.getHeaders());
            writer.write(aircraft.toCSV());
            writer.newLine();
        }
    }

    @Test
    void writeToCSV() throws IOException {

        storage.writeToCSV(aircraft, PATH_TO_ACTUAL);

        Path expected = new File(PATH_TO_EXPECTED).toPath();
        Path actual = new File(PATH_TO_ACTUAL).toPath();

        assertEquals(-1L, Files.mismatch(expected, actual));
    }

    @Test
    void writeNullToCSV() throws IOException {

        storage.writeToCSV(null, PATH_TO_ACTUAL);

        Path expected = new File(PATH_TO_EMPTY).toPath();
        Path actual = new File(PATH_TO_ACTUAL).toPath();

        assertEquals(-1L, Files.mismatch(expected, actual));
    }

    @Test
    void getLastIdIfMapClear() {
        assertTrue(aircrafts.isEmpty());
    }

    @Test
    void readFromCSV() {
    	aircrafts.put(1, aircraft);

        File path = new File(PATH_TO_ACTUAL_FILES);
        assertEquals(aircrafts.size(), storage.readFromCSV(path).size());
    }

    @Test
    void getLastId() {
        assertEquals(0, storage.getLastId(aircrafts));
    }

    @Test
    void readFromNullCSV() {
        File path = new File(PATH_TO_EMPTY);
        assertTrue(storage.readFromCSV(path).isEmpty());
    }

    @Test
    void changeEntityByID() throws IOException {
    	Aircraft aircraft2 = new Aircraft(1, "UA", "Ger",400,"Airway","Ukraine",20,"70776");
        Path expected_put = new File(PATH_TO_EXPECTED_PUT).toPath();
        Path actual = new File(PATH_TO_ACTUAL).toPath();

        File monthDirectory = new File(PATH_TO_ACTUAL_FILES);

        storage.changeEntityByID(1, aircraft2, monthDirectory.listFiles());

        assertEquals(-1L, Files.mismatch(expected_put, actual));
    }

    @Test
    void deleteFromCSV() throws IOException {
        Path expected_delete = new File(PATH_TO_EXPECTED_DELETE).toPath();
        Path actual = new File(PATH_TO_ACTUAL).toPath();

        File monthDirectory = new File(PATH_TO_ACTUAL_FILES);
        storage.deleteFromCSV(1, monthDirectory.listFiles());

        assertEquals(-1L, Files.mismatch(expected_delete, actual));
    }
}