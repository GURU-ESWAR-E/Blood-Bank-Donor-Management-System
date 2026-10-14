package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * LoginFrame.java
 *
 * Starter Swing GUI window for user authentication.
 *
 * Team Member responsible: Student 3 (User Interface)
 *
 * For beginners:
 * - A JFrame is the top-level desktop window with title bar, minimize, and close buttons.
 * - Components (labels, text fields, buttons) are added to containers using layout managers.
 */
public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton cancelButton;
    private JLabel statusLabel;

    public LoginFrame() {
        initUI();
    }

    private void initUI() {
        setTitle("Blood Bank System - Login");
        setSize(420, 260);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center window on screen
        setResizable(false);

        // Main container panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        // Header Title
        JLabel titleLabel = new JLabel("Blood Bank Management", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Center Form (GridBagLayout for clean form alignment)
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Username Label & Field
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        formPanel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        usernameField = new JTextField(15);
        formPanel.add(usernameField, gbc);

        // Password Label & Field
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        formPanel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        passwordField = new JPasswordField(15);
        formPanel.add(passwordField, gbc);

        // Status Label (displays error or success messages)
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        statusLabel = new JLabel(" ", SwingConstants.CENTER);
        statusLabel.setForeground(Color.RED);
        formPanel.add(statusLabel, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Buttons Panel at the bottom
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        loginButton = new JButton("Login");
        cancelButton = new JButton("Cancel");

        buttonPanel.add(cancelButton);
        buttonPanel.add(loginButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // --- Event Listeners (Starter Stubs) ---

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usernameField.setText("");
                passwordField.setText("");
                statusLabel.setText("");
            }
        });

        // Set default enter-key button
        getRootPane().setDefaultButton(loginButton);

        add(mainPanel);
    }

    /**
     * Starter stub for login authentication.
     */
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            statusLabel.setText("Please enter username and password.");
            return;
        }

        // TODO: Validate credentials against the 'users' table using a UserDAO or JDBC query
        // Example starter feedback:
        statusLabel.setForeground(new Color(0, 128, 0));
        statusLabel.setText("Login clicked (Feature to be implemented).");
    }
}
