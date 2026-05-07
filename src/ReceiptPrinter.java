import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Handles the final printed bill layout.
 */
public class ReceiptPrinter {
    private static final String RESTAURANT_NAME = "Syntecxhub Restaurant";
    private BillCalculator billCalculator;

    public ReceiptPrinter(BillCalculator billCalculator) {
        this.billCalculator = billCalculator;
    }

    public void printReceipt(Order order) {
        if (order.isEmpty()) {
            System.out.println("Cannot print receipt. Order is empty.");
            return;
        }

        double subtotal = billCalculator.calculateSubtotal(order);
        double cgst = billCalculator.calculateCgst(subtotal);
        double sgst = billCalculator.calculateSgst(subtotal);
        double grandTotal = billCalculator.calculateGrandTotal(subtotal);
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));

        System.out.println();
        System.out.println("==================================================================");
        System.out.println("                    " + RESTAURANT_NAME);
        System.out.println("                    Date: " + dateTime);
        System.out.println("==================================================================");
        System.out.printf("%-8s %-22s %6s %10s %12s%n", "ID", "Item", "Qty", "Price", "Amount");
        System.out.println("------------------------------------------------------------------");

        for (OrderItem orderItem : order.getOrderItems()) {
            MenuItem item = orderItem.getMenuItem();
            System.out.printf("%-8d %-22s %6d Rs.%7.2f Rs.%9.2f%n",
                    item.getId(),
                    item.getName(),
                    orderItem.getQuantity(),
                    item.getPrice(),
                    orderItem.getTotalPrice());
        }

        System.out.println("------------------------------------------------------------------");
        System.out.printf("%-49s Rs.%9.2f%n", "Subtotal", subtotal);
        System.out.printf("%-49s Rs.%9.2f%n", "CGST 9%", cgst);
        System.out.printf("%-49s Rs.%9.2f%n", "SGST 9%", sgst);
        System.out.println("------------------------------------------------------------------");
        System.out.printf("%-49s Rs.%9.2f%n", "Grand Total", grandTotal);
        System.out.println("==================================================================");
        System.out.println("                  Thank you! Visit again.");
        System.out.println("==================================================================");
    }
}
