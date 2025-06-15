package com.jfkastro.pulsarsapi.util;

import com.jfkastro.pulsarsapi.model.Pulsar;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CSVParser {
    private CSVParser() {}

    /**
     * Parses a CSV file containing pulsar data and returns a list of Pulsar objects.
     *
     * @param filePath The path to the CSV file.
     * @return A list of Pulsar objects parsed from the CSV file.
     * @throws Exception If an error occurs while reading the file or parsing the data.
     */
    public static List<Pulsar> parseCSV(String filePath) throws Exception {
        List<Pulsar> pulsars = new ArrayList<>();

        int added = 0;
        int missed = 0;

        log.info(
                "Loading CSV file last modified on {}.",
                epochToDateString(new File(filePath).lastModified())
        );

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            String currentLine;

            while ((currentLine = bufferedReader.readLine()) != null) {
                if (currentLine.trim().isEmpty() || currentLine.startsWith("PSRJ")) {
                    continue;
                }

                String[] parsedTokens = currentLine.split(",");

                if(parsedTokens.length < 11) {
                    log.warn("Skipped pulsar {} due to improper data.", parsedTokens[0]);
                    missed++;

                    continue;
                }

                pulsars.add(
                        new Pulsar(
                                parsedTokens[0].trim(),
                                parseDoubleOrNull(parsedTokens[1]),
                                parseDoubleOrNull(parsedTokens[2]),
                                parseDoubleOrNull(parsedTokens[3]),
                                parseDoubleOrNull(parsedTokens[4]),
                                parseDoubleOrNull(parsedTokens[5]),
                                parseDoubleOrNull(parsedTokens[6]),
                                parseDoubleOrNull(parsedTokens[7]),
                                parseDoubleOrNull(parsedTokens[8]),
                                parseDoubleOrNull(parsedTokens[9]),
                                parseDoubleOrNull(parsedTokens[10]
                        ))
                );

                added++;
            }
        }

        log.info("Loaded {} pulsars, missed {} pulsars. {}% loaded.", added, missed,
                (added * 100.0) / (added + missed));

        return pulsars;
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
