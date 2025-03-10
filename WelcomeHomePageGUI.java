package PDIproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomeHomePageGUI extends JFrame {
    
    public WelcomeHomePageGUI() {
        // Setup frame
        setTitle("Welcome to Apsara Paradise Residence");
        setSize(1100, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header panel with title
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(new Color(30, 144, 255));
        JLabel welcomeLabel = new JLabel("Welcome to Apsara Paradise Residence", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 28));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        headerPanel.add(welcomeLabel, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);

        // Center panel with image
        JPanel centerPanel = new JPanel(new BorderLayout());
        
        ImageIcon hotelImageIcon = new ImageIcon("C:/Users/panha/Documents/eclipse IDE/PDIproject/IMG_8410.JPG");
        Image hotelImg = hotelImageIcon.getImage().getScaledInstance(1080, 450, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(hotelImg));
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        centerPanel.add(imageLabel, BorderLayout.CENTER);
        
        add(centerPanel, BorderLayout.CENTER);
        
        // Footer Panel with buttons
        JPanel footerPanel = new JPanel();
        footerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 20));
        footerPanel.setBackground(new Color(240, 240, 240));
        
        JButton loginButton = new JButton("Login");
        JButton signUpButton = new JButton("Sign Up");
        styleButton(loginButton);
        styleButton(signUpButton);
        
        footerPanel.add(loginButton);
        footerPanel.add(signUpButton);
        
        add(footerPanel, BorderLayout.SOUTH);

        // Add action listeners to buttons
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new UserLogin().setVisible(true);
                dispose();
            }
        });

        signUpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new UserRegistration().setVisible(true);
                dispose();
            }
        });

        setVisible(true);
    }

    // Utility method to style buttons
    private void styleButton(JButton button) {
        button.setPreferredSize(new Dimension(120, 40));
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(50, 205, 50));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new WelcomeHomePageGUI().setVisible(true);
            }
        });
    }
}
