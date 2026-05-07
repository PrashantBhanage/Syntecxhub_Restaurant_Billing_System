import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Menu data kept in sync with MySQL and the in-memory list.
 */
public class Menu {
    private ArrayList<MenuItem> items;

    public Menu() {
        items = new ArrayList<>();
        loadItemsFromDatabase();
    }

    private void loadItemsFromDatabase() {
        String sql = "SELECT id, name, category, price FROM menu_items ORDER BY id";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            items.clear();
            while (resultSet.next()) {
                items.add(new MenuItem(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("category"),
                        resultSet.getDouble("price")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error loading menu from database: " + e.getMessage());
        }
    }

    public boolean addMenuItem(MenuItem item) {
        if (findItemById(item.getId()) != null) {
            return false;
        }
        items.add(item);
        return true;
    }

    public boolean addMenuItem(String name, String category, double price) {
        String sql = "INSERT INTO menu_items (name, category, price) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, category);
            preparedStatement.setDouble(3, price);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                System.out.println("Database insert failed. No menu item was added.");
                return false;
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    items.add(new MenuItem(generatedId, name, category, price));
                    return true;
                }
            }

            System.out.println("Database insert failed. No generated ID was returned.");
        } catch (SQLException e) {
            System.out.println("Error adding menu item to database: " + e.getMessage());
        }

        return false;
    }

    public boolean removeMenuItem(int id) {
        MenuItem item = findItemById(id);
        if (item == null) {
            return false;
        }

        String sql = "DELETE FROM menu_items WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                items.remove(item);
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error removing menu item from database: " + e.getMessage());
        }

        return false;
    }

    public MenuItem findItemById(int id) {
        for (MenuItem item : items) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    public void displayMenu() {
        if (items.isEmpty()) {
            System.out.println("No menu items available.");
            return;
        }

        System.out.println();
        System.out.println("========================= RESTAURANT MENU =========================");
        System.out.printf("%-8s %-25s %-18s %10s%n", "ID", "Name", "Category", "Price");
        System.out.println("-------------------------------------------------------------------");
        for (MenuItem item : items) {
            System.out.printf("%-8d %-25s %-18s Rs.%8.2f%n",
                    item.getId(), item.getName(), item.getCategory(), item.getPrice());
        }
        System.out.println("===================================================================");
    }

    public ArrayList<MenuItem> getItems() {
        return items;
    }
}
