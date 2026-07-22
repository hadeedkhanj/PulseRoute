import model.BloodType;
import model.BloodPacket;
import model.Donor;
import model.EmergencyRequest;
import java.time.LocalDate;


//using this class for testing the data model classes
public class Main {
    public static void main(String[] args) {

        //(should be on cooldown)
        Donor donor1 = new Donor("D-101", "Ahmad Ali", "03001234567", BloodType.O_NEGATIVE, LocalDate.now().minusDays(30));
        
        //(should be eligible)
        Donor donor2 = new Donor("D-102", "Usman Khan", "03119876543", BloodType.A_POSITIVE, LocalDate.now().minusDays(100));

        System.out.println(donor1);
        System.out.println(donor2);
        System.out.println();


        BloodPacket packet = new BloodPacket("BP-501", "Blood Bank Shelf A", BloodType.O_NEGATIVE, LocalDate.now().plusDays(20), 450.0);
        System.out.println(packet.getDetails());
        System.out.println();


        EmergencyRequest request = new EmergencyRequest("REQ-901", "Hamza", "City Hospital", BloodType.O_NEGATIVE, 2, 45);
        System.out.println(request);
    }
}
