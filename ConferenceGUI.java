package PDIproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConferenceGUI {
	 public static void main(String[] args) {
	        SwingUtilities.invokeLater(ConferenceGUI::new);
	    }

	    public ConferenceGUI() {
	        JFrame frame = new JFrame("Room Display Page");
	        frame.setSize(900, 800);
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setLocationRelativeTo(null);
	        frame.setLayout(new BorderLayout());
	        
	     // Set custom logo
	        frame.setIconImage(new ImageIcon("C:\\Users\\panha\\Downloads\\Telegram Desktop\\photo_2025-03-26_23-22-57.jpg").getImage());

	        // Header Panel
	        JPanel headerPanel = new JPanel(new BorderLayout());
	        headerPanel.setBackground(new Color(34, 34, 34));
	        headerPanel.setPreferredSize(new Dimension(900, 80));
	        JLabel titleLabel = new JLabel("APSARA PARADISE RESIDENCE", SwingConstants.LEFT);
	        titleLabel.setForeground(Color.WHITE);
	        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));
	        headerPanel.add(titleLabel, BorderLayout.WEST);
	        frame.add(headerPanel, BorderLayout.NORTH);

	     // Banner Panel
	        JPanel bannerPanel = new JPanel(new BorderLayout()) {
	            private final Image backgroundImage = new ImageIcon("C:\\Users\\panha\\Downloads\\Telegram Desktop\\photo_2025-03-26_23-21-28.jpg").getImage();
	            
	            @Override
	            protected void paintComponent(Graphics g) {
	                super.paintComponent(g);
	                g.drawImage(backgroundImage, 0, 0, getWidth(), (int) (getHeight()), this); // Reduce height to 2/3
	            }
	        };
	        bannerPanel.setPreferredSize(new Dimension(900, 200)); // Adjust height manually if needed
	        frame.add(bannerPanel, BorderLayout.CENTER);


	        // Services Panel
	        JPanel servicesPanel = new JPanel(new GridLayout(1, 5, 20, 20));
	        servicesPanel.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));

	        String[] serviceTitles = {"Small Meeting Room", "Conference Hall", "Large Conference Hall"};
	        String[] serviceDescriptions = {
	            "All conference rooms are set with facilities with latest technology ready for your important business meeting.",
	            "Perfect for environmentally conscious gatherings. ",
	            "Best for event host, ensuring that your event runs smoothly",
	            
	        };
	        String[] imagePaths = {
	            "C:\\Users\\panha\\Downloads\\Telegram Desktop\\c3.jpg",
	            "C:\\Users\\panha\\Downloads\\Telegram Desktop\\c2.jpg",
	            "C:\\Users\\panha\\Downloads\\Telegram Desktop\\c6.jpg",
	            
	        };

	        for (int i = 0; i < 3; i++) {
	            JPanel serviceCard = new JPanel(new BorderLayout());
	            serviceCard.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));

	            // Load the image normally
	            ImageIcon originalIcon = new ImageIcon(imagePaths[i]);
	            Image image = originalIcon.getImage();

	            // Set a preferred size for the image button
	            JButton imgButton = new JButton(new ImageIcon(image));
	            imgButton.setPreferredSize(new Dimension(150, 200));  // Set width to 150 and height to 200
	            imgButton.setBorderPainted(false);
	            imgButton.setFocusPainted(false);
	            imgButton.setContentAreaFilled(false);
	            
	            int index = i;
	            imgButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Redirecting to " + serviceTitles[index] + " page"));

	            JLabel title = new JLabel(serviceTitles[i], SwingConstants.CENTER);
	            title.setFont(new Font("Times New Roman", Font.BOLD, 16));
	            title.setForeground(new Color(50, 50, 50));

	            JLabel description = new JLabel("<html><div style='text-align:center;'>" + serviceDescriptions[i] + "</div></html>", SwingConstants.CENTER);
	            description.setFont(new Font("Times New Roman", Font.PLAIN, 12));
	            description.setForeground(new Color(100, 100, 100));

	            JPanel textPanel = new JPanel(new BorderLayout());
	            textPanel.add(title, BorderLayout.NORTH);
	            textPanel.add(description, BorderLayout.CENTER);
	            textPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

	            serviceCard.add(imgButton, BorderLayout.NORTH);
	            serviceCard.add(textPanel, BorderLayout.CENTER);
	            servicesPanel.add(serviceCard);
	        }

	        // Add components to frame
	        frame.add(servicesPanel, BorderLayout.SOUTH);
	        frame.setVisible(true);

	    }
	}






   