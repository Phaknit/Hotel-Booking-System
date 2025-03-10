package PDIproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class UserLogin extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private static final String DB_URL = "jdbc:sqlite:RegistrationInfo.db";

    public UserLogin() {
        setTitle("User Login");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // === TOP PANEL (Title) ===
        JLabel titleLabel = new JLabel("Login to Your Account", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 22));
        titleLabel.setForeground(new Color(50, 50, 150));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        // === CENTER PANEL (Login Form) ===
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);

        formPanel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        usernameField = new JTextField(15);
        formPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        formPanel.add(passwordField, gbc);

        add(formPanel, BorderLayout.CENTER);

        // === BOTTOM PANEL (Buttons) ===
        JPanel buttonPanel = new JPanel();
        JButton loginButton = new JButton("Login");
        JButton backButton = new JButton("Back");

        // Button Styling
        loginButton.setBackground(new Color(34, 167, 240));
        loginButton.setForeground(Color.WHITE);
        backButton.setBackground(new Color(242, 38, 19));
        backButton.setForeground(Color.WHITE);
        
        loginButton.setFocusPainted(false);
        backButton.setFocusPainted(false);
        
        buttonPanel.add(loginButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // === Button Actions ===
        loginButton.addActionListener(e -> authenticateUser());
        backButton.addActionListener(e -> {
            new WelcomeHomePageGUI().setVisible(true);
            dispose();
        });

        setVisible(true);
    }

    private void authenticateUser() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (validateUsername(username) && validatePassword(password)) {
            if (checkCredentials(username, password)) {
                JOptionPane.showMessageDialog(this, "✅ Login successful! Welcome, " + username);
                logUserActivity("Login SUCCESS - Username: " + username + " - " + LocalDateTime.now());

                new GUI().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Invalid username or password!", "Login Failed", JOptionPane.ERROR_MESSAGE);
                logUserActivity("Login FAILED - Username: " + username + " - " + LocalDateTime.now());
            }
        } else {
            JOptionPane.showMessageDialog(this, "⚠️ Username and password must be valid!", "Invalid Input", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void logUserActivity(String activity) {
        try (FileWriter writer = new FileWriter("user_activity_log.txt", true)) {
            writer.write(activity + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean checkCredentials(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean validateUsername(String username) {
        return username != null && !username.trim().isEmpty();
    }

    public static boolean validatePassword(String password) {
        return password != null && password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UserLogin().setVisible(true));
    }
}
