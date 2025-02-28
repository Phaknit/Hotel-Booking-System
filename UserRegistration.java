package PDIproject;

import java.util.Scanner;
import java.util.regex.Pattern;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.*;


public class UserRegistration { // declare variable to store user input
	    private String fname;
	    private String lname;
	    private String email;
	    private String phoneNum;
	    private String dateOfBirth;
	    private String username;
	    private String password;

	    // Default Constructor
	    public UserRegistration() {
	        // Default constructor
	    }

	    // Method to collect user input
	    public void userInput() {
	        Scanner input = new Scanner(System.in); 
	        
	        System.out.println("***Input user information***");

	        // ask for user first name and store the input to fname, if it is invalid to the validate condition that had set invalid message will display
	        do {
	            System.out.print("Firstname: ");
	            this.fname = input.nextLine();
	            if (!validateFirstName(this.fname)) {
	                System.out.println("Invalid first name. Please enter letters only.");
	            }
	        } while (!validateFirstName(this.fname));

	        do {
	            System.out.print("Lastname: ");
	            this.lname = input.nextLine();
	            if (!validateLastName(this.lname)) {
	                System.out.println("Invalid last name. Please enter letters only.");
	            }
	        } while (!validateLastName(this.lname));

	        do {
	            System.out.print("Email address: ");
	            this.email = input.nextLine();
	            if (!validateEmail(this.email)) {
	                System.out.println("Invalid email format. Please enter a valid email.");
	            }
	        } while (!validateEmail(this.email));

	        do {
	            System.out.print("Phone number: ");
	            this.phoneNum = input.nextLine();
	            if (!validatePhoneNumber(this.phoneNum)) {
	                System.out.println("Invalid phone number. Please enter 8 to 10 digits.");
	            }
	        } while (!validatePhoneNumber(this.phoneNum));

	        do {
	            System.out.print("Date of Birth (yyyy-MM-dd): ");
	            this.dateOfBirth = input.nextLine();
	            if (!validateDate(this.dateOfBirth)) {
	                System.out.println("Invalid date format. Please enter date in yyyy-MM-dd format.");
	            }
	        } while (!validateDate(this.dateOfBirth));

	        System.out.println();

	        do {
	            System.out.print("Username: ");
	            this.username = input.nextLine();
	            if (!validateUsername(this.username)) {
	                System.out.println("Username cannot be empty.");
	            }
	        } while (!validateUsername(this.username));

	        do {
	            System.out.print("Password: ");
	            this.password = input.nextLine();
	            if (!validatePassword(this.password)) {
	                System.out.println("Password must be at least 8 characters long and include both letters and numbers.");
	            }
	        } while (!validatePassword(this.password));

	        input.close();
	    }

	    // Validation methods
	    public static boolean validateFirstName(String fname) {
	        return fname != null && fname.matches("^[A-Za-z]+$");
	    }

	    public static boolean validateLastName(String lname) {
	        return lname != null && lname.matches("^[A-Za-z]+$");
	    }

	    public static boolean validateEmail(String email) {
	        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
	                "[a-zA-Z0-9_+&*-]+)*@" +
	                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
	                "A-Z]{2,7}$";
	        Pattern pattern = Pattern.compile(emailRegex);
	        return email != null && pattern.matcher(email).matches();
	    }

	    public static boolean validatePhoneNumber(String phoneNum) {
	        return phoneNum != null && phoneNum.matches("^\\d{8,10}$");
	    }

	    public static boolean validateDate(String dateOfBirth) {
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	        dateFormat.setLenient(false);
	        try {
	            Date parsedDate = dateFormat.parse(dateOfBirth);
	            return true;
	        } catch (ParseException e) {
	            return false;
	        }
	    }

	    public static boolean validateUsername(String username) {
	        return username != null && !username.isEmpty();
	    }

	    public static boolean validatePassword(String password) {
	        return password != null && password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");
	    }

