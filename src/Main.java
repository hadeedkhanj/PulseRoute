import model.BloodType;
import model.BloodPacket;
import model.Donor;
import model.EmergencyRequest;
import java.time.LocalDate;
import service.MatchEngine;
import java.util.ArrayList;
import java.util.List;
import storage.DataManager;
import gui.MainFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;



// using this class for testing the classes
public class Main {

    /*file paths
    private static final String DONORS_FILE = "donors.dat";
    private static final String PACKETS_FILE = "packets.dat";
    private static final String REQUESTS_FILE = "requests.dat";
    */

    public static void main(String[] args) {

        /* 

        //THE FIRST PHASE OF TESTING!!!!

        //(should be on cooldown)
        Donor donor1 = new Donor("FA25-BCS-116", "Hadeed Khan", "03001234567", BloodType.O_NEGATIVE, LocalDate.now().minusDays(30));
        
        //(should be eligible)
        Donor donor2 = new Donor("FA25-BCS-142", "Hassan Murtaza", "0311430000", BloodType.A_POSITIVE, LocalDate.now().minusDays(100));

        System.out.println(donor1);
        System.out.println(donor2);
        System.out.println();


        BloodPacket packet = new BloodPacket("BP-999", "Blood Bank Shelf A", BloodType.O_NEGATIVE, LocalDate.now().plusDays(20), 450.0);
        System.out.println(packet.getDetails());
        System.out.println();


        EmergencyRequest request = new EmergencyRequest("FA25-BCS-147", "Zaheer Khan", "AMC", BloodType.O_NEGATIVE, 2, 45);
        System.out.println(request);

        

        //THE SECOND PHASE OF TESTING!!!!

        System.out.println();
        System.out.println();
        System.out.println();

        // creating an EmergencyRequest (example: the patient needs B+ blood)
        EmergencyRequest request2 = new EmergencyRequest(
                "REQ-111", 
                "Syed Hamza", 
                "Ayub Teaching Hospital", 
                BloodType.B_POSITIVE, 
                2, 
                30
        );

        System.out.println("CRITICAL REQUEST LOGGED:");
        System.out.println(request2);
        System.out.println("--------------------------------------------------\n");

        // creating a list of donors:
        List<Donor> donorList = new ArrayList<>();
        
        // B+ and eligible
        donorList.add(new Donor("D-101", "Waqas Jadoon", "03001111111", BloodType.B_POSITIVE, LocalDate.now().minusDays(100)));
        
        //O- and eligible
        donorList.add(new Donor("D-102", "Dr. Mazhar", "03002222222", BloodType.O_NEGATIVE, LocalDate.now().minusDays(120)));
        
        // B+ but on 90day cooldown
        donorList.add(new Donor("D3", "Shahmeer Jadoon", "03003333333", BloodType.B_POSITIVE, LocalDate.now().minusDays(15)));
        
        //A+ incompatible blood type
        donorList.add(new Donor("D4", "Haad Afzal", "03004444444", BloodType.A_POSITIVE, LocalDate.now().minusDays(100)));

        // creating a list of blood packets
        List<BloodPacket> packetList = new ArrayList<>();
        
        //  B+ unexpired
        packetList.add(new BloodPacket("BP-1", "Main Lab Storage", BloodType.B_POSITIVE, LocalDate.now().plusDays(15), 500.0));
        
        // B+ and expired!!
        packetList.add(new BloodPacket("BP-2", "Emergency Room Ward", BloodType.B_POSITIVE, LocalDate.now().minusDays(2), 300.0));

        // running the match engine
        List<Donor> matchedDonors = MatchEngine.findMatchingDonors(request2, donorList);
        List<BloodPacket> matchedPackets = MatchEngine.findMatchingPackets(request2, packetList);

        //printing them out (how many found and the list of them including their details)
        System.out.println("MATCHED ELIGIBLE DONORS (" + matchedDonors.size() + " found):");
        for (Donor d : matchedDonors) {
            System.out.println("  -> " + d.getName() + " (" + d.getBloodType() + ") | Contact: " + d.getPhone());
        }

        System.out.println("\nMATCHED AVAILABLE BLOOD PACKETS (" + matchedPackets.size() + " found):");
        for (BloodPacket p : matchedPackets) {
            System.out.println("  -> " + p.getDetails());
        }


        // THE THIRD PHASE OF TESTING !!!!

        System.out.println();
        System.out.println();
        System.out.println();

        // creating records in memory

        List<Donor> originalDonors = new ArrayList<>();
        originalDonors.add(new Donor("D-101", "Waqas Jadoon", "03001111111", BloodType.B_POSITIVE, LocalDate.now().minusDays(100)));
        originalDonors.add(new Donor("D-102", "Dr. Mazhar", "03002222222", BloodType.O_NEGATIVE, LocalDate.now().minusDays(120)));

        List<BloodPacket> originalPackets = new ArrayList<>();
        originalPackets.add(new BloodPacket("BP-501", "Main Storage", BloodType.B_POSITIVE, LocalDate.now().plusDays(15), 450.0));

        List<EmergencyRequest> originalRequests = new ArrayList<>();
        originalRequests.add(new EmergencyRequest("REQ-901", "Syed Hamza", "Ayub Teaching Hospital", BloodType.B_POSITIVE, 2, 30));

        //saving these records to binary files
        DataManager.saveData(originalDonors, DONORS_FILE);
        DataManager.saveData(originalPackets, PACKETS_FILE);
        DataManager.saveData(originalRequests, REQUESTS_FILE);

        //simulating a system crash/reboot 
        System.out.println("\n--------------------------------------------------");
        System.out.println("SIMULATING SYSTEM CRASH / REBOOT (Wiping memory)...");
        System.out.println("--------------------------------------------------\n");

        originalDonors = null;
        originalPackets = null;
        originalRequests = null;

        //reading data back from the files
        List<Donor> recoveredDonors = DataManager.loadData(DONORS_FILE);
        List<BloodPacket> recoveredPackets = DataManager.loadData(PACKETS_FILE);
        List<EmergencyRequest> recoveredRequests = DataManager.loadData(REQUESTS_FILE);

        //verifying the recovered data now
        System.out.println("\n[RECOVERED DONORS]:");
        for (Donor d : recoveredDonors) {
            System.out.println(d.getName() + " (" + d.getBloodType() + ") | Contact: " + d.getPhone());
        }

        System.out.println("\n[RECOVERED BLOOD PACKETS]:");
        for (BloodPacket p : recoveredPackets) {
            System.out.println( p.getDetails());
        }

        System.out.println("\n[RECOVERED EMERGENCY REQUESTS]:");
        for (EmergencyRequest r : recoveredRequests) {
            System.out.println(r);
        } 

        


        // TESTING THE GUI !!!!

        System.out.println();
        System.out.println();
        System.out.println();

        */

        // using java's built-in modern Nimbus theme
        
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Nimbus theme not available, using default.");
        }

        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });

    }
}
