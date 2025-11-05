package sunuguide.service;

import com.fasterxml.jackson.annotation.JsonProperty;

// Classe PredictionSegment

import jakarta.persistence.Embeddable;

@Embeddable
public class PredictionSegment {

    @JsonProperty("transport")// <--- DECOMMENTER ET VÉRIFIER L'UNDERSCORE

    private String typeTransport; // <-- CHANGEMENT ICI
    private String depart;
    private String arrivee;

    @JsonProperty("distance_km")
    private double distanceKm;

    @JsonProperty("duree_min")
    private double dureeMin;

    @JsonProperty("prix_estime")
    private double prixEstime;

    public PredictionSegment() {
    }

    public String getTypeTransport() {
        return typeTransport;
    }

    public void setTypeTransport(String transport) {
        this.typeTransport = transport;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getArrivee() {
        return arrivee;
    }

    public void setArrivee(String arrivee) {
        this.arrivee = arrivee;
    }

    public double getDistanceKm() {
        return distanceKm;
    }

    public void setDistanceKm(double distanceKm) {
        this.distanceKm = distanceKm;
    }

    public double getDureeMin() {
        return dureeMin;
    }

    public void setDureeMin(double dureeMin) {
        this.dureeMin = dureeMin;
    }

    public double getPrixEstime() {
        return prixEstime;
    }

    public void setPrixEstime(double prixEstime) {
        this.prixEstime = prixEstime;
    }
}