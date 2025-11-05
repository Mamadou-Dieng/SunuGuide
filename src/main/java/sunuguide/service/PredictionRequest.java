package sunuguide.service;

public class PredictionRequest {


    private String depart;
    private String arrivee;

    // Constructeur par défaut
    public PredictionRequest() {
    }

    // Constructeur avec paramètres
    public PredictionRequest(String depart, String arrivee) {
        this.depart = depart;
        this.arrivee = arrivee;
    }

    // Getters et Setters
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


}