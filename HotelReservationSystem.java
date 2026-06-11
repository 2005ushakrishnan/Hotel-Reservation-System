import java.io.*;
import java.util.*;

class Room {
    int roomNo;
    String category;
    double price;
    boolean available;

    Room(int roomNo, String category, double price) {
        this.roomNo = roomNo;
        this.category = category;
        this.price = price;
        this.available = true;
    }
}

class Booking implements Serializable {
    String customerName;
    int roomNo;
    String category;
    double amount;

    Booking(String customerName, int roomNo, String category, double amount) {
        this.customerName = customerName;
        this.roomNo = roomNo;
        this.category = category;
        this.amount = amount;
    }

    public String toString() {
        return "Customer: " + customerName +
               ", Room No: " + roomNo +
               ", Category: " + category +
               ", Amount Paid: ₹" + amount;
    }
}

public class HotelReservationSystem {

    static ArrayList<Room> rooms = new ArrayList<>();
    static ArrayList<Booking> bookings = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    static void initializeRooms() {
        rooms.add(new Room(101, "Standard", 1000));
        rooms.add(new Room(102, "Standard", 1000));
        rooms.add(new Room(201, "Deluxe", 2000));
        rooms.add(new Room(202, "Deluxe", 2000));
        rooms.add(new Room(301, "Suite", 3000));
    }

    static void searchRooms() {
        System.out.println("\nAvailable Rooms:");
        for (Room r : rooms) {
            if (r.available) {
                System.out.println("Room No: " + r.roomNo +
                        " | Category: " + r.category +
                        " | Price: ₹" + r.price);
            }
        }
    }

    static void bookRoom() {
        System.out.print("Enter Customer Name: ");
        String name = sc.nextLine();

        searchRooms();

        System.out.print("Enter Room Number to Book: ");
        int roomNo = sc.nextInt();
        sc.nextLine();

        for (Room r : rooms) {
            if (r.roomNo == roomNo && r.available) {

                System.out.println("Room Price: ₹" + r.price);
                System.out.println("Payment Successful!");

                r.available = false;

                Booking booking =
                        new Booking(name, r.roomNo, r.category, r.price);

                bookings.add(booking);
                saveBooking(booking);

                System.out.println("Room Booked Successfully!");
                return;
            }
        }

        System.out.println("Room Not Available!");
    }

    static void cancelBooking() {
        System.out.print("Enter Room Number to Cancel: ");
        int roomNo = sc.nextInt();
        sc.nextLine();

        Iterator<Booking> it = bookings.iterator();

        while (it.hasNext()) {
            Booking b = it.next();

            if (b.roomNo == roomNo) {
                it.remove();

                for (Room r : rooms) {
                    if (r.roomNo == roomNo) {
                        r.available = true;
                    }
                }

                System.out.println("Booking Cancelled Successfully!");
                return;
            }
        }

        System.out.println("Booking Not Found!");
    }

    static void viewBookings() {
        System.out.println("\nBooking Details:");

        if (bookings.isEmpty()) {
            System.out.println("No Bookings Found.");
            return;
        }

        for (Booking b : bookings) {
            System.out.println(b);
        }
    }

    static void saveBooking(Booking booking) {
        try {
            FileWriter fw = new FileWriter("bookings.txt", true);
            fw.write(booking.toString() + "\n");
            fw.close();
        } catch (IOException e) {
            System.out.println("File Error!");
        }
    }

    public static void main(String[] args) {

        initializeRooms();

        while (true) {
            System.out.println("\n===== HOTEL RESERVATION SYSTEM =====");
            System.out.println("1. Search Rooms");
            System.out.println("2. Book Room");
            System.out.println("3. Cancel Booking");
            System.out.println("4. View Booking Details");
            System.out.println("5. Exit");

            System.out.print("Enter Choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

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
                    System.out.println("Thank You!");
                    System.exit(0);

                default:
                    System.out.println("Invalid Choice!");
            }
        }
    }
}
