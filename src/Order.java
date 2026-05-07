import java.util.ArrayList;

/**
 * The customer's in-progress order.
 */
public class Order {
    private ArrayList<OrderItem> orderItems;

    public Order() {
        orderItems = new ArrayList<>();
    }

    public void addItem(MenuItem menuItem, int quantity) {
        OrderItem existingItem = findOrderItemById(menuItem.getId());
        if (existingItem != null) {
            existingItem.addQuantity(quantity);
        } else {
            orderItems.add(new OrderItem(menuItem, quantity));
        }
    }

    public boolean removeItem(int itemId) {
        OrderItem orderItem = findOrderItemById(itemId);
        if (orderItem == null) {
            return false;
        }
        orderItems.remove(orderItem);
        return true;
    }

    public OrderItem findOrderItemById(int itemId) {
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getMenuItem().getId() == itemId) {
                return orderItem;
            }
        }
        return null;
    }

    public boolean isEmpty() {
        return orderItems.isEmpty();
    }

    public void clear() {
        orderItems.clear();
    }

    public void displayOrder() {
        if (isEmpty()) {
            System.out.println("Current order is empty.");
            return;
        }

        System.out.println();
        System.out.println("========================== CURRENT ORDER ==========================");
        System.out.printf("%-8s %-25s %8s %12s %12s%n", "ID", "Item", "Qty", "Price", "Total");
        System.out.println("------------------------------------------------------------------");
        for (OrderItem orderItem : orderItems) {
            MenuItem item = orderItem.getMenuItem();
            System.out.printf("%-8d %-25s %8d Rs.%8.2f Rs.%8.2f%n",
                    item.getId(),
                    item.getName(),
                    orderItem.getQuantity(),
                    item.getPrice(),
                    orderItem.getTotalPrice());
        }
        System.out.println("==================================================================");
    }

    public ArrayList<OrderItem> getOrderItems() {
        return orderItems;
    }
}
