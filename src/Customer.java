public class Customer {
    String name;
    String phone;
    int roomNumber;

    public Customer(String name, String phone, int roomNumber) {
        this.name = name;
        this.phone = phone;
        this.roomNumber = roomNumber;
    }

    public void displayCustomerInfo() {
        System.out.println("Name: " + name + ", Phone: " + phone + ", Room Number: " + roomNumber);
    }

}