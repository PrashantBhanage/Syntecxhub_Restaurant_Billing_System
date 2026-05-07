/**
 * Keeps all GST math in one place.
 */
public class BillCalculator {
    private static final double CGST_RATE = 0.09;
    private static final double SGST_RATE = 0.09;

    public double calculateSubtotal(Order order) {
        double subtotal = 0.0;
        for (OrderItem orderItem : order.getOrderItems()) {
            subtotal += orderItem.getTotalPrice();
        }
        return subtotal;
    }

    public double calculateCgst(double subtotal) {
        return subtotal * CGST_RATE;
    }

    public double calculateSgst(double subtotal) {
        return subtotal * SGST_RATE;
    }

    public double calculateGrandTotal(double subtotal) {
        // grand total = subtotal + both GST components
        return subtotal + calculateCgst(subtotal) + calculateSgst(subtotal);
    }
}
