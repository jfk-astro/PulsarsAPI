package com.jfkastro.pulsarsapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Pulsar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String jName;

    private String raJ;
    private String decJ;

    private Double pmRa;
    private Double pmDec;

    private Double px;
    private Double posEpoch;

    private Double eLong;
    private Double eLat;

    private Double pmElong;
    private Double pmElat;

    private Double gl;
    private Double gb;

    private Double raJd;
    private Double decJd;

    private String bName;

    private Double pmL;
    private Double pmB;

    private String survey;
    private String type;
}
