package PDIproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TeamMember extends JFrame {
    public TeamMember() {
        // Frame setup
        setTitle("Our Teams");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Set the application icon
        setIconImage(new ImageIcon("C:\\Users\\panha\\Downloads\\Telegram Desktop\\photo_2025-03-26_23-22-57.jpg").getImage());

        // Title section
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.BLACK); // Black background
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JLabel titleLabel = new JLabel("Meet Our Teams", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);

        add(titlePanel, BorderLayout.NORTH);

        // Background panel with GridBagLayout for positioning
        JPanel backgroundPanel = new JPanel() {
            private final Image backgroundImage = new ImageIcon("C:\\Users\\panha\\Downloads\\Telegram Desktop\\photo_2025-03-26_23-21-28.jpg").getImage(); // Replace with your background image path

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(new GridBagLayout()); // GridBagLayout for precise positioning

        // Add team member cards with custom positioning
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20); // Spacing between elements

        // First team member (top-left)
        gbc.gridx = 0; // Column 0
        gbc.gridy = 0; // Row 0
        backgroundPanel.add(createTeamMemberCard("Neth Barom Phaknit", "GUI designer", "C:\\Users\\panha\\Downloads\\Telegram Desktop\\i1.jpg"), gbc);

        // Second team member (center-right)
        gbc.gridx = 0; // Column 1
        gbc.gridy = 3; // Row 0
        backgroundPanel.add(createTeamMemberCard("Eng Soklang", "Sub-class coder", "C:\\Users\\panha\\Downloads\\Telegram Desktop\\photo_2025-03-26_23-29-11.jpg"), gbc);

        // Third team member (bottom-right)
        gbc.gridx = 2; // Column 2
        gbc.gridy = 2; // Row 2
        gbc.anchor = GridBagConstraints.SOUTHEAST; // Anchor to bottom-right
        backgroundPanel.add(createTeamMemberCard("Van Sotheany", "Code integrator", "C:\\Users\\panha\\Pictures\\Camera Roll\\image.jpg"), gbc);

        add(backgroundPanel, BorderLayout.CENTER);

        // Bottom panel for Back icon
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Align Back icon to the left
        bottomPanel.setBackground(Color.BLACK); // Match title background

        // Scale down the Back icon
        ImageIcon backIconImage = new ImageIcon("C:\\Users\\panha\\Downloads\\keyboard_double_arrow_left_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24.png"); // Replace with your back icon path
        Image scaledBackIcon = backIconImage.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH); // Scale to smaller size
        JLabel backIcon = new JLabel(new ImageIcon(scaledBackIcon));
        backIcon.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Change cursor to hand when hovering

        // Add click listener to the icon
        backIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Logic for the Back button (e.g., close current window)
            	new HotelMainPage();
                dispose(); // Close this window (can be replaced with navigation logic)
            }
        });

        bottomPanel.add(backIcon);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    // Method to create a card layout for each team member
    private JPanel createTeamMemberCard(String name, String role, String imagePath) {
        JPanel cardPanel = new JPanel(new BorderLayout());
        cardPanel.setBackground(Color.LIGHT_GRAY); // Grey background for the card
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY, 2), // Outer border
                BorderFactory.createEmptyBorder(10, 10, 10, 10) // Padding
        ));

        // Image section
        ImageIcon imageIcon = new ImageIcon(imagePath);
        Image scaledImage = imageIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH); // Enlarge image
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        cardPanel.add(imageLabel, BorderLayout.WEST);

        // Text section with better styling
        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.setBackground(Color.LIGHT_GRAY); // Grey background matching the card
        textPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 22));
        nameLabel.setForeground(Color.BLACK);

        JLabel roleLabel = new JLabel(role);
        roleLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        roleLabel.setForeground(Color.DARK_GRAY);

        textPanel.add(nameLabel);
        textPanel.add(roleLabel);
        cardPanel.add(textPanel, BorderLayout.CENTER);

        return cardPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TeamMember();
        });
    }
}
