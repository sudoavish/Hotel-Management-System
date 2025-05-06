public class Customer {
    private String name;
    private String phone;
    private int roomNumber;
    private int nights;
    private double totalCost;

    public Customer(String name, String phone, int roomNumber, int nights, double totalCost) {
        this.name = name;
        this.phone = phone;
        this.roomNumber = roomNumber;
        this.nights = nights;
        this.totalCost = totalCost;
    }

    public String getName() {
        return name;
    }
    public String getPhone() {
        return phone;
    }
    public int getRoomNumber() {
        return roomNumber;
    }
    public int getNights() {
        return nights;
    }
    public double getTotalCost() {
        return totalCost;
    }

    public void displayCustomerInfo() {
        System.out.printf("%-12s %-10s %-10s %-10d $%-9.2f%n", "", "", "", nights, totalCost);
    }
    public void listAllCustomers() {
        System.out.println("All customers in the hotel:");
        System.out.printf("%-12s %-20s %-15s %-10s %-10s%n", "Room Number", "Name", "Phone", "Nights", "Total Cost");
        for (Customer c : HotelManagementSystem.getCustomers()) {
            System.out.printf("%-12d %-20s %-15s %-10d $%-9.2f%n", c.roomNumber, c.name, c.phone, c.nights, c.totalCost);
        }
    }
}