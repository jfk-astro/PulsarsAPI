package com.jfkastro.pulsarsapi.service;

import com.jfkastro.pulsarsapi.model.Pulsar;

import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
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
                return pulsar;
            }
        }

        return null;
    }

    private void loadPulsarsFromCSV() {
        // TODO: Implement CSV loading logic.
        pulsars.add(new Pulsar());
    }
}