	    // Method to save user data to the database
	    public void saveToDatabase() {
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
	            PreparedStatement pstmt = conn.prepareStatement(insertSQL);
	            pstmt.setString(1, this.fname);
	            pstmt.setString(2, this.lname);
	            pstmt.setString(3, this.email);
	            pstmt.setString(4, this.phoneNum);
	            pstmt.setString(5, this.dateOfBirth);
	            pstmt.setString(6, this.username);
	            pstmt.setString(7, this.password);
	            pstmt.executeUpdate();

	            System.out.println("\nRegistration successful!");

	        } catch (SQLException e) {
	            if (e.getMessage().contains("UNIQUE constraint failed")) {
	                System.out.println("The email or username already exists. Please use a different one.");
	            } else {
	                e.printStackTrace();
	            }
	        }
	    }

	    // Method to display user data from the database
	    public void display() {
	        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:RegistrationInfo.db")) {
	            String query = "SELECT * FROM users WHERE username = ?";
	            PreparedStatement stmt = conn.prepareStatement(query);
	            stmt.setString(1, this.username);
	            ResultSet rs = stmt.executeQuery();

	            if (rs.next()) {
	                System.out.println("\n***Personal Information***");
	                System.out.println("Firstname: " + rs.getString("firstname"));
	                System.out.println("Lastname: " + rs.getString("lastname"));
	                System.out.println("Email: " + rs.getString("email"));
	                System.out.println("Phone Number: " + rs.getString("phone_number"));
	                System.out.println("Date of Birth: " + rs.getString("date_of_birth"));
	                System.out.println("\n***Account Details***");
	                System.out.println("Username: " + rs.getString("username"));
	                System.out.println("Password: " + rs.getString("password"));
	                System.out.println("-----------------------------");
	            } else {
	                System.out.println("No user found with username: " + this.username);
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    public class UserLogin {
	    	private static String username;
	    	private static String password;
	    	
	    	 public static boolean validateUsername(String username) {
	    	        return username != null && !username.isEmpty();
	    	    }

	    	    public static boolean validatePassword(String password) {
	    	        return password != null && password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");
	    	    }
	    	public void userLogin() {
	    		Scanner input = new Scanner(System.in);
	    		
	            do {
	                System.out.print("Username: ");
	                this.username = input.nextLine();
	                if (!validateUsername(this.username)) {
	                    System.out.println("Username cannot be empty.");
	                }
	            } while (!validateUsername(this.username));

	            do {
	                System.out.print("Password: ");
	                this.password = input.nextLine();
	                if (!validatePassword(this.password)) {
	                    System.out.println("Password must be at least 8 characters long and include both letters and numbers.");
	                }
	            } while (!validatePassword(this.password));

	            input.close();
	    			
	    	}
	    	
	    	 public boolean userLoginCheck () {
	    	    	String selectUserSQL = "SELECT * FROM Users WHERE username = ? AND password = ?";
	    	
	    	        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:RegistrationInfo.db");
	    	             PreparedStatement stmt = conn.prepareStatement(selectUserSQL)) {
	    	             
	    	            stmt.setString(1, username);
	    	            stmt.setString(2, password); // Use password hashing in production
	    	            ResultSet rs = stmt.executeQuery();
	    	
	    	            return rs.next(); // User found
	    	        } catch (SQLException e) {
	    	            e.printStackTrace();
	    	            return false;
	    	        }
	    	    }
	    }
//	    public boolean userLogin (String username, String password) {
//	    	String selectUserSQL = "SELECT * FROM Users WHERE username = ? AND password = ?";
//	
//	        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:RegistrationInfo.db");
//	             PreparedStatement stmt = conn.prepareStatement(selectUserSQL)) {
//	             
//	            stmt.setString(1, username);
//	            stmt.setString(2, password); // Use password hashing in production
//	            ResultSet rs = stmt.executeQuery();
//	
//	            return rs.next(); // User found
//	        } catch (SQLException e) {
//	            e.printStackTrace();
//	            return false;
//	        }
//	    }
	    
	   

	    // Main method
	    public static void main(String[] args) {
	        // Create object using the default constructor
	        UserRegistration user = new UserRegistration();
//	        UserLogin login = new UserLogin();

	        // Collect user input
	        user.userInput();

	        // Save user data to database
	        user.saveToDatabase();

	        // Display user data
	        user.display();
	       
			 
//			 user.userLogin();
//			 boolean isValidUser = login.userLoginCheck();
//
//		        if (isValidUser) {
//		            System.out.println("Login successful!");
//		        } else {
//		            System.out.println("Invalid username or password.");
//		        }
//			 
	    }
	}
	  


