package com.jfkastro.pulsarsapi.controller;

import com.jfkastro.pulsarsapi.exception.PulsarException;
import com.jfkastro.pulsarsapi.model.Pulsar;
import com.jfkastro.pulsarsapi.service.PulsarService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    private final String accessKey;

    public PulsarController(PulsarService pulsarService) {
        this.pulsarService = pulsarService;
        this.accessKey = pulsarService.generateRandomNumber();

        log.info("PulsarController initialized with access key: {}", accessKey);
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

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePulsarById(@PathVariable long id, String key) {
        if (!key.equalsIgnoreCase(accessKey)) {
            return new ResponseEntity<String>("Not Authorized", HttpStatus.UNAUTHORIZED);
        }

        Pulsar pulsar = pulsarService.removePulsarFromId(id);

        if (pulsar != null) {
            log.info("Pulsar with ID: {} was successfully deleted.", id);
            return new ResponseEntity<String>("Pulsar was successfully deleted.", HttpStatus.OK);
        }

        log.warn("Pulsar with ID: {} was not found to be deleted.", id);
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<String> deletePulsarByName(@PathVariable String name, String key) {
        if (!key.equalsIgnoreCase(accessKey)) {
            return new ResponseEntity<String>("Not Authorized", HttpStatus.UNAUTHORIZED);
        }

        Pulsar pulsar = pulsarService.removePulsarFromName(name);

        if (pulsar != null) {
            log.info("Pulsar with name: {} was successfully deleted.", name);
            return new ResponseEntity<String>("Pulsar was successfully deleted.", HttpStatus.OK);
        }

        log.warn("Pulsar with name: {} was not found to be deleted.", name);
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> replacePulsarById(@PathVariable long id, String key, Pulsar newPulsar) {
        if (!key.equalsIgnoreCase(accessKey)) {
            return new ResponseEntity<String>("Not Authorized", HttpStatus.UNAUTHORIZED);
        }

        Pulsar pulsar = pulsarService.replacePulsarById(id, newPulsar);

        if (pulsar != null) {
            log.info("Pulsar with ID: {} was successfully replaced.", id);
            return new ResponseEntity<String>("Pulsar was successfully replaced.", HttpStatus.OK);
        }

        log.warn("Pulsar with ID: {} was not found for replacement.", id);
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/new")
    public ResponseEntity<String> addNewPulsar(Pulsar addedPulsar, String key) {
        if (!key.equalsIgnoreCase(accessKey)) {
            return new ResponseEntity<String>("Not authorized", HttpStatus.UNAUTHORIZED);
        }

        try {
            pulsarService.addPulsar(addedPulsar);
            return new ResponseEntity<String>("Pulsar was successfully added.", HttpStatus.OK);

        } catch (PulsarException e) {
            log.error("Error adding the pulsar: {}.", e.getMessage());
            return new ResponseEntity<String>("Error adding the pulsar.", HttpStatus.BAD_REQUEST);
        }
    }
}
