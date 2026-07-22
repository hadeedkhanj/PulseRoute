package model;

import java.io.Serializable;
import java.time.LocalDate;


public class Donor implements Serializable {

    private static final long serialVersionUID = 1L;

    private String donorId;
    private String name;
    private String phone;
    private BloodType bloodType;
    private LocalDate lastDonationDate;

    public Donor(String donorId, String name, String phone, BloodType bloodType, LocalDate lastDonationDate) {
        this.donorId = donorId;
        this.name = name;
        this.phone = phone;
        this.bloodType = bloodType;
        this.lastDonationDate = lastDonationDate;
    }

    // a blood donor must have a 90 day cooldown period before donating again - the logic for that is underneath
    public boolean isEligibleToDonate() {
        if (lastDonationDate == null) {
            return true;
        }
        LocalDate eligibleDate = lastDonationDate.plusDays(90);
        return LocalDate.now().isAfter(eligibleDate) || LocalDate.now().isEqual(eligibleDate);
    }

    public String getDonorId() {
        return donorId;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public BloodType getBloodType() {
        return bloodType;
    }

    public LocalDate getLastDonationDate() {
        return lastDonationDate;
    }

    public void setLastDonationDate(LocalDate lastDonationDate) {
        this.lastDonationDate = lastDonationDate;
    }
    
    @Override
    public String toString() {
        String status = isEligibleToDonate() ? "Eligible" : "On Cooldown (90 days)";
        return "Donor ID: " + donorId + " | Name: " + name + " | Blood Type: " + bloodType +
               "\nContact: " + phone + " | Status: " + status;
    }
}
