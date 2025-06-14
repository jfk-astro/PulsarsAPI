package com.jfkastro.pulsarsapi.controller;

import com.jfkastro.pulsarsapi.model.Pulsar;
import com.jfkastro.pulsarsapi.service.PulsarService;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/pulsars")
@Slf4j
public class PulsarController {
    private final PulsarService pulsarService;

    public PulsarController(PulsarService pulsarService) {
        this.pulsarService = new PulsarService();
    }

    @GetMapping("/{name}")
    public ResponseEntity<Pulsar> getPulsarByName(@PathVariable String name) {
        Pulsar pulsar = pulsarService.getPulsarFromName(name);

        if (pulsar != null) {
            log.info("Pulsar found: {}", pulsar);
            return ResponseEntity.ok(pulsar);
        } else {
            log.warn("Pulsar not found with name: {}", name);
            return ResponseEntity.notFound().build();
        }
    }
}
