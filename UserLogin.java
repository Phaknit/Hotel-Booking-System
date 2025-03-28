package PDIproject;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

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

        // Create the login_activity table if it doesn't exist
        createTableIfNotExists();

        // === TITLE PANEL ===
        JLabel titleLabel = new JLabel("Login to Your Account", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 22));
        titleLabel.setForeground(new Color(50, 50, 150));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        // === FORM PANEL ===
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

        // === BUTTON PANEL ===
        JPanel buttonPanel = new JPanel();
        JButton loginButton = new JButton("Login");
        JButton backButton = new JButton("Back");

        // Styling
        loginButton.setBackground(new Color(34, 167, 240));
        loginButton.setForeground(Color.WHITE);
        backButton.setBackground(new Color(242, 38, 19));
        backButton.setForeground(Color.WHITE);
        
        loginButton.setFocusPainted(false);
        backButton.setFocusPainted(false);
        
        buttonPanel.add(loginButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // === BUTTON ACTIONS ===
        loginButton.addActionListener(e -> authenticateUser());
        backButton.addActionListener(e -> {
            new HotelWelcomePage().setVisible(true);
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
                logUserActivity(username, "Login SUCCESS");

                new HotelMainPage();
                dispose(); // Close login window
            } else {
                JOptionPane.showMessageDialog(this, "❌ Invalid username or password!", "Login Failed", JOptionPane.ERROR_MESSAGE);
                logUserActivity(username, "Login FAILED");
            }
        } else {
            JOptionPane.showMessageDialog(this, "⚠️ Username and password must be valid!", "Invalid Input", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void logUserActivity(String username, String activity) {
        String query = "INSERT INTO login_activity (username, activity) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, activity);
            stmt.executeUpdate();
        } catch (SQLException e) {
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

    private void createTableIfNotExists() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS login_activity (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT, " +
                "activity TEXT NOT NULL, " +
                "timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ");";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
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
