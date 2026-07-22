package model;
import java.time.LocalDate;


public class BloodPacket extends CriticalResource {
    
    private BloodType bloodType;
    private LocalDate expiryDate;
    private double volumeInMl;

    public BloodPacket(String resourceId, String location, BloodType bloodType, LocalDate expiryDate, double volumeInMl) {
        super(resourceId, location);
        this.bloodType = bloodType;
        this.expiryDate = expiryDate;
        this.volumeInMl = volumeInMl;
    }

    public BloodType getBloodType() {
        return bloodType;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public double getVolumeInMl() {
        return volumeInMl;
    }

    public boolean isExpired() {
        return LocalDate.now().isAfter(expiryDate);
    }

    @Override
    public String getDetails() {
        return "Blood Packet: [" + getResourceId() + "] - Type: " + bloodType +
               "\nVol: " + volumeInMl + "ml | Exp: " + expiryDate +
               " | Location: " + getLocation();
    }
}
