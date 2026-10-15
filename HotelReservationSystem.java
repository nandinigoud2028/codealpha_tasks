import java.io.*;
import java.util.*;

class Room {
    int roomNumber;
    String category;
    double price;
    boolean available;

    Room(int roomNumber, String category, double price) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.price = price;
        this.available = true;
    }
}

class Reservation {
    String name;
    int roomNumber;
    String category;
    double amount;

    Reservation(String name, int roomNumber, String category, double amount) {
        this.name = name;
        this.roomNumber = roomNumber;
        this.category = category;
        this.amount = amount;
    }
}

public class HotelReservationSystem {

    static ArrayList<Room> rooms = new ArrayList<>();
    static ArrayList<Reservation> bookings = new ArrayList<>();

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        // Creating rooms
        rooms.add(new Room(101, "Standard", 1500));
        rooms.add(new Room(102, "Standard", 1500));
        rooms.add(new Room(201, "Deluxe", 2500));
        rooms.add(new Room(202, "Deluxe", 2500));
        rooms.add(new Room(301, "Suite", 4000));

        loadBookings();

        while (true) {

            System.out.println("\n===== HOTEL RESERVATION SYSTEM =====");
            System.out.println("1. Search Rooms");
            System.out.println("2. Book Room");
            System.out.println("3. Cancel Reservation");
            System.out.println("4. View Bookings");
            System.out.println("5. Exit");

            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    searchRooms();
                    break;

                case 2:
                    bookRoom();
                    break;

                case 3:
                    cancelBooking();
                    break;

                case 4:
                    viewBookings();
                    break;

                case 5:
                    System.out.println("Thank you for using the Hotel Reservation System!");
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    // Search available rooms
    static void searchRooms() {

        System.out.println("\nAvailable Rooms:");

        boolean found = false;

        for (Room room : rooms) {

            if (room.available) {

                System.out.println(
                    "Room: " + room.roomNumber +
                    " | Category: " + room.category +
                    " | Price: Rs." + room.price
                );

                found = true;
            }
        }

        if (!found) {
            System.out.println("No rooms available.");
        }
    }

    // Book a room
    static void bookRoom() {

        searchRooms();

        System.out.print("\nEnter room number: ");
        int roomNumber = sc.nextInt();

        Room selectedRoom = null;

        for (Room room : rooms) {

            if (room.roomNumber == roomNumber && room.available) {
                selectedRoom = room;
                break;
            }
        }

        if (selectedRoom == null) {
            System.out.println("Room is not available.");
            return;
        }

        sc.nextLine();

        System.out.print("Enter your name: ");
        String name = sc.nextLine();

        System.out.println("\nBooking Details");
        System.out.println("Room: " + selectedRoom.roomNumber);
        System.out.println("Category: " + selectedRoom.category);
        System.out.println("Price: Rs." + selectedRoom.price);

        System.out.print("Confirm booking? (yes/no): ");
        String confirm = sc.nextLine();

        if (confirm.equalsIgnoreCase("yes")) {

            // Payment simulation
            System.out.print("Enter payment amount: Rs.");
            double payment = sc.nextDouble();

            if (payment >= selectedRoom.price) {

                selectedRoom.available = false;

                Reservation booking = new Reservation(
                    name,
                    selectedRoom.roomNumber,
                    selectedRoom.category,
                    selectedRoom.price
                );

                bookings.add(booking);

                saveBookings();

                System.out.println("\nPayment successful!");
                System.out.println("Room booked successfully.");
                System.out.println("Booking Name: " + name);
                System.out.println("Room Number: " + selectedRoom.roomNumber);
                System.out.println("Category: " + selectedRoom.category);

            } else {

                System.out.println("Payment failed. Insufficient amount.");
            }

        } else {

            System.out.println("Booking cancelled.");
        }
    }

    // Cancel booking
    static void cancelBooking() {

        System.out.print("\nEnter room number to cancel: ");
        int roomNumber = sc.nextInt();

        Reservation foundBooking = null;

        for (Reservation booking : bookings) {

            if (booking.roomNumber == roomNumber) {
                foundBooking = booking;
                break;
            }
        }

        if (foundBooking == null) {

            System.out.println("No booking found.");

        } else {

            bookings.remove(foundBooking);

            // Make room available again
            for (Room room : rooms) {

                if (room.roomNumber == roomNumber) {
                    room.available = true;
                    break;
                }
            }

            saveBookings();

            System.out.println("Reservation cancelled successfully.");
        }
    }

    // View bookings
    static void viewBookings() {

        System.out.println("\n===== BOOKING DETAILS =====");

        if (bookings.isEmpty()) {

            System.out.println("No bookings available.");
            return;
        }

        for (Reservation booking : bookings) {

            System.out.println("----------------------------");
            System.out.println("Name     : " + booking.name);
            System.out.println("Room No. : " + booking.roomNumber);
            System.out.println("Category : " + booking.category);
            System.out.println("Amount   : Rs." + booking.amount);
        }
    }

    // Save bookings to file
    static void saveBookings() {

        try {

            FileWriter writer = new FileWriter("bookings.txt");

            for (Reservation booking : bookings) {

                writer.write(
                    booking.name + "," +
                    booking.roomNumber + "," +
                    booking.category + "," +
                    booking.amount + "\n"
                );
            }

            writer.close();

        } catch (IOException e) {

            System.out.println("Error saving booking.");
        }
    }

    // Load bookings from file
    static void loadBookings() {

        try {

            File file = new File("bookings.txt");

            if (!file.exists()) {
                return;
            }

            Scanner fileReader = new Scanner(file);

            while (fileReader.hasNextLine()) {

                String line = fileReader.nextLine();

                String[] data = line.split(",");

                String name = data[0];
                int roomNumber = Integer.parseInt(data[1]);
                String category = data[2];
                double amount = Double.parseDouble(data[3]);

                bookings.add(
                    new Reservation(
                        name,
                        roomNumber,
                        category,
                        amount
                    )
                );

                // Mark room as unavailable
                for (Room room : rooms) {

                    if (room.roomNumber == roomNumber) {
                        room.available = false;
                    }
                }
            }

            fileReader.close();

        } catch (Exception e) {

            System.out.println("Error loading bookings.");
        }
    }
}