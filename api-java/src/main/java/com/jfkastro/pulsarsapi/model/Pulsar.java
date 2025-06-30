package com.jfkastro.pulsarsapi.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pulsar {
    private static long previousId = 0;

    private long id;

    private String name;
    private String bName;
    private String journalName;

    private Double age;
    private Double distance;

    private Double period;
    private Double periodDerivative;

    private Double dispersion;

    private Double magneticFieldStrength;
    private Double spinDownLuminosity;

    private Double rightAscensionDegrees;
    private Double declinationDegrees;
    private Double parallax;

    private Double raProperMotion;
    private Double decProperMotion;

    private Double galacticLongitude;
    private Double galacticLatitude;

    private Double eclipticLongitude;
    private Double eclipticLatitude;

    private Double properMotionEclipticLongitude;
    private Double properMotionEclipticLatitude;

    private Double properMotionGalacticLongitude;
    private Double properMotionGalacticLatitude;

    private Double dispersionError;
    private Double periodError;
    private Double periodDerivativeError;
    private Double rightAscensionError;
    private Double declinationError;
    private Double parallaxError;
    private Double raProperMotionError;
    private Double decProperMotionError;

    private Double epochOfPosition;
    private String rightAscensionJournal;
    private String declinationJournal;

    public Pulsar() {
        this.id = previousId++;
    }
}

