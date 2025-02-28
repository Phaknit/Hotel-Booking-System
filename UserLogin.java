package PDIproject;

import java.util.Scanner;
import java.sql.*;

public class UserLogin {
	private static String username;
	private static String password;
	
	UserLogin(){
		
	}
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
	 
	 public static void main (String[] args) {
		 UserLogin login = new UserLogin();
		 
		 login.userLogin();
		 boolean isValidUser = login.userLoginCheck();

	        if (isValidUser) {
	            System.out.println("Login successful!");
	        } else {
	            System.out.println("Invalid username or password.");
	        }
		 
		 
		 
	 }
}

	

	 