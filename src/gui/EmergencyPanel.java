package gui;

import model.BloodPacket;
import model.BloodType;
import model.Donor;
import model.EmergencyRequest;
import service.MatchEngine;
import storage.DataManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EmergencyPanel extends JPanel {

    private List<EmergencyRequest> requestList;
    private List<Donor> donorList;
    private List<BloodPacket> packetList;
    private String filePath;

    private JTextField patientField;
    private JTextField hospitalField;
    private JComboBox<BloodType> bloodTypeCombo;
    private JTextField unitsField;
    private JTextField timeLimitField;

    //requests and matches
    private JTable requestsTable;
    private DefaultTableModel requestsModel;
    private JTable matchedDonorsTable;
    private DefaultTableModel matchedDonorsModel;
    private JTable matchedPacketsTable;
    private DefaultTableModel matchedPacketsModel;

    public EmergencyPanel(List<EmergencyRequest> requestList, List<Donor> donorList, 
            List<BloodPacket> packetList, String filePath) {

        this.requestList = requestList;
        this.donorList = donorList;
        this.packetList = packetList;
        this.filePath = filePath;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 6, 6));
        formPanel.setBorder(BorderFactory.createTitledBorder("Log Emergency Case"));

        formPanel.add(new JLabel("Patient Name:"));
        patientField = new JTextField();
        formPanel.add(patientField);

        formPanel.add(new JLabel("Hospital Name:"));
        hospitalField = new JTextField("Ayub Teaching Hospital");
        formPanel.add(hospitalField);

        formPanel.add(new JLabel("Required Blood Type:"));
        bloodTypeCombo = new JComboBox<>(BloodType.values());
        formPanel.add(bloodTypeCombo);

        formPanel.add(new JLabel("Units Required:"));
        unitsField = new JTextField("2");
        formPanel.add(unitsField);

        formPanel.add(new JLabel("Time Limit (Minutes):"));
        timeLimitField = new JTextField("45");
        formPanel.add(timeLimitField);

        JButton logButton = new JButton("Log Emergency Request");
        formPanel.add(logButton);

        add(formPanel, BorderLayout.NORTH);

        // tables

        String[] reqCols = {"Req ID", "Patient", "Hospital", "Blood Type", "Units", "Time Limit"};
        requestsModel = new DefaultTableModel(reqCols, 0);
        requestsTable = new JTable(requestsModel);

        JScrollPane reqScroll = new JScrollPane(requestsTable);
        reqScroll.setBorder(BorderFactory.createTitledBorder("Active Emergency Requests (Click a row to match)"));

        // matching tables

        String[] donorCols = {"Donor ID", "Name", "Phone", "Blood Type"};
        matchedDonorsModel = new DefaultTableModel(donorCols, 0);
        matchedDonorsTable = new JTable(matchedDonorsModel);
        JScrollPane donorScroll = new JScrollPane(matchedDonorsTable);
        donorScroll.setBorder(BorderFactory.createTitledBorder("Matched Compatible Donors"));

        String[] packetCols = {"Packet ID", "Location", "Blood Type", "Volume"};
        matchedPacketsModel = new DefaultTableModel(packetCols, 0);
        matchedPacketsTable = new JTable(matchedPacketsModel);
        JScrollPane packetScroll = new JScrollPane(matchedPacketsTable);
        packetScroll.setBorder(BorderFactory.createTitledBorder("Matched Inventory Packets"));

        // a single matched panel for donor and packets
        JPanel matchPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        matchPanel.add(donorScroll);
        matchPanel.add(packetScroll);

        // creating a split panel for requests and matched panel        
        JSplitPane centerSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, reqScroll, matchPanel);
        centerSplit.setDividerLocation(180);
        add(centerSplit, BorderLayout.CENTER);

        refreshRequestsTable();
        logButton.addActionListener(e -> logRequest());

        // row selection listener, it runs match engine when a request is clicked
        requestsTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = requestsTable.getSelectedRow();
                if (selectedRow != -1) {
                    EmergencyRequest selectedReq = requestList.get(selectedRow);
                    runMatchEngine(selectedReq);
                }
            }
        });
    }

    private void logRequest() {
        String patient = patientField.getText().trim();
        String hospital = hospitalField.getText().trim();
        BloodType bloodType = (BloodType) bloodTypeCombo.getSelectedItem();
        String unitsStr = unitsField.getText().trim();
        String timeStr = timeLimitField.getText().trim();

        if (patient.isEmpty() || hospital.isEmpty() || unitsStr.isEmpty() || timeStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int units = Integer.parseInt(unitsStr);
            int timeLimit = Integer.parseInt(timeStr);
            String reqId = "REQ-" + (requestList.size() + 901);

            EmergencyRequest newReq = new EmergencyRequest(reqId, patient, hospital, bloodType, units, timeLimit);
            requestList.add(newReq);
            DataManager.saveData(requestList, filePath);

            refreshRequestsTable();
            clearFields();

            JOptionPane.showMessageDialog(this, "Emergency request logged successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Units and time limit must be in integers", "Input Error", JOptionPane.ERROR_MESSAGE);
        }

    }


    private void runMatchEngine(EmergencyRequest request) {
        List<Donor> matchedDonors = MatchEngine.findMatchingDonors(request, donorList);
        List<BloodPacket> matchedPackets = MatchEngine.findMatchingPackets(request, packetList);

        matchedDonorsModel.setRowCount(0);
        for (Donor d : matchedDonors) {
            matchedDonorsModel.addRow(new Object[] {
                d.getDonorId(), d.getName(), d.getPhone(), d.getBloodType()
            });
        }

        matchedPacketsModel.setRowCount(0);
        for (BloodPacket p : matchedPackets) {
            matchedPacketsModel.addRow(new Object[] {
                p.getResourceId(), p.getLocation(), p.getBloodType(), p.getVolumeInMl() + "ml"
            });
        }
    }

    public void refreshRequestsTable() {
        requestsModel.setRowCount(0);
        for (EmergencyRequest r : requestList) {
            requestsModel.addRow(new Object[] {
                r.getRequestId(),
                r.getPatientName(),
                r.getHospitalName(),
                r.getRequiredBloodType(),
                r.getUnitsRequired(),
                r.getTimeLimitMinutes() + " mins"
            });
        }
    }

    private void clearFields() {
        patientField.setText("");
        unitsField.setText("2");
        timeLimitField.setText("45");
    }
}
