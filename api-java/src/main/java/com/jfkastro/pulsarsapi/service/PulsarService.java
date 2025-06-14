package com.jfkastro.pulsarsapi.service;

import com.jfkastro.pulsarsapi.model.Pulsar;

import org.springframework.stereotype.Service;

import java.util.ArrayList;

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
        // TODO: Implement a better method of storage in order to take advantage of binary search and faster lookups.
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
        // TODO: Implement CSV loading logic.
        pulsars.add(new Pulsar());

        log.info("Loaded all pulsars from the CSV file.");

        // TODO: Implement proper date managing.
        // log.info("The pulsar's file was last updated on {}", "2099-12-31");
    }
}
