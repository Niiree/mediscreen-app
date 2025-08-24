package com.mediscreen.clientui.beans;

public class DiabetesRiskResponse {
    public enum RiskLevel { NONE, BORDERLINE, IN_DANGER, EARLY_ONSET, UNKNOWN }
    private RiskLevel riskLevel;
    private String message;

    public RiskLevel getRiskLevel() { return riskLevel; }
    public void setRiskLevel(RiskLevel riskLevel) { this.riskLevel = riskLevel; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
