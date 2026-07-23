package gui;

import model.BloodPacket;
import model.Donor;
import model.EmergencyRequest;
import storage.DataManager;
import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

//this is the main application window which sets the look and feel, initializes the 3 tabs, loads and saves data
public class MainFrame extends JFrame {
    
    private static final String DONORS_FILE = "donors.dat";
    private static final String PACKETS_FILE = "packets.dat";
    private static final String REQUESTS_FILE = "requests.dat";

    private List<Donor> donorList;
    private List<BloodPacket> packetList;
    private List<EmergencyRequest> requestList;

    public MainFrame() {

        //setting native system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
    

        setTitle("PulseRoute - Emergency Resource Coordinator");
        setSize(950, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //loading data from files
        donorList = DataManager.loadData(DONORS_FILE);
        packetList = DataManager.loadData(PACKETS_FILE);
        requestList = DataManager.loadData(REQUESTS_FILE);

        // JTabbedPane will allow us to manage multiple panels/screen using tabs
        JTabbedPane tabbedPane = new JTabbedPane();

        EmergencyPanel emergencyPanel = new EmergencyPanel(requestList, donorList, packetList, REQUESTS_FILE);
        DonorPanel donorPanel = new DonorPanel(donorList, DONORS_FILE);
        PacketPanel packetPanel = new PacketPanel(packetList, PACKETS_FILE);

        tabbedPane.addTab("Emergency Dispatch & Matching", emergencyPanel);
        tabbedPane.addTab("Donor Directory", donorPanel);
        tabbedPane.addTab("Blood Inventory", packetPanel);

        // refresh emergency match options when switching tabs
        tabbedPane.addChangeListener(e -> {
            donorPanel.refreshTable();
            packetPanel.refreshTable();
            emergencyPanel.refreshRequestsTable();
        });

        add(tabbedPane);

        // saving data when user clicks on the 'x' button
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                DataManager.saveData(donorList, DONORS_FILE);
                DataManager.saveData(packetList, PACKETS_FILE);
                DataManager.saveData(requestList, REQUESTS_FILE);
            }
        });
    }    
}


