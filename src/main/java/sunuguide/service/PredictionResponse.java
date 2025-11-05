package sunuguide.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class PredictionResponse {

    private String message;
    private Object corrections;

    @JsonProperty("results") // ceci assure que le JSON "results" mappe sur cette propriété
    private List<PredictionSegment> results;

    public PredictionResponse() {
    }

    public PredictionResponse(String message, Object corrections, List<PredictionSegment> results) {
        this.message = message;
        this.corrections = corrections;
        this.results = results;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getCorrections() {
        return corrections;
    }

    public void setCorrections(Object corrections) {
        this.corrections = corrections;
    }

    public List<PredictionSegment> getResults() {
        return results;
    }

    public void setResults(List<PredictionSegment> results) {
        this.results = results;
    }
}