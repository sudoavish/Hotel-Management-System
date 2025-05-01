import java.util.ArrayList;
import java.util.Scanner;

public class HotelManagementSystem {

    public static ArrayList<Room> rooms = new ArrayList<>();
    public static ArrayList<Customer> customers = new ArrayList<>();
    private static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        initializeRooms();

        int choice;
        do {
            System.out.println("===== Hotel Management Menu =====");
            System.out.println("1. View Available Rooms");
            System.out.println("2. Book a Room");
            System.out.println("3. View Booked Rooms");
            System.out.println("4. Check Out");
            System.out.println("5. Cancel Booking");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            try {
                choice = input.nextInt();
                input.nextLine();
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                input.nextLine();
                choice = 0;
            }

            switch (choice) {
                case 1: showAvailableRooms(); break;
                case 2: bookRoom(); break;
                case 3: viewBookedRooms(); break;
                case 4: checkOut(); break;
                case 5: cancelBooking(); break;
                case 6: System.out.println("Thank you for using the Hotel Management System!"); break;
                default: System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 6);
        input.close();
    }

    static void initializeRooms() {
        rooms.add(new Room(101, "Single", 1500));
        rooms.add(new Room(102, "Double", 2500));
        rooms.add(new Room(103, "Single", 1500));
        rooms.add(new Room(104, "Double", 2500));
    }

    static void showAvailableRooms() {
        System.out.println("\nAvailable Rooms:");
        boolean hasAvailable = false;
        for (Room room : rooms) {
            if (!room.isBooked) {
                room.displayRoomInfo();
                hasAvailable = true;
            }
        }
        if (!hasAvailable) {
            System.out.println("No rooms are currently available.");
        }
    }

    static void bookRoom() {
        System.out.print("Enter your name: ");
        String name = input.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }

        System.out.print("Enter your phone number: ");
        String phone = input.nextLine().trim();
        if (!phone.matches("\\d{10}")) { // Basic phone validation
            System.out.println("Invalid phone number. Please enter a 10-digit number.");
            return;
        }

        showAvailableRooms();
        System.out.print("Enter room number to book: ");
        int roomNum;
        try {
            roomNum = input.nextInt();
            input.nextLine();
        } catch (Exception e) {
            System.out.println("Invalid room number.");
            input.nextLine();
            return;
        }

        for (Room room : rooms) {
            if (room.roomNumber == roomNum) {
                if (!room.isBooked) {
                    room.isBooked = true;
                    customers.add(new Customer(name, phone, roomNum));
                    System.out.println("Room " + roomNum + " booked successfully for " + name + "!");
                    return;
                } else {
                    System.out.println("Room " + roomNum + " is already booked.");
                    return;
                }
            }
        }
        System.out.println("Invalid room number. No such room exists.");
    }

    static void viewBookedRooms() {
        System.out.println("\nBooked Rooms:");
        boolean hasBookedRooms = false;
        for (Room room : rooms) {
            if (room.isBooked) {
                room.displayRoomInfo();
                for (Customer customer : customers) {
                    if (customer.roomNumber == room.roomNumber) {
                        customer.displayCustomerInfo();
                    }
                }
                hasBookedRooms = true;
            }
        }
        if (!hasBookedRooms) {
            System.out.println("No rooms are currently booked.");
        }
    }

    static void checkOut() {
        System.out.print("Enter room number to check out: ");
        int roomNum;
        try {
            roomNum = input.nextInt();
            input.nextLine();
        } catch (Exception e) {
            System.out.println("Invalid room number.");
            input.nextLine();
            return;
        }

        for (Room room : rooms) {
            if (room.roomNumber == roomNum) {
                if (room.isBooked) {
                    room.isBooked = false;
                    for (Customer customer : customers) {
                        if (customer.roomNumber == roomNum) {
                            System.out.println("Check-out successful for " + customer.name + " (Room " + roomNum + ")");
                            customers.remove(customer);
                            return;
                        }
                    }
                } else {
                    System.out.println("Room " + roomNum + " is not booked.");
                    return;
                }
            }
        }
        System.out.println("Invalid room number. No such room exists.");
    }

    static void cancelBooking() {
        System.out.print("Enter room number to cancel booking: ");
        int roomNum;
        try {
            roomNum = input.nextInt();
            input.nextLine();
        } catch (Exception e) {
            System.out.println("Invalid room number.");
            input.nextLine();
            return;
        }

        for (Room room : rooms) {
            if (room.roomNumber == roomNum) {
                if (room.isBooked) {
                    room.isBooked = false;
                    for (Customer customer : customers) {
                        if (customer.roomNumber == roomNum) {
                            System.out.println("Booking cancelled for " + customer.name + " (Room " + roomNum + ")");
                            customers.remove(customer);
                            return;
                        }
                    }
                } else {
                    System.out.println("Room " + roomNum + " is not booked.");
                    return;
                }
            }
        }
        System.out.println("Invalid room number. No such room exists.");
    }
}