package service;

import model.BloodPacket;
import model.Donor;
import model.EmergencyRequest;

import java.util.ArrayList;
import java.util.List;

/*
this is the core processor of our program, it will receive an EmergencyRequest
and the list of available resources and then filter them by:
    biological check (is the blood type compatible)
    checking donor cooldown and the expiration date of the blood packet
*/

public class MatchEngine {
    
    //returns all eligible donors for an emergency request
    public static List<Donor> findMatchingDonors(EmergencyRequest request, List<Donor> allDonors) {
        List<Donor> matchedDonors = new ArrayList<>();

        for (Donor d : allDonors) {
            boolean isBiologicallyCompatible = BloodCompatibility.canDonate(
                    d.getBloodType(), request.getRequiredBloodType()
            );

            boolean isEligible = d.isEligibleToDonate();

            //if the donor is both biologically compatible and elible to donate (no cooldown), add it to the arraylist
            if (isBiologicallyCompatible && isEligible) {
                matchedDonors.add(d);
            }
        }

        return matchedDonors;
    }

    public static List<BloodPacket> findMatchingPackets(EmergencyRequest request, List<BloodPacket> allPackets) {
        List<BloodPacket> matchedPackets = new ArrayList<>();

        for (BloodPacket p : allPackets) {
            boolean isBiologicallyCompatible = BloodCompatibility.canDonate(
                    p.getBloodType(), request.getRequiredBloodType()
            );

            // checking the expiration date here
            boolean isFresh = !p.isExpired();

            if (isBiologicallyCompatible && isFresh) {
                matchedPackets.add(p);
            }
        }

        return matchedPackets;
    }
    
}
