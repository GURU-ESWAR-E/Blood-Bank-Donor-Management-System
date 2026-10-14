package ui;

import javax.swing.*;
import java.awt.*;

/**
 * RequestPanel.java
 *
 * Starter Swing JPanel for viewing, approving, and managing blood requisitions.
 *
 * Team Member responsible: Student 3 (User Interface)
 */
public class RequestPanel extends JPanel {

    private JButton newRequestButton;
    private JButton approveButton;
    private JButton rejectButton;
    private JButton refreshButton;
    private JTable requestTable;

    public RequestPanel() {
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Top Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Blood Requests & Allocation");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        headerPanel.add(titleLabel, BorderLayout.WEST);

        // Action Toolbar
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        newRequestButton = new JButton("+ New Request");
        approveButton = new JButton("Approve");
        rejectButton = new JButton("Reject");
        refreshButton = new JButton("Refresh");

        actionPanel.add(newRequestButton);
        actionPanel.add(approveButton);
        actionPanel.add(rejectButton);
        actionPanel.add(refreshButton);
        headerPanel.add(actionPanel, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // Table Placeholder
        String[] columns = {"ID", "Patient Name", "Hospital", "Blood Group", "Units", "Status"};
        Object[][] dummyData = {
            {1, "Alice Walker", "Metro General", "O+", 2, "APPROVED"},
            {2, "David Miller", "St. Jude Clinic", "A+", 1, "PENDING"},
            {3, "Grace Lee", "Metro General", "B-", 3, "PENDING"}
        };
        requestTable = new JTable(dummyData, columns);
        JScrollPane scrollPane = new JScrollPane(requestTable);
        add(scrollPane, BorderLayout.CENTER);

        // TODO: Attach ActionListeners and connect to RequestDAO
    }
}
