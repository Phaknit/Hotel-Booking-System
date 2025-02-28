package Hotel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class RoomAvailability {
    // HashMap to store the availability of rooms
    private Map<String, Integer> roomAvailability;
    // HashMap to store the total number of rooms
    private Map<String, Integer> totalRooms;
    // HashMap to store the rates of each room type
    private Map<String, Integer> roomRates;
    // ArrayList to store the types of rooms
    private ArrayList<String> roomTypes;

    public RoomAvailability() {
        roomAvailability = new HashMap<>();
        totalRooms = new HashMap<>();
        roomRates = new HashMap<>();
        roomTypes = new ArrayList<>();

        // Add room types and rates
        roomTypes.add("Suite");
        roomTypes.add("Single Bed");
        roomTypes.add("Double Bed");

        roomRates.put("Suite", 200);
        roomRates.put("Single Bed", 100);
        roomRates.put("Double Bed", 150);

        // Initialize total number of rooms and their availability
        totalRooms.put("Suite", 5);
        totalRooms.put("Single Bed", 15);
        totalRooms.put("Double Bed", 10);

        roomAvailability.put("Suite", 4); // 5 total, 1 booked
        roomAvailability.put("Single Bed", 13); // 15 total, 2 booked
        roomAvailability.put("Double Bed", 5); // 10 total, 5 booked
    }

    // Method to check room availability
    public String isRoomAvailable(String roomType) {
        if (roomAvailability.containsKey(roomType)) {
            int total = totalRooms.get(roomType);
            int booked = total - roomAvailability.get(roomType);
            int availableRooms = roomAvailability.get(roomType);
            return availableRooms > 0 ? 
                "Available (" + availableRooms + " rooms left, " + booked + " booked)" : 
                "Not Available (" + booked + " booked)";
        } else {
            return "Room type does not exist.";
        }
    }

    // Method to book a room
    public void bookRoom(String roomType) {
        if ("Available".equals(isRoomAvailable(roomType).split(" ")[0])) {
            roomAvailability.put(roomType, roomAvailability.get(roomType) - 1);
            System.out.println(roomType + " has been booked at $" + roomRates.get(roomType) + " per night.");
        } else {
            System.out.println(roomType + " is not available.");
        }
    }

    public static void main(String[] args) {
        RoomAvailability hbs = new RoomAvailability();
        Scanner scanner = new Scanner(System.in);
        boolean exitLoop = false;

        while (!exitLoop) {
            // Check room availability and book a room based on user input
            System.out.println("Enter the room type you want to check availability for (Suite, Single Bed, Double Bed): ");
            String roomType = scanner.nextLine();

            System.out.println("Availability for " + roomType + ": " + hbs.isRoomAvailable(roomType));

            System.out.println("Do you want to book this room? (yes/no)");
            String book = scanner.nextLine();

            if ("yes".equalsIgnoreCase(book)) {
                hbs.bookRoom(roomType);
                exitLoop = true;
            } else {
                System.out.println("No room booked. Would you like to check another room? (yes/no)");
                String checkAnother = scanner.nextLine();
                if (!"yes".equalsIgnoreCase(checkAnother)) {
                    exitLoop = true;
                }
            }
        }

        // Close scanner
        scanner.close();
    }
}
