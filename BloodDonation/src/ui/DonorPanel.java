package ui;

import javax.swing.*;
import java.awt.*;

/**
 * DonorPanel.java
 *
 * Starter Swing JPanel for viewing, registering, and managing blood donors.
 * Can be embedded inside a main dashboard tab or split pane.
 *
 * Team Member responsible: Student 3 (User Interface)
 */
public class DonorPanel extends JPanel {

    private JButton addDonorButton;
    private JButton refreshButton;
    private JButton deleteButton;
    private JTable donorTable;

    public DonorPanel() {
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Top Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Donor Management");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        headerPanel.add(titleLabel, BorderLayout.WEST);

        // Action Toolbar
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        addDonorButton = new JButton("+ Add Donor");
        refreshButton = new JButton("Refresh");
        deleteButton = new JButton("Delete");

        actionPanel.add(addDonorButton);
        actionPanel.add(refreshButton);
        actionPanel.add(deleteButton);
        headerPanel.add(actionPanel, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // Center Content Placeholder (Table)
        String[] columns = {"ID", "Full Name", "Phone", "Blood Group", "Last Donation Date"};
        Object[][] dummyData = {
            {1, "Sample Donor 1", "555-0101", "O+", "2024-01-15"},
            {2, "Sample Donor 2", "555-0102", "A+", "2024-02-10"}
        };
        donorTable = new JTable(dummyData, columns);
        JScrollPane scrollPane = new JScrollPane(donorTable);
        add(scrollPane, BorderLayout.CENTER);

        // TODO: Attach ActionListeners to buttons and load live records from DonorDAO
    }
}
