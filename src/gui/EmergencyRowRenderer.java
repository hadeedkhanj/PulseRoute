package gui;

import model.EmergencyRequest;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.List;

public class EmergencyRowRenderer extends DefaultTableCellRenderer {
    
    private List<EmergencyRequest> requestList;

    public EmergencyRowRenderer(List<EmergencyRequest> requestList) {
        this.requestList = requestList;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
        boolean isSelected, boolean hasFocus, int row, int column) {

        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (row < requestList.size()) {
            EmergencyRequest request = requestList.get(row);
            int minutesRemaining = request.getMinutesRemaining();

            if (!isSelected) {
                if (minutesRemaining <= 15) {

                    // Critical or Overdue will give a Red Alert
                    c.setBackground(new Color(255, 210, 210));
                    c.setForeground(new Color(160, 0, 0));
                    setFont(getFont().deriveFont(Font.BOLD));
                } else if (minutesRemaining <= 30) {

                    // Warning will be given in Yellow
                    c.setBackground(new Color(255, 243, 205));
                    c.setForeground(new Color(133, 100, 4));
                    setFont(getFont().deriveFont(Font.BOLD));
                } else {

                    // Normal is just the Standard Row Color
                    c.setBackground(Color.WHITE);
                    c.setForeground(Color.BLACK);
                    setFont(getFont().deriveFont(Font.PLAIN));
                }
            } else {

                // it will preserve clear selection color when clicked
                c.setBackground(table.getSelectionBackground());
                c.setForeground(table.getSelectionForeground());
            }
        }

        return c;
    }
}
