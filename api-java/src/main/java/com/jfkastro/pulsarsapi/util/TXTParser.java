package com.jfkastro.pulsarsapi.util;

import com.jfkastro.pulsarsapi.model.Pulsar;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Slf4j
public class TXTParser {
    private TXTParser() {}

    public static Pulsar parseTxt(String updatePath, int desiredLine) throws Exception {
        try (BufferedReader parser = new BufferedReader(new FileReader(updatePath))) {
            int currentLine = 1;
            String activeLine;

            log.info(
                    "Loading pulsar from file {} on line {} last modified {}.",
                    updatePath,
                    desiredLine,
                    epochToDateString(new File(updatePath).lastModified())
            );

            while (currentLine < desiredLine) {
                parser.readLine();
                ++currentLine;
            }

            activeLine = parser.readLine();

            String[] pulsarAttributes = activeLine.split(",");

            if (pulsarAttributes.length < 11 || activeLine.trim().isEmpty()) {
                log.warn("Pulsar {} was not created due to missing data.", pulsarAttributes[0]);
                return null;
            }

            Pulsar newPulsar = new Pulsar(
                    pulsarAttributes[0].trim(),
                    parseDoubleOrNull(pulsarAttributes[1]),
                    parseDoubleOrNull(pulsarAttributes[2]),
                    parseDoubleOrNull(pulsarAttributes[3]),
                    parseDoubleOrNull(pulsarAttributes[4]),
                    parseDoubleOrNull(pulsarAttributes[5]),
                    parseDoubleOrNull(pulsarAttributes[6]),
                    parseDoubleOrNull(pulsarAttributes[7]),
                    parseDoubleOrNull(pulsarAttributes[8]),
                    parseDoubleOrNull(pulsarAttributes[9]),
                    parseDoubleOrNull(pulsarAttributes[10])
            );

            log.info("Loaded pulsar {}.", newPulsar.getName());

            return newPulsar;
        }
    }

    private static Double parseDoubleOrNull(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        try {
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException e) {
            log.error("Error parsing double value: {}", value, e);

            return null;
        }
    }

    private static String epochToDateString(long epoch) {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(epoch), ZoneId.systemDefault()).toString();
    }
}
