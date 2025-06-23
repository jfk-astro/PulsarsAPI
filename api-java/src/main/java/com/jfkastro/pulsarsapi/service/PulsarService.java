package com.jfkastro.pulsarsapi.service;

import com.jfkastro.pulsarsapi.exception.PulsarException;

import com.jfkastro.pulsarsapi.model.Pulsar;

import com.jfkastro.pulsarsapi.util.CSVParser;

import com.jfkastro.pulsarsapi.util.TXTParser;
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
     * Retrieves all loaded pulsars.
     *
     * @return A list of all pulsars.
     */
    public List<Pulsar> getAllPulsars() {
        if (pulsars.isEmpty()) {
            log.warn("No pulsars are currently loaded.");
            return new ArrayList<>();
        }

        log.info("Retrieved {} pulsars.", pulsars.size());
        return new ArrayList<>(pulsars);
    }

    /**
     * Retrieves a specific pulsar from its assigned ID by brute forcing through the pulsar list.
     * If no such matching ID is found, the function will return null
     *
     * @param id The matching ID of the pulsar.
     * @return If the ID is matched, it will return the pulsar.
     *         If the ID is not matched, it will return null.
     */
    public Pulsar getPulsarFromId(long id) {
        for (Pulsar p : pulsars) {
            if (p.getId() == id) {
                return p;
            }
        }

        return null;
    }

    /**
     * Retrieves a pulsar using binary search given its name, if there is no suitable pulsar, it will return null.
     *
     * @param name The name of the pulsar to retrieve.
     *             If the name is not found, it will return null.
     *             If the name is found, it will return the pulsar object.
     * @return The pulsar object if found, otherwise null.
     */
    public Pulsar getPulsarFromName(String name) {
        // Similar to the addition, it uses binary search to find the pulsar by its name.

        if (name.isBlank()) {
            log.warn("Attempted to find pulsar with an empty or blank name.");
            return null;
        }

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

        if (name.isBlank()) {
            log.warn("Attempted to remove pulsar with an empty or blank name.");
            return null;
        }

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

    /**
     * Removes a pulsar from the list using its ID.
     *
     * @param id The ID attribute of each pulsar.
     * @return If a pulsar is found with a matching ID, then the function returns that pulsar.
     *         If no pulsar is found with a matching ID, then the function returns null.
     */
    public Pulsar removePulsarFromId(long id) {
        for (Pulsar p : pulsars) {
            if (p.getId() == id) {
                pulsars.remove(p);
                log.info("Removed pulsar of ID: {}", p.getName());
                return p;
            }
        }
        log.warn("Pulsar of ID '{}' not found for removal.", id);
        return null;
    }

    /**
     * Replaces a pulsar with another pulsar using the old pulsar's ID.
     *
     * @param oldId The old ID of the pulsar.
     * @param newPulsar The new pulsar object to replace the old pulsar.
     * @return If the ID corresponding to the desired pulsar is found, the old pulsar is returned.
     *         If the ID corresponding to the desired pulsar is not found, null is returned.
     */
    public Pulsar replacePulsarById(long oldId, Pulsar newPulsar) {
        for (int i = 0; i < pulsars.size(); ++i) {
            if (pulsars.get(i).getId() == oldId) {
                Pulsar oldPulsar = pulsars.get(i);
                pulsars.set(i, newPulsar);
                return oldPulsar;
            }
        }
        log.warn("Pulsar of ID '{}' not found for replacement.", oldId);
        return null;
    }

    private void loadPulsarsFromCSV() {
        try {
            List<Pulsar> pulsarList = CSVParser.parseCSV("src/main/resources/pulsars.csv");

            for (Pulsar p : pulsarList) {
                addPulsar(p);
            }
        } catch (Exception exception) {
            log.error("Error loading pulsars from CSV file: {}", exception.getMessage());
        }
    }

    @Deprecated
    private Pulsar createPulsarFromTXT(int line) {
        try {
            return TXTParser.parseTxt("src/main/resources/addPulsar.txt", line);
        } catch (Exception exception) {
            log.error("Error creating pulsar from TXT file: {}", exception.getMessage());
        }
        return null;
    }

    private void addPulsar(Pulsar pulsar) throws PulsarException {
        // This adds the pulsar using the binary search algorithm.
        // Whenever a pulsar is added, it will automatically be placed in the correct position.

        if (pulsar == null || pulsar.getName() == null || pulsar.getName().isEmpty()) {
            log.warn("Attempted to add a pulsar with an empty or null name.");

            throw new PulsarException("Pulsar name cannot be null or empty.");
        }

        int low = 0;
        int high = pulsars.size() - 1;

        while (low <= high) {
            int midpoint = (low + high) / 2;
            int comparator = pulsars.get(midpoint).getName().compareTo(pulsar.getName());

            if (comparator == 0) {
                log.warn("Pulsar with name '{}' already exists.", pulsar.getName());
                return;
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
