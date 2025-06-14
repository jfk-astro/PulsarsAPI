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

    public Pulsar getPulsarFromName(String name) {
        for (Pulsar pulsar : pulsars) {
            if (pulsar.getName().equalsIgnoreCase(name)) {
                log.info("Found pulsar: {}", pulsar.getName());
                return pulsar;
            }
        }

        log.warn("Pulsar with name '{}' not found.", name);
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
