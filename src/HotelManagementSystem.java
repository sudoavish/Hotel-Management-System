import java.util.*;

public class HotelManagementSystem {

    private static ArrayList<Room> rooms = new ArrayList<>();
    private static ArrayList<Customer> customers = new ArrayList<>();
    private static Queue<Customer> waitingList = new LinkedList<>();
    private static BookingHistory bookingHistory = new BookingHistory();
    private static Scanner input = new Scanner(System.in);

    public static ArrayList<Room> getRooms(){
        return rooms;
    }

    public static ArrayList<Customer> getCustomers(){
        return customers;
    }

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
            System.out.println("6. View Room Booking History");
            System.out.println("7. Exit");
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
                case 6: viewBookingHistory(); break;
                case 7: System.out.println("Thank you for using the Hotel Management System!"); break;
                default: System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 7);
        input.close();
    }

    static void initializeRooms() {
        addRoom(new Room(101, "Single", 1500));
        addRoom(new Room(102, "Double", 2500));
        addRoom(new Room(103, "Single", 1500));
        addRoom(new Room(104, "Double", 2500));
        addRoom(new Room(105, "Suite", 4000));
    }

    static void addRoom(Room room){
        for(Room r: rooms){
            if(r.getRoomNumber() == room.getRoomNumber()){
                System.out.println("Error: Room number "+ room.getRoomNumber()+" already exists.");
                return;
            }
        }
        rooms.add(room);
    }

    static void showAvailableRooms() {
        System.out.println("\nAvailable Rooms:");
        System.out.printf("%-12s %-10s %-10s %-10s%n", "Room Number", "Type", "Price", "Booked");
        ArrayList<Room> availableRooms = new ArrayList<>();
        for (Room room : rooms) {
            if (!room.isBooked()) {
                availableRooms.add(room);
            }
        }
        Collections.sort(availableRooms,Comparator.comparingDouble(Room::getPrice));
        boolean hasAvailable = false;
        for(Room room: availableRooms){
            room.displayRoomInfo();
            hasAvailable = true;
        }
        if (!hasAvailable) {
            System.out.println("No rooms are currently available.");
        }
    }

    static void bookRoom() {
        String name;
        do{
            System.out.print("Enter your name: ");
            name = input.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("Name cannot be empty. Try Again");
            }
        } while (name.isEmpty());

        String phone;
        do{
            System.out.print("Enter your phone number (10 digits): ");
            phone = input.nextLine().trim();
            if (!phone.matches("\\d{10}")) {
                System.out.println("Invalid phone number. Please enter a 10-digit number.");
            }
        } while (!phone.matches("\\d{10}"));

        int nights;
        do {
            System.out.print("Enter number of nights: ");
            try {
                nights = input.nextInt();
                input.nextLine();
                if (nights <= 0) {
                    System.out.println("Number of nights must be positive.");
                    continue;
                }
                break;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                input.nextLine();
            }
        } while (true);

        boolean hasAvailableRoom = false;
        for (Room room : rooms) {
            if (!room.isBooked()) {
                hasAvailableRoom = true;
                break;
            }
        }

        if (!hasAvailableRoom) {
            System.out.println("All rooms are booked. Adding to waiting list.");
            waitingList.offer(new Customer(name, phone, 0, nights, 0));
            System.out.println("You have been added to the waiting list.");
            return;
        }

        showAvailableRooms();
        int roomNum;
        do{
            System.out.print("Enter room number to book: ");
            try {
                roomNum = input.nextInt();
                input.nextLine();
                break;
            } catch (Exception e) {
                System.out.println("Invalid room number.");
                input.nextLine();
            }
        } while(true);


        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNum) {
                if (!room.isBooked()) {
                    room.bookRoom();
                    double totalCost = room.getPrice() * nights;
                    customers.add(new Customer(name, phone, roomNum, nights, totalCost));
                    bookingHistory.incrementBookingCount(roomNum);
                    System.out.println("Room " + roomNum + " booked successfully for " + name + "!");
                    System.out.println("Total cost for "+nights + " nights: $" + totalCost);
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
        System.out.printf("%-12s %-10s %-10s %-10s %-20s %-15s %-10s%n", "Room Number", "Type", "Price", "Booked", "Customer Name", "Phone", "Nights");
        boolean hasBookedRooms = false;
        for (Room room : rooms) {
            if (room.isBooked()) {
                room.displayRoomInfo();
                for (Customer customer : customers) {
                    if (customer.getRoomNumber() == room.getRoomNumber()) {
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
        int roomNum;
        do {
            System.out.print("Enter room number to check out: ");
            try {
                roomNum = input.nextInt();
                input.nextLine();
                break;
            } catch (Exception e) {
                System.out.println("Invalid room number. Please enter a number.");
                input.nextLine();
            }
        } while (true);

        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNum) {
                if (room.isBooked()) {
                    System.out.print("Confirm check-out for room " + roomNum + "? (y/n): ");
                    if (input.nextLine().trim().toLowerCase().startsWith("y")) {
                        room.checkOut();
                        for (Customer customer : new ArrayList<>(customers)) {
                            if (customer.getRoomNumber() == roomNum) {
                                System.out.println("Check-out successful for " + customer.getName() + " (Room " + roomNum + ")");
                                System.out.println("Total cost: $" + customer.getTotalCost());
                                customers.remove(customer);
                                break;
                            }
                        }
                        if(!waitingList.isEmpty()){
                            Customer waitingCustomer = waitingList.poll();
                            room.bookRoom();
                            double totalCost = room.getPrice() * waitingCustomer.getNights();
                            customers.add(new Customer(waitingCustomer.getName(), waitingCustomer.getPhone(), roomNum, waitingCustomer.getNights(), totalCost));
                            bookingHistory.incrementBookingCount(roomNum);
                            System.out.println("Room " + roomNum + " booked for waiting customer: " + waitingCustomer.getName());
                            System.out.println("Total cost for " + waitingCustomer.getNights() + " nights: $" + totalCost);
                        }
                        return;
                    } else {
                        System.out.println("Check-out cancelled.");
                        return;
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
        int roomNum;
        do{
            System.out.print("Enter room number to cancel booking: ");
            try {
                roomNum = input.nextInt();
                input.nextLine();
                break;
            } catch (Exception e) {
                System.out.println("Invalid room number.");
                input.nextLine();
            }
        } while(true);


        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNum) {
                if (room.isBooked()) {
                    System.out.println("Confirm cancellation for room " + roomNum + "? (y/n): ");
                    if (input.nextLine().trim().toLowerCase().startsWith("y")) {
                        room.checkOut();
                        for (Customer customer : new ArrayList<>(customers)) {
                            if (customer.getRoomNumber() == roomNum) {
                                System.out.println("Booking cancelled for " + customer.getName() + " (Room " + roomNum + ")");
                                customers.remove(customer);
                                break;
                            }
                        }
                        if (!waitingList.isEmpty()) {
                            Customer waitingCustomer = waitingList.poll();
                            room.bookRoom();
                            double totalCost = room.getPrice() * waitingCustomer.getNights();
                            customers.add(new Customer(waitingCustomer.getName(), waitingCustomer.getPhone(), roomNum, waitingCustomer.getNights(), totalCost));
                            bookingHistory.incrementBookingCount(roomNum);
                            System.out.println("Room " + roomNum + " booked for waiting customer: " + waitingCustomer.getName());
                            System.out.println("Total cost for " + waitingCustomer.getNights() + " nights: $" + totalCost);
                        }
                        return;
                    } else {
                        System.out.println("Cancellation aborted.");
                        return;
                    }
                } else {
                    System.out.println("Room " + roomNum + " is not booked.");
                    return;
                }
            }
        }
        System.out.println("Invalid room number. No such room exists.");
    }

    static void viewBookingHistory() {
        System.out.print("Enter room number to view booking history (0 for all rooms): ");
        int roomNum;
        try {
            roomNum = input.nextInt();
            input.nextLine();
        } catch (Exception e) {
            System.out.println("Invalid input. Please enter a number.");
            input.nextLine();
            return;
        }

        if (roomNum == 0) {
            System.out.println("\nBooking History for All Rooms:");
            bookingHistory.displayAll();
        } else {
            int count = bookingHistory.getBookingCount(roomNum);
            if (count == -1) {
                System.out.println("Room " + roomNum + " does not exist or has no bookings.");
            } else {
                System.out.println("Room " + roomNum + " has been booked " + count + " time(s).");
            }
        }
    }
}