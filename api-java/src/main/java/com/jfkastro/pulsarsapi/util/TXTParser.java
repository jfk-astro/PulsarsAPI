package com.jfkastro.pulsarsapi.util;

import com.jfkastro.pulsarsapi.model.Pulsar;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Deprecated
@Slf4j
public class TXTParser {
    private TXTParser() {}

    @Deprecated
    public static Pulsar parseTxt(String updatePath, int desiredLine) throws Exception {
        return null;
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
