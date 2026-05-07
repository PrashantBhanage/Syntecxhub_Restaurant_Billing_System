import java.util.Scanner;
import java.sql.SQLException;

/**
 * Console entry point for the billing system.
 */
public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            DatabaseSetup.initialize();
            System.out.println("✔ Connected to database successfully.");
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
            scanner.close();
            return;
        }

        Menu menu = new Menu();
        Order order = new Order();
        BillCalculator billCalculator = new BillCalculator();
        ReceiptPrinter receiptPrinter = new ReceiptPrinter(billCalculator);

        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    menu.displayMenu();
                    break;
                case 2:
                    addItemToOrder(menu, order);
                    break;
                case 3:
                    removeItemFromOrder(order);
                    break;
                case 4:
                    order.displayOrder();
                    break;
                case 5:
                    receiptPrinter.printReceipt(order);
                    if (!order.isEmpty()) {
                        order.clear();
                        System.out.println("Order completed and cleared.");
                    }
                    break;
                case 6:
                    adminMenu(menu);
                    break;
                case 0:
                    running = false;
                    System.out.println("Exiting Restaurant Billing System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    private static void printMainMenu() {
        System.out.println();
        System.out.println("========== SYNTECXHUB RESTAURANT BILLING SYSTEM ==========");
        System.out.println("1. View Menu");
        System.out.println("2. Add Item to Order");
        System.out.println("3. Remove Item from Order");
        System.out.println("4. View Current Order");
        System.out.println("5. Print Bill");
        System.out.println("6. Admin Menu Management");
        System.out.println("0. Exit");
        System.out.println("==========================================================");
    }

    private static void addItemToOrder(Menu menu, Order order) {
        if (menu.getItems().isEmpty()) {
            System.out.println("No menu items available to order.");
            return;
        }

        menu.displayMenu();
        int itemId = readInt("Enter item ID to add: ");
        MenuItem menuItem = menu.findItemById(itemId);
        if (menuItem == null) {
            System.out.println("Invalid item ID. No item added.");
            return;
        }

        int quantity = readPositiveInt("Enter quantity: ");
        order.addItem(menuItem, quantity);
        System.out.println(menuItem.getName() + " x " + quantity + " added to order.");
    }

    private static void removeItemFromOrder(Order order) {
        if (order.isEmpty()) {
            System.out.println("Cannot remove item. Current order is empty.");
            return;
        }

        order.displayOrder();
        int itemId = readInt("Enter item ID to remove from order: ");
        if (order.removeItem(itemId)) {
            System.out.println("Item removed from order.");
        } else {
            System.out.println("Item ID not found in current order.");
        }
    }

    private static void adminMenu(Menu menu) {
        boolean managing = true;
        while (managing) {
            printAdminMenu();
            int choice = readInt("Enter admin choice: ");

            switch (choice) {
                case 1:
                    addMenuItem(menu);
                    break;
                case 2:
                    removeMenuItem(menu);
                    break;
                case 3:
                    menu.displayMenu();
                    break;
                case 0:
                    managing = false;
                    break;
                default:
                    System.out.println("Invalid admin choice. Please try again.");
            }
        }
    }

    private static void printAdminMenu() {
        System.out.println();
        System.out.println("================ ADMIN MENU MANAGEMENT ================");
        System.out.println("1. Add Menu Item");
        System.out.println("2. Remove Menu Item");
        System.out.println("3. View Menu");
        System.out.println("0. Back to Main Menu");
        System.out.println("=======================================================");
    }

    private static void addMenuItem(Menu menu) {
        String name = readNonEmptyString("Enter item name: ");
        String category = readNonEmptyString("Enter item category: ");
        double price = readPositiveDouble("Enter item price: ");

        if (menu.addMenuItem(name, category, price)) {
            System.out.println("Menu item added successfully.");
        } else {
            System.out.println("Could not add item.");
        }
    }

    private static void removeMenuItem(Menu menu) {
        if (menu.getItems().isEmpty()) {
            System.out.println("No menu items available to remove.");
            return;
        }

        menu.displayMenu();
        int id = readInt("Enter item ID to remove: ");
        if (menu.removeMenuItem(id)) {
            System.out.println("Menu item removed successfully.");
        } else {
            System.out.println("Invalid item ID. No item removed.");
        }
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please enter a valid integer.");
            }
        }
    }

    private static int readPositiveInt(String prompt) {
        while (true) {
            int value = readInt(prompt);
            if (value > 0) {
                return value;
            }
            System.out.println("Value must be greater than zero.");
        }
    }

    private static double readPositiveDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                double value = Double.parseDouble(input);
                if (value > 0) {
                    return value;
                }
                System.out.println("Value must be greater than zero.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid price. Please enter a valid number.");
            }
        }
    }

    private static String readNonEmptyString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Input cannot be empty.");
        }
    }
}
