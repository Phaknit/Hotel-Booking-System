package PDIproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GUI extends JFrame {

	    public GUI() {
	        setTitle("Apsara Paradise Residence");
	        setSize(1100, 750);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setLocationRelativeTo(null);
	        setLayout(new BorderLayout());
	        
	        // Set the background of the entire frame to white
	        getContentPane().setBackground(Color.WHITE);

	        // ======= TOP: TITLE AND BUTTONS =======
	        JPanel topPanel = new JPanel(new BorderLayout());
	        JPanel titlePanel = new JPanel(); 
	        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20)); // Center align title and give vertical space

	        JLabel titleLabel = new JLabel("Apsara Paradise Residence", JLabel.CENTER);
	        titleLabel.setFont(new Font("Arial Black", Font.BOLD, 36)); // Stylish bold font, size 36
	        titleLabel.setText(titleLabel.getText().toUpperCase()); // Make the text uppercase
	        titlePanel.add(titleLabel); // Add title label to panel
	        
	        // ======= Right Panel for Buttons =======
	        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	        
	        JButton locationButton = new JButton("Location (Branch)");
	        JButton costConversionButton = new JButton("Cost Conversion");

	        // Location button action (show options for branches)
	        locationButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                String[] locations = {"Siem Reap Branch", "Mondulkiri Branch"};
	                String selectedLocation = (String) JOptionPane.showInputDialog(
	                        null,
	                        "Choose a branch location:",
	                        "Branch Selection",
	                        JOptionPane.PLAIN_MESSAGE,
	                        null,
	                        locations,
	                        locations[0]
	                );
	                if (selectedLocation != null) {
	                    JOptionPane.showMessageDialog(null, "You selected: " + selectedLocation);
	                }
	            }
	        });

	        // Cost Conversion button options 
	        costConversionButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                String[] options = {"USD", "KHR"};
	                String currency = (String) JOptionPane.showInputDialog(
	                        null,
	                        "Choose a currency:",
	                        "Currency Selection",
	                        JOptionPane.PLAIN_MESSAGE,
	                        null,
	                        options,
	                        options[0]
	                );
	                if (currency != null) {
	                    JOptionPane.showMessageDialog(null, "You selected: " + currency);
	                }
	            }
	        });

	        // Add the buttons to the right panel
	        
	        rightPanel.add(locationButton);
	        rightPanel.add(costConversionButton);

	        topPanel.add(titlePanel, BorderLayout.CENTER);
	        topPanel.add(rightPanel, BorderLayout.EAST);

	        add(topPanel, BorderLayout.NORTH);

	        // ======= MAIN CONTENT PANEL =======
	        JPanel mainPanel = new JPanel(new BorderLayout());
	        mainPanel.setBackground(Color.WHITE); 

	        // ======= LEFT PANEL (Feature Images) =======
	        JPanel leftPanel = new JPanel();
	        leftPanel.setLayout(new GridLayout(6, 1, 10, 10)); // 6 rows, 1 column
	        leftPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 10));


	        // Image Paths
	        String[] imagePaths = {
	            "C:\\Users\\panha\\Documents\\eclipse IDE\\PDIproject\\IMG_8408.JPG",  // Rooms
	            "C:\\Users\\panha\\Documents\\eclipse IDE\\PDIproject\\IMG_8413.JPG",  // Dining
	            "C:\\Users\\panha\\Documents\\eclipse IDE\\PDIproject\\IMG_8412.JPG",  // Conference
	            "C:\\Users\\panha\\Documents\\eclipse IDE\\PDIproject\\IMG_8411.JPG",  // Spa
	            "C:\\Users\\panha\\Documents\\eclipse IDE\\PDIproject\\IMG_8409.JPG",  // Garden
	            "C:\\Users\\panha\\Documents\\eclipse IDE\\PDIproject\\IMG_8407.JPG"   // Activities
	        };

	        String[] featureNames = {"Rooms", "Dining", "Conference", "Spa", "Garden", "Activities"};

	        // Adding images and labels
	        for (int i = 0; i < imagePaths.length; i++) {
	            JPanel featurePanel = new JPanel(new BorderLayout());
	            JButton featureButton = createImageButton(imagePaths[i], featureNames[i]);

	            JLabel featureLabel = new JLabel(featureNames[i], JLabel.CENTER);
	            featureLabel.setFont(new Font("Serif", Font.BOLD, 16));

	            featurePanel.add(featureButton, BorderLayout.CENTER);
	            featurePanel.add(featureLabel, BorderLayout.SOUTH);
	            leftPanel.add(featurePanel);
	        }

	        mainPanel.add(leftPanel, BorderLayout.WEST);

	        // ======= CENTER PANEL (Hotel Image & Description) =======
	        JPanel centerPanel = new JPanel();
	        centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); 

	        // Hotel Image - Adjusting width and height
	        ImageIcon hotelImageIcon = new ImageIcon("C:\\Users\\panha\\Documents\\eclipse IDE\\PDIproject\\IMG_8410.JPG");
	        Image hotelImg = hotelImageIcon.getImage().getScaledInstance(1100, 500, Image.SCALE_SMOOTH); // Increased length (height)
	        JLabel imageLabel = new JLabel(new ImageIcon(hotelImg));
	        imageLabel.setHorizontalAlignment(JLabel.CENTER);
	        imageLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
	        centerPanel.add(imageLabel);

	        // Description - Adjust the size of the text box, make it invisible, and center align the text
	        JTextArea descriptionArea = new JTextArea(
	            "Apsara Paradise Residence offers a world-class hospitality experience designed for both relaxation and business. " +
	            "Our elegant hotel boasts a variety of luxurious rooms tailored to suit every guest, from cozy single suites to spacious " +
	            "family accommodations. Indulge in ultimate relaxation at our serene spa, where expert therapists provide rejuvenating " +
	            "treatments. For business travelers, our state-of-the-art conference hall ensures a seamless meeting experience, while our " +
	            "grand dining hall serves exquisite international and local cuisine, perfect for any occasion. Unwind in our sophisticated " +
	            "lounge, the ideal space for socializing over premium drinks. Whether you're seeking adventure or leisure, our curated activities " +
	            "offer something for everyone—from cultural excursions to on-site entertainment. At Apsara Paradise Residence, luxury isn’t " +
	            "just a service—it’s a way of life. Book your stay today and experience true paradise!"
	        );
	        descriptionArea.setFont(new Font("Georgia", Font.ITALIC, 14));  // Changed font to italicized Georgia
	        descriptionArea.setLineWrap(true);
	        descriptionArea.setWrapStyleWord(true);
	        descriptionArea.setEditable(false);
	        descriptionArea.setBackground(Color.WHITE);  // Making the background white to blend with the panel
	        descriptionArea.setBorder(null);  // Removing border
	        descriptionArea.setPreferredSize(new Dimension(1000, 200)); // Increased width to fit hotel picture
	        descriptionArea.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align the text
	        descriptionArea.setAlignmentY(Component.CENTER_ALIGNMENT);


	        JScrollPane descriptionScroll = new JScrollPane(descriptionArea);
	        descriptionScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
	        centerPanel.add(descriptionScroll); // Add the scrollable text area to the panel

	        mainPanel.add(centerPanel, BorderLayout.CENTER);
	        add(mainPanel, BorderLayout.CENTER);

	        // ======= BOTTOM: BOOKING SECTION =======
	        JPanel bookingPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

	        JButton checkAvailability = new JButton("Check Available");
	        JComboBox<String> adultsBox = new JComboBox<>(new String[]{"1 Adult", "2 Adults", "3 Adults"});
	        JComboBox<String> childrenBox = new JComboBox<>(new String[]{"0 Children", "1 Child", "2 Children"});
	        JButton bookNow = new JButton("BOOK NOW");

	        bookingPanel.add(checkAvailability);
	        bookingPanel.add(adultsBox);
	        bookingPanel.add(childrenBox);
	        bookingPanel.add(bookNow);

	        add(bookingPanel, BorderLayout.SOUTH);

	        setVisible(true);
	    }

	    // ======= METHOD: Create Clickable Image Button =======
	    private JButton createImageButton(String imagePath, String featureName) {
	        ImageIcon featureIcon = new ImageIcon(imagePath);
	        Image scaledImage = featureIcon.getImage().getScaledInstance(180, 120, Image.SCALE_SMOOTH);
	        JButton Room = new JButton(new ImageIcon(scaledImage));

	        Room.setBorder(BorderFactory.createEmptyBorder());
	        Room.setContentAreaFilled(false);
	        Room.setToolTipText("Go to " + featureName);

	        // Add Click Action
	        Room.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                JOptionPane.showMessageDialog(null, "Opening " + featureName + " page...");
	            }
	        });

	        return Room;
	    }

	    public static void main(String[] args) {
	        SwingUtilities.invokeLater(() -> new GUI());
	    }
	}


