package service;
import model.BloodType;

public class BloodCompatibility {
    
    // checking if donorType can give blood to recipientType
    public static boolean canDonate(BloodType donorType, BloodType recipientType) {
        if (donorType == null || recipientType == null) {
            return false;
        }

        // if youre O- negative, you can donate blood to everyone
        if (donorType == BloodType.O_NEGATIVE) {
            return true;
        }

        // if youre AB+ positive, you can receive blood from everyone
        if (recipientType == BloodType.AB_POSITIVE) {
            return true;
        }

        //using switch statements for the blood donation/receiving rules
        switch (donorType) {
            case O_POSITIVE:
                return recipientType == BloodType.O_POSITIVE ||
                       recipientType == BloodType.A_POSITIVE ||
                       recipientType == BloodType.B_POSITIVE ||
                       recipientType == BloodType.AB_POSITIVE;

            case A_NEGATIVE:
                return recipientType == BloodType.A_NEGATIVE ||
                       recipientType == BloodType.A_POSITIVE ||
                       recipientType == BloodType.AB_NEGATIVE ||
                       recipientType == BloodType.AB_POSITIVE;

            case A_POSITIVE:
                return recipientType == BloodType.A_POSITIVE ||
                       recipientType == BloodType.AB_POSITIVE;

            case B_NEGATIVE:
                return recipientType == BloodType.B_NEGATIVE ||
                       recipientType == BloodType.B_POSITIVE ||
                       recipientType == BloodType.AB_NEGATIVE ||
                       recipientType == BloodType.AB_POSITIVE;

            case B_POSITIVE:
                return recipientType == BloodType.B_POSITIVE ||
                       recipientType == BloodType.AB_POSITIVE;

            case AB_NEGATIVE:
                return recipientType == BloodType.AB_NEGATIVE ||
                       recipientType == BloodType.AB_POSITIVE;

            default:
                return false;
        }
    }
}
