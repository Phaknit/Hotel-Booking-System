package PDIproject;

import java.util.regex.Pattern;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class UserRegistration extends JFrame {
    private JTextField fnameField;
    private JTextField lnameField;
    private JTextField emailField;
    private JTextField phoneNumField;
    private JTextField dateOfBirthField;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public UserRegistration() {
           	setTitle("Sign Up");
            setSize(500, 400);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            setLayout(new BorderLayout());

            // === TOP PANEL (Title) ===
            JLabel titleLabel = new JLabel("Sign up Your Account", SwingConstants.CENTER);
            titleLabel.setFont(new Font("Serif", Font.BOLD, 22));
            titleLabel.setForeground(new Color(50, 50, 150));
            titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
            add(titleLabel, BorderLayout.NORTH);

            // === CENTER PANEL (Form) ===
            JPanel formPanel = new JPanel(new GridBagLayout());
            formPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.insets = new Insets(5, 5, 5, 5);

            // Add form fields
            gbc.gridx = 0;
            gbc.gridy = 0;
            formPanel.add(new JLabel("First name:"), gbc);
            gbc.gridx = 1;
            fnameField = new JTextField(15);
            formPanel.add(fnameField, gbc);

            gbc.gridx = 0;
            gbc.gridy++;
            formPanel.add(new JLabel("Last name:"), gbc);
            gbc.gridx = 1;
            lnameField = new JTextField(15);
            formPanel.add(lnameField, gbc);

            gbc.gridx = 0;
            gbc.gridy++;
            formPanel.add(new JLabel("Email:"), gbc);
            gbc.gridx = 1;
            emailField = new JTextField(15);
            formPanel.add(emailField, gbc);

            gbc.gridx = 0;
            gbc.gridy++;
            formPanel.add(new JLabel("Phone number:"), gbc);
            gbc.gridx = 1;
            phoneNumField = new JTextField(15);
            formPanel.add(phoneNumField, gbc);

            gbc.gridx = 0;
            gbc.gridy++;
            formPanel.add(new JLabel("Date of Birth (yyyy-MM-dd):"), gbc);
            gbc.gridx = 1;
            dateOfBirthField = new JTextField(15);
            formPanel.add(dateOfBirthField, gbc);

            gbc.gridx = 0;
            gbc.gridy++;
            formPanel.add(new JLabel("Username:"), gbc);
            gbc.gridx = 1;
            usernameField = new JTextField(15);
            formPanel.add(usernameField, gbc);

            gbc.gridx = 0;
            gbc.gridy++;
            formPanel.add(new JLabel("Password:"), gbc);
            gbc.gridx = 1;
            passwordField = new JPasswordField(15);
            formPanel.add(passwordField, gbc);

            add(formPanel, BorderLayout.CENTER);

            // === BOTTOM PANEL (Buttons) ===
            JPanel buttonPanel = new JPanel();
            JButton registerButton = new JButton("Register");
            JButton backButton = new JButton("Back");

            // Button Styling
            registerButton.setBackground(new Color(34, 167, 240));
            registerButton.setForeground(Color.WHITE);
            backButton.setBackground(new Color(242, 38, 19));
            backButton.setForeground(Color.WHITE);
            registerButton.setFocusPainted(false);
            backButton.setFocusPainted(false);

            buttonPanel.add(registerButton);
            buttonPanel.add(backButton);
            add(buttonPanel, BorderLayout.SOUTH);

            // Register button action
            registerButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (userInput()) {
                        if (saveToDatabase()) {
                            logUserActivity("Sign-Up SUCCESS - Username: " + usernameField.getText().trim() + " - " + LocalDateTime.now());
                            new UserLogin().setVisible(true);
                            dispose();
                        } else {
                            logUserActivity("Sign-Up FAILED - Username: " + usernameField.getText().trim() + " - " + LocalDateTime.now());
                        }
                    }
                }
            });

            // Back button action
            backButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    new WelcomeHomePageGUI().setVisible(true);
                    dispose();
                }
            });

            setVisible(true);
        }
    // Method to collect and validate user input
    public boolean userInput() {
        String fname = fnameField.getText().trim();
        String lname = lnameField.getText().trim();
        String email = emailField.getText().trim();
        String phoneNum = phoneNumField.getText().trim();
        String dateOfBirth = dateOfBirthField.getText().trim();
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (!validateFirstName(fname)) {
            showMessage("Invalid first name. Please enter letters only.");
            return false;
        }
        if (!validateLastName(lname)) {
            showMessage("Invalid last name. Please enter letters only.");
            return false;
        }
        if (!validateEmail(email)) {
            showMessage("Invalid email format. Please enter a valid email.");
            return false;
        }
        if (!validatePhoneNumber(phoneNum)) {
            showMessage("Invalid phone number. Please enter 8 to 10 digits.");
            return false;
        }
        if (!validateDateOfBirth(dateOfBirth)) {
            showMessage("Invalid date format. Please enter date in yyyy-MM-dd format.");
            return false;
        }
        if (!validateUsername(username)) {
            showMessage("Username cannot be empty.");
            return false;
        }
        if (!validatePassword(password)) {
            showMessage("Password must be at least 8 characters long and include both letters and numbers.");
            return false;
        }

        return true;
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Input Error", JOptionPane.ERROR_MESSAGE);
    }

    // Validation methods
    public static boolean validateFirstName(String fname) {
        return fname.matches("^[A-Za-z]+$");
    }

    public static boolean validateLastName(String lname) {
        return lname.matches("^[A-Za-z]+$");
    }

    public static boolean validateEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        return Pattern.compile(emailRegex).matcher(email).matches();
    }

    public static boolean validatePhoneNumber(String phoneNum) {
        return phoneNum.matches("^\\d{8,10}$");
    }

    public static boolean validateDateOfBirth(String dateOfBirth) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(dateOfBirth);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static boolean validateUsername(String username) {
        return !username.isEmpty();
    }

    public static boolean validatePassword(String password) {
        return password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");
    }

    // 📝 Log user activity
    private void logUserActivity(String activity) {
        try (FileWriter writer = new FileWriter("user_activity_log.txt", true)) {
            writer.write(activity + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to save user data to the database
    public boolean saveToDatabase() {
        String fname = fnameField.getText().trim();
        String lname = lnameField.getText().trim();
        String email = emailField.getText().trim();
        String phoneNum = phoneNumField.getText().trim();
        String dateOfBirth = dateOfBirthField.getText().trim();
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:RegistrationInfo.db")) {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS users (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "firstname TEXT, " +
                    "lastname TEXT, " +
                    "email TEXT UNIQUE, " +
                    "phone_number TEXT, " +
                    "date_of_birth TEXT, " +
                    "username TEXT UNIQUE, " +
                    "password TEXT)";
            conn.createStatement().execute(createTableSQL);

            String insertSQL = "INSERT INTO users (firstname, lastname, email, phone_number, date_of_birth, username, password) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
                pstmt.setString(1, fname);
                pstmt.setString(2, lname);
                pstmt.setString(3, email);
                pstmt.setString(4, phoneNum);
                pstmt.setString(5, dateOfBirth);
                pstmt.setString(6, username);
                pstmt.setString(7, password);
                pstmt.executeUpdate();
            }

            JOptionPane.showMessageDialog(this, "Registration successful!");
            return true;

        } catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE constraint failed")) {
                showMessage("The email or username already exists. Please use a different one.");
            } else {
                e.printStackTrace();
            }
            return false;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UserRegistration().setVisible(true));
    }
}
