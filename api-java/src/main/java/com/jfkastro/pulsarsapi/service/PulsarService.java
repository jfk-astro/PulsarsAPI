package com.jfkastro.pulsarsapi.service;

import com.jfkastro.pulsarsapi.model.Pulsar;

import com.jfkastro.pulsarsapi.util.CSVParser;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PulsarService {
    private final ArrayList<Pulsar> pulsars;

    public PulsarService() {
        this.pulsars = new ArrayList<>();

        loadPulsarsFromCSV();
    }

    /**
     * Retrieves a pulsar using binary search given its name, if there is no suitable pulsar, it will return null.
     * @param name The name of the pulsar to retrieve.
     *             If the name is not found, it will return null.
     *             If the name is found, it will return the pulsar object.
     * @return The pulsar object if found, otherwise null.
     */
    public Pulsar getPulsarFromName(String name) {
        // Similar to the addition, it uses binary search to find the pulsar by its name.
        int high = pulsars.size() - 1;
        int low = 0;

        while (low <= high) {
            int midpoint = (low + high) / 2;

            Pulsar currentPulsar = pulsars.get(midpoint);
            int comparison = currentPulsar.getName().compareToIgnoreCase(name);

            if (comparison == 0) {
                log.info("Found pulsar: {}", currentPulsar.getName());
                return currentPulsar;
            } else if (comparison < 0) {
                low = midpoint + 1;
            } else {
                high = midpoint - 1;
            }
        }

        log.warn("Pulsar with name '{}' not found.", name);
        return null;
    }

    /**
     * Removes a pulsar from the list using its name.
     * @param name The name of the pulsar to remove.
     * @return The pulsar object if found, otherwise null.
     */
    public Pulsar removePulsarFromName(String name) {
        // This method uses binary search to find the pulsar by its name and removes it from the list.

        int high = pulsars.size() - 1;
        int low = 0;

        while (low <= high) {
            int midpoint = (low + high) / 2;

            Pulsar currentPulsar = pulsars.get(midpoint);
            int comparison = currentPulsar.getName().compareToIgnoreCase(name);

            if (comparison == 0) {
                pulsars.remove(midpoint);
                log.info("Removed pulsar: {}", currentPulsar.getName());
                return currentPulsar;
            } else if (comparison < 0) {
                low = midpoint + 1;
            } else {
                high = midpoint - 1;
            }
        }

        log.warn("Pulsar with name '{}' not found for removal.", name);
        return null;
    }

    private void loadPulsarsFromCSV() {
        try {
            List<Pulsar> pulsarList = CSVParser.parseCSV("src/main/resources/pulsars.csv");

            // TODO: Implement auto sorting in the CSVParser itself, this should suffice for now, though.
            for(Pulsar p : pulsarList) {
                addPulsar(p);
            }
        } catch (Exception exception) {
            log.error("Error loading pulsars from CSV file: {}", exception.getMessage());
        }
    }

    private void addPulsar(Pulsar pulsar) {
        // This adds the pulsar using the binary search algorithm.
        // Whenever a pulsar is added, it will automatically be placed in the correct position.

        int low = 0;
        int high = pulsars.size() - 1;

        while (low <= high) {
            int midpoint = (low + high) / 2;
            int comparator = pulsars.get(midpoint).getName().compareTo(pulsar.getName());

            if (comparator == 0) {
                log.warn("Pulsar with name '{}' already exists.", pulsar.getName());
            } else if (comparator < 0) {
                high = midpoint - 1;
            } else {
                low = midpoint + 1;
            }
        }

        pulsars.add(low, pulsar);
        log.info("Pulsar '{}' added successfully.", pulsar.getName());
    }
}
