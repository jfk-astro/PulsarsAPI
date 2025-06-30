package com.jfkastro.pulsarsapi.util;

import com.jfkastro.pulsarsapi.exception.PulsarException;
import com.jfkastro.pulsarsapi.model.Pulsar;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

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

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String headerLine = reader.readLine();

            if (headerLine == null) throw new PulsarException("CSV file is empty.");

            String[] headers = headerLine.split(",");

            Map<String, Integer> col = new HashMap<>();
            for (int i = 0; i < headers.length; i++) {
                col.put(headers[i].trim().toUpperCase(), i);
            }

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] fields = line.split(",", -1);

                try {
                    Pulsar p = new Pulsar();

                    p.setName(getStr(fields, col, "PSRJ"));
                    p.setBName(getStr(fields, col, "BNAME"));
                    p.setJournalName(p.getName().replace(" ", "_"));

                    p.setPeriod(parse(fields, col, "P0"));
                    p.setPeriodError(parse(fields, col, "P0_ERR"));
                    p.setPeriodDerivative(parse(fields, col, "P1"));
                    p.setPeriodDerivativeError(parse(fields, col, "P1_ERR"));

                    p.setDispersion(parse(fields, col, "DM"));
                    p.setDispersionError(parse(fields, col, "DM_ERR"));

                    p.setAge(parse(fields, col, "AGE"));
                    p.setDistance(parse(fields, col, "DIST"));

                    p.setMagneticFieldStrength(parse(fields, col, "BSURF"));
                    p.setSpinDownLuminosity(parse(fields, col, "EDOT"));

                    p.setRightAscensionDegrees(parse(fields, col, "RAJD"));
                    p.setDeclinationDegrees(parse(fields, col, "DECJD"));
                    p.setParallax(parse(fields, col, "PX"));
                    p.setParallaxError(parse(fields, col, "PX_ERR"));

                    p.setRaProperMotion(parse(fields, col, "PMRA"));
                    p.setRaProperMotionError(parse(fields, col, "PMRA_ERR"));
                    p.setRightAscensionError(parse(fields, col, "RAJ_ERR"));

                    p.setDecProperMotion(parse(fields, col, "PMDEC"));
                    p.setDecProperMotionError(parse(fields, col, "PMDEC_ERR"));
                    p.setDeclinationError(parse(fields, col, "DECJ_ERR"));

                    p.setGalacticLongitude(parse(fields, col, "GL"));
                    p.setGalacticLatitude(parse(fields, col, "GB"));

                    p.setEclipticLongitude(parse(fields, col, "ELONG"));
                    p.setEclipticLatitude(parse(fields, col, "ELAT"));

                    p.setProperMotionEclipticLongitude(parse(fields, col, "PMELONG"));
                    p.setProperMotionEclipticLatitude(parse(fields, col, "PMELAT"));

                    p.setProperMotionGalacticLongitude(parse(fields, col, "PML"));
                    p.setProperMotionGalacticLatitude(parse(fields, col, "PMB"));

                    p.setEpochOfPosition(parse(fields, col, "POSEPOCH"));

                    p.setRightAscensionJournal(getStr(fields, col, "RAJ"));
                    p.setDeclinationJournal(getStr(fields, col, "DECJ"));

                    pulsars.add(p);

                    added++;
                } catch (Exception e) {
                    missed++;

                    log.warn("Skipping row due to error: {}", e.getMessage());
                }
            }
        }

        log.info("Loaded {} pulsars, missed {} ({}% success).", added, missed,
                added + missed == 0 ? 0 : (added * 100.0) / (added + missed));

        return pulsars;
    }


    private static Double parse(String[] fields, Map<String, Integer> map, String key) {
        try {
            int index = map.getOrDefault(key.toUpperCase(), -1);

            if (index == -1 || index >= fields.length) return null;

            String value = fields[index].trim();

            return value.isEmpty() ? null : Double.parseDouble(value);
        } catch (Exception e) {
            return null;
        }
    }

    private static String getStr(String[] fields, Map<String, Integer> map, String key) {
        int index = map.getOrDefault(key.toUpperCase(), -1);

        if (index == -1 || index >= fields.length) return null;

        String value = fields[index].trim();

        return value.isEmpty() ? null : value;
    }
}
