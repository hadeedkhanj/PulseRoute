package model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class EmergencyRequest implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private String requestId;
    private String patientName;
    private String hospitalName;
    private BloodType requiredBloodType;
    private int unitsRequired;
    private LocalDateTime requestTime;
    private int timeLimitMinutes;

    public EmergencyRequest(String requestId, String patientName, String hospitalName, 
        BloodType requiredBloodType, int unitsRequired, int timeLimitMinutes) {

            this.requestId = requestId;
            this.patientName = patientName;
            this.hospitalName = hospitalName;
            this.requiredBloodType = requiredBloodType;
            this.unitsRequired = unitsRequired;
            this.timeLimitMinutes = timeLimitMinutes;
            this.requestTime = LocalDateTime.now();
    }

    public String getRequestId() {
        return requestId;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public BloodType getRequiredBloodType() {
        return requiredBloodType;
    }

    public int getUnitsRequired() {
        return unitsRequired;
    }

    public LocalDateTime getRequestTime() {
        return requestTime;
    }

    public int getTimeLimitMinutes() {
        return timeLimitMinutes;
    }

    @Override
    public String toString() {
        return "Emergency Request [" + requestId + "] - Patient: " + patientName +
               " | Hospital: " + hospitalName + "\nNeeds: " + unitsRequired + " unit(s) of " +
               requiredBloodType + " | Time Limit: " + timeLimitMinutes + " mins";
    }

    
}
