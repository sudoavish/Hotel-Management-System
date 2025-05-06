public class BookingHistory {
    private class Node{
        int roomNumber;
        int bookingCount;
        Node left;
        Node right;

        Node (int roomNumber){
            this.roomNumber = roomNumber;
            this.bookingCount = 1;
            this.left = this.right = null;
        }
    }

    private Node root;

    public BookingHistory(){
        root = null;
    }

    public void incrementBookingCount(int roomNumber){
        root = insert(root, roomNumber);
    }

    private Node insert(Node node, int roomNumber){
        if(node == null){
            return new Node(roomNumber);
        }

        if(roomNumber < node.roomNumber){
            node.left = insert(node.left, roomNumber);
        } else if(roomNumber > node.roomNumber){
            node.right = insert(node.right, roomNumber);
        } else {
            node.bookingCount++;
        }
        return node;
    }
    public int getBookingCount(int roomNumber) {
        Node node = search(root, roomNumber);
        return node == null ? -1 : node.bookingCount;
    }

    private Node search(Node node, int roomNumber) {
        if (node == null || node.roomNumber == roomNumber) {
            return node;
        }
        if (roomNumber < node.roomNumber) {
            return search(node.left, roomNumber);
        }
        return search(node.right, roomNumber);
    }

    public void displayAll() {
        System.out.printf("%-12s %-10s%n", "Room Number", "Bookings");
        inOrder(root);
    }

    private void inOrder(Node node) {
        if (node != null) {
            inOrder(node.left);
            System.out.printf("%-12d %-10d%n", node.roomNumber, node.bookingCount);
            inOrder(node.right);
        }
    }
}
