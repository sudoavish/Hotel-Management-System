public class Room {
    int roomNumber;
    String roomType;
    boolean isBooked;
    double price;

    public Room(int roomNumber, String roomType, double price) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.price = price;
        this.isBooked = false;
    }

    public void displayRoomInfo() {
        System.out.println("Room Number: " + roomNumber + ", Type: " + roomType + ", Price: $" + price + ", Booked: " + (isBooked ? "Yes" : "No"));
    }

}