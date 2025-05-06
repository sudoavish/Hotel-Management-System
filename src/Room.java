public class Room {
    private int roomNumber;
    private String roomType;
    private boolean isBooked;
    private double price;

    private static final String[] VALID_ROOM_TYPES = {"Single", "Double", "Suite"};

    public Room(int roomNumber, String roomType, double price) {
        this.roomNumber = roomNumber;
        if(isValidRoomType(roomType)){
            this.roomType = roomType;
        } else {
            throw new IllegalArgumentException("Invalid room type: " + roomType);
        }
        this.price = price;
        this.isBooked = false;
    }
    private boolean isValidRoomType(String type){
        for(String validType: VALID_ROOM_TYPES){
            if(validType.equalsIgnoreCase(type)){
                return true;
            }
        }
        return false;
    }

    public int getRoomNumber() {
        return roomNumber;
    }
    public double getPrice() {
        return price;
    }
    public boolean isBooked() {
        return isBooked;
    }
    public void bookRoom() {
        isBooked = true;
    }
    public void checkOut() {
        isBooked = false;
    }

    public void displayRoomInfo() {
        System.out.printf("%-12d %-10s $%-9.2f %-10s%n", roomNumber, roomType, price, isBooked ? "Yes" : "No");
    }

    public void listAllRooms() {
        System.out.println("All rooms in the hotel:");
        System.out.printf("%-12s %-10s %-10s %-10s%n", "Room Number", "Type", "Price", "Booked");
        for (Room r : HotelManagementSystem.getRooms()) {
            r.displayRoomInfo();
        }
    }
}