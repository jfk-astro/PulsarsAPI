package com.jfkastro.pulsarsapi.controller;

import com.jfkastro.pulsarsapi.model.Pulsar;
import com.jfkastro.pulsarsapi.service.PulsarService;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RestController
@RequestMapping("/api/pulsars")
@Slf4j
public class PulsarController {
    private final PulsarService pulsarService;
    public String key;

    public PulsarController(PulsarService pulsarService) {
        this.pulsarService = pulsarService;
        String key = pulsarService.generateRandomNumber();

    }

    /**
     * Retrieves all pulsars.
     *
     * @return A ResponseEntity containing a list of all pulsars or a no content status if none are found.
     */
    @GetMapping
    public ResponseEntity<List<Pulsar>> getPulsars() {
        List<Pulsar> pulsars = pulsarService.getAllPulsars();

        if (pulsars.isEmpty()) {
            log.warn("No pulsars found.");

            return ResponseEntity.noContent().build();
        } else {
            log.info("Returning {} pulsars.", pulsars.size());

            return ResponseEntity.ok(pulsars);
        }
    }

    /**
     * Retrieves a pulsar by its name.
     *
     * @param name The name of the pulsar to retrieve.
     * @return A ResponseEntity containing the pulsar if found, or a not found status if not found.
     */
    @GetMapping("/{name}")
    public ResponseEntity<Pulsar> getPulsarByName(@PathVariable String name) {
        Pulsar pulsar = pulsarService.getPulsarFromName(name);

        if (pulsar != null) {
            log.info("Pulsar found: {}", pulsar);
            return ResponseEntity.ok(pulsar);
        }

        log.warn("Pulsar not found with name: {}", name);
        return ResponseEntity.notFound().build();
    }

    /**
     * Retrieves a pulsar by ID.
     *
     * @param id The id of the pulsar to retrieve.
     * @return A ResponseEntity containing the pulsar if found, or a not found status if not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Pulsar> getPulsarById(@PathVariable long id) {
        Pulsar pulsar = pulsarService.getPulsarFromId(id);

        if (pulsar != null) {
            log.info("Pulsar found: {}", pulsar);
            return ResponseEntity.ok(pulsar);
        }

        log.warn("Pulsar not found with ID: {}", id);
        return ResponseEntity.notFound().build();
    }
}
