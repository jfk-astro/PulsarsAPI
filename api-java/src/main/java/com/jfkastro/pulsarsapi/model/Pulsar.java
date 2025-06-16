package com.jfkastro.pulsarsapi.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pulsar {
    public static long previousId = 0;

    private long id;

    private String name;
    private String jName;

    private Double p1;
    private Double p0Err;
    private Double bSurf;
    private Double dist;
    private Double p1Err;
    private Double age;
    private Double dmErr;
    private Double p0;
    private Double dm;
    private Double edot;

    public Pulsar(String name, Double p1, Double p0Err,
                  Double bSurf, Double distance, Double p1Err,
                  Double age, Double dmErr, Double p0,
                  Double dm, Double edot) {

        this.name = name;
        this.jName = name.replace(" ", "_");

        this.p1 = p1;
        this.p0Err = p0Err;
        this.bSurf = bSurf;
        this.dist = distance;
        this.p1Err = p1Err;
        this.age = age;
        this.dmErr = dmErr;
        this.p0 = p0;
        this.dm = dm;
        this.edot = edot;
        this.id = previousId;

        ++previousId;
    }
}
