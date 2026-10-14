package ui;

import javax.swing.*;
import java.awt.*;

/**
 * StockPanel.java
 *
 * Starter Swing JPanel for monitoring and updating current blood stock inventory.
 *
 * Team Member responsible: Student 3 (User Interface)
 */
public class StockPanel extends JPanel {

    private JButton updateStockButton;
    private JButton refreshStockButton;
    private JTable stockTable;

    public StockPanel() {
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Top Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Blood Stock Inventory");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        headerPanel.add(titleLabel, BorderLayout.WEST);

        // Action Toolbar
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        updateStockButton = new JButton("Update Units");
        refreshStockButton = new JButton("Refresh Stock");

        actionPanel.add(updateStockButton);
        actionPanel.add(refreshStockButton);
        headerPanel.add(actionPanel, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // Table Placeholder
        String[] columns = {"Blood Group", "Units Available", "Status"};
        Object[][] dummyData = {
            {"A+", 15, "Normal"},
            {"A-", 6, "Low Stock"},
            {"B+", 12, "Normal"},
            {"B-", 4, "Critical"},
            {"AB+", 8, "Normal"},
            {"AB-", 3, "Critical"},
            {"O+", 22, "Adequate"},
            {"O-", 5, "Low Stock"}
        };
        stockTable = new JTable(dummyData, columns);
        JScrollPane scrollPane = new JScrollPane(stockTable);
        add(scrollPane, BorderLayout.CENTER);

        // TODO: Attach ActionListeners and connect to StockDAO to display live inventory
    }
}
