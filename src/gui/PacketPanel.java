package gui;

import model.BloodPacket;
import model.BloodType;
import storage.DataManager;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;


public class PacketPanel extends JPanel {

    private List<BloodPacket> packetList;
    private String filePath;
    private JTextField locationField;
    private JComboBox<BloodType> bloodTypeCombo;
    private JTextField expiryDaysField;
    private JTextField volumeField;
    private JTable packetTable;
    private DefaultTableModel tableModel;

    public PacketPanel(List<BloodPacket> packetList, String filePath) {
        this.packetList = packetList;
        this.filePath = filePath;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // input form panel (top)
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 8, 8));
        formPanel.setBorder(BorderFactory.createTitledBorder("Add Blood Packet to Storage"));

        formPanel.add(new JLabel("Storage Location:"));
        locationField = new JTextField("Main Storage Shelf A");
        formPanel.add(locationField);

        formPanel.add(new JLabel("Blood Type:"));
        bloodTypeCombo = new JComboBox<>(BloodType.values());
        formPanel.add(bloodTypeCombo);

        formPanel.add(new JLabel("Days Until Expiry:"));
        expiryDaysField = new JTextField("30");
        formPanel.add(expiryDaysField);

        formPanel.add(new JLabel("Volume (ml):"));
        volumeField = new JTextField("450");
        formPanel.add(volumeField);

        JButton addButton = new JButton("Add Blood Packet");
        formPanel.add(addButton);

        add(formPanel, BorderLayout.NORTH);

        // displaying the inventory in the center
        String[] columnNames = {"Packet ID", "Location", "Blood Type", "Expiry Date", "Volume (ml)", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0);
        packetTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(packetTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Active Blood Inventory"));
        add(scrollPane, BorderLayout.CENTER);

        refreshTable();

        addButton.addActionListener(e -> addPacket());
    }

    private void addPacket() {

        String location = locationField.getText().trim();
        BloodType bloodType = (BloodType) bloodTypeCombo.getSelectedItem();
        String expiryStr = expiryDaysField.getText().trim();
        String volumeStr = volumeField.getText().trim();

        if (location.isEmpty() || expiryStr.isEmpty() || volumeStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int expiryDays = Integer.parseInt(expiryStr);
            double volume = Double.parseDouble(volumeStr);

            //501 because we are starting from 500 and since 1 is already created, it needs to start from 502
            String packetId = "BP-" + (packetList.size() + 501);
            LocalDate expiryDate = LocalDate.now().plusDays(expiryDays);

            BloodPacket packet = new BloodPacket(packetId, location, bloodType, expiryDate, volume);
            packetList.add(packet);

            DataManager.saveData(packetList, filePath);

            refreshTable();
            clearFields();

            JOptionPane.showMessageDialog(this, "Blood packet added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Expiry days and volume must be valid numbers", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void refreshTable() {
        tableModel.setRowCount(0);
        for (BloodPacket p : packetList) {
            String status = p.isExpired() ? "EXPIRED" : "Available";
            Object[] row = {
                p.getResourceId(),
                p.getLocation(),
                p.getBloodType(),
                p.getExpiryDate(),
                p.getVolumeInMl(),
                status
            };

            tableModel.addRow(row);
        }
    }

    private void clearFields() {
        locationField.setText("Main Storage Shelf A");
        expiryDaysField.setText("30");
        volumeField.setText("450");
    }
}
