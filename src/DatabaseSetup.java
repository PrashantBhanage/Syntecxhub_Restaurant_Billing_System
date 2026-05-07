import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * First-run database setup for the restaurant menu.
 */
public class DatabaseSetup {
    public static void initialize() throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            createMenuItemsTable(connection);
            insertDefaultItemsIfEmpty(connection);
        } catch (SQLException e) {
            throw new SQLException("Database initialization failed: " + e.getMessage(), e);
        }
    }

    private static void createMenuItemsTable(Connection connection) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS menu_items ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "name VARCHAR(100) NOT NULL, "
                + "category VARCHAR(50) NOT NULL, "
                + "price DOUBLE NOT NULL"
                + ")";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        }
    }

    private static void insertDefaultItemsIfEmpty(Connection connection) throws SQLException {
        // Keeps user/admin-added menu data from being duplicated on later runs.
        if (!isMenuItemsTableEmpty(connection)) {
            return;
        }

        String sql = "INSERT INTO menu_items (name, category, price) VALUES (?, ?, ?)";
        String[][] defaultItems = {
                {"Veg Spring Rolls", "Starters", "120"},
                {"Paneer Tikka", "Starters", "220"},
                {"Chicken Tikka", "Starters", "280"},
                {"Seekh Kebab", "Starters", "260"},
                {"Paneer Butter Masala", "Main Course", "280"},
                {"Dal Makhani", "Main Course", "200"},
                {"Chicken Curry", "Main Course", "300"},
                {"Mutton Rogan Josh", "Main Course", "380"},
                {"Veg Biryani", "Rice & Biryani", "220"},
                {"Chicken Biryani", "Rice & Biryani", "320"},
                {"Steamed Rice", "Rice & Biryani", "80"},
                {"Butter Naan", "Breads", "40"},
                {"Garlic Naan", "Breads", "50"},
                {"Butter Roti", "Breads", "25"},
                {"Paratha", "Breads", "45"},
                {"Masala Dosa", "Breakfast", "120"},
                {"Idli Sambhar", "Breakfast", "90"},
                {"Poha", "Breakfast", "70"},
                {"Upma", "Breakfast", "70"},
                {"Mango Lassi", "Beverages", "80"},
                {"Cold Coffee", "Beverages", "100"},
                {"Fresh Lime Soda", "Beverages", "60"},
                {"Masala Chai", "Beverages", "30"},
                {"Gulab Jamun", "Desserts", "60"},
                {"Ice Cream 2 Scoops", "Desserts", "90"},
                {"Rasgulla", "Desserts", "60"},
                {"Chocolate Brownie", "Desserts", "120"}
        };

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (String[] item : defaultItems) {
                preparedStatement.setString(1, item[0]);
                preparedStatement.setString(2, item[1]);
                preparedStatement.setDouble(3, Double.parseDouble(item[2]));
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
    }

    private static boolean isMenuItemsTableEmpty(Connection connection) throws SQLException {
        String sql = "SELECT COUNT(*) FROM menu_items";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt(1) == 0;
            }
        }

        return true;
    }
}
