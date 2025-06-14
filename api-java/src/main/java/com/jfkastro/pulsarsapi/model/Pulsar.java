package com.jfkastro.pulsarsapi.model;

import java.util.Optional;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getjName() {
        return jName;
    }

    public void setjName(String jName) {
        this.jName = jName;
    }

    public String getRaJ() {
        return raJ;
    }

    public void setRaJ(String raJ) {
        this.raJ = raJ;
    }

    public String getDecJ() {
        return decJ;
    }

    public void setDecJ(String decJ) {
        this.decJ = decJ;
    }

    public Double getPmRa() {
        return pmRa;
    }

    public void setPmRa(Double pmRa) {
        this.pmRa = pmRa;
    }

    public Double getPmDec() {
        return pmDec;
    }

    public void setPmDec(Double pmDec) {
        this.pmDec = pmDec;
    }

    public Double getPx() {
        return px;
    }

    public void setPx(Double px) {
        this.px = px;
    }

    public Double getPosEpoch() {
        return posEpoch;
    }

    public void setPosEpoch(Double posEpoch) {
        this.posEpoch = posEpoch;
    }

    public Double geteLong() {
        return eLong;
    }

    public void seteLong(Double eLong) {
        this.eLong = eLong;
    }

    public Double geteLat() {
        return eLat;
    }

    public void seteLat(Double eLat) {
        this.eLat = eLat;
    }

    public Double getPmElong() {
        return pmElong;
    }

    public void setPmElong(Double pmElong) {
        this.pmElong = pmElong;
    }

    public Double getPmElat() {
        return pmElat;
    }

    public void setPmElat(Double pmElat) {
        this.pmElat = pmElat;
    }

    public Double getGl() {
        return gl;
    }

    public void setGl(Double gl) {
        this.gl = gl;
    }

    public Double getGb() {
        return gb;
    }

    public void setGb(Double gb) {
        this.gb = gb;
    }

    public Double getRaJd() {
        return raJd;
    }

    public void setRaJd(Double raJd) {
        this.raJd = raJd;
    }

    public Double getDecJd() {
        return decJd;
    }

    public void setDecJd(Double decJd) {
        this.decJd = decJd;
    }

    public String getbName() {
        return bName;
    }

    public void setbName(String bName) {
        this.bName = bName;
    }

    public Double getPmL() {
        return pmL;
    }

    public void setPmL(Double pmL) {
        this.pmL = pmL;
    }

    public Double getPmB() {
        return pmB;
    }

    public void setPmB(Double pmB) {
        this.pmB = pmB;
    }

    public String getSurvey() {
        return survey;
    }

    public void setSurvey(String survey) {
        this.survey = survey;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
