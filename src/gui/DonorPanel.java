package gui;

import model.BloodType;
import model.Donor;
import storage.DataManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;


// it handles registering new donors and viewing existing ones in a JTable
// when a donor is registered, it updates the UI and automatically saves it
public class DonorPanel extends JPanel {
    
    private List<Donor> donorList;
    private String filePath;
    private JTextField nameField;
    private JTextField phoneField;
    private JComboBox<BloodType> bloodTypeCombo;
    private JTextField daysAgoField;
    private JTable donorTable;
    private DefaultTableModel tableModel;

    public DonorPanel(List<Donor> donorList, String filePath) {
        this.donorList = donorList;
        this.filePath = filePath;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // input form panel (top)
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 8, 8));
        formPanel.setBorder(BorderFactory.createTitledBorder("Register New Donor"));

        formPanel.add(new JLabel("Full Name:"));
        nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("Phone Number:"));
        phoneField = new JTextField();
        formPanel.add(phoneField);

        formPanel.add(new JLabel("Blood Type:"));
        bloodTypeCombo = new JComboBox<>(BloodType.values());
        formPanel.add(bloodTypeCombo);

        formPanel.add(new JLabel("Days Since Last Donation:"));
        daysAgoField = new JTextField("100");
        formPanel.add(daysAgoField);

        JButton addButton = new JButton("Register Donor");
        formPanel.add(addButton);

        add(formPanel, BorderLayout.NORTH);

        // 2. Donor Table Display (Center)
        String[] columnNames = {"ID", "Name", "Phone", "Blood Type", "Last Donation", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0);
        donorTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(donorTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Registered Donors Directory"));
        add(scrollPane, BorderLayout.CENTER);

        //loading data to fill table
        refreshTable();

        // button action listener
        addButton.addActionListener(e -> registerDonor());

        }

        private void registerDonor() {
            String name = nameField.getText().trim();
            String phone = phoneField.getText().trim();
            BloodType bloodType = (BloodType) bloodTypeCombo.getSelectedItem();
            String daysStr = daysAgoField.getText().trim();

            if (name.isEmpty() || phone.isEmpty() || daysStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
            }

            try {
                int daysAgo = Integer.parseInt(daysStr);
                String donorId = "D-" + (donorList.size() + 101);
                LocalDate lastDonation = LocalDate.now().minusDays(daysAgo);

                Donor newDonor = new Donor(donorId, name, phone, bloodType, lastDonation);
                donorList.add(newDonor);

                // auto saving the data
                DataManager.saveData(donorList, filePath);

                refreshTable();
                clearFields();

                JOptionPane.showMessageDialog(this, "Donor registered successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Days must be a valid number", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        public void refreshTable() {
            tableModel.setRowCount(0);
            for (Donor d : donorList) {
                String status = d.isEligibleToDonate() ? "Eligible" : "On Cooldown";
                Object[] row = {
                    d.getDonorId(),
                    d.getName(),
                    d.getPhone(),
                    d.getBloodType(),
                    d.getLastDonationDate(),
                    status
                };

                tableModel.addRow(row);
            }
        }    

        private void clearFields() {
            nameField.setText("");
            phoneField.setText("");
            daysAgoField.setText("100");
        }
}
