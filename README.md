# 🍽️ Syntecxhub Restaurant Billing System

A console-based **Restaurant Billing System** built in Java with full **MySQL database integration**. Supports dynamic menu management, order processing, GST-based bill calculation, and itemized receipt generation.

---

## 📌 Features

- 📋 View full menu fetched directly from MySQL database
- 🛒 Place orders by selecting items and quantities
- ➕ Add new menu items dynamically (saved to DB)
- ❌ Remove menu items dynamically (deleted from DB)
- 🧾 Generate itemized receipt with CGST (9%) + SGST (9%)
- 💾 All menu data persists in MySQL — nothing is hardcoded
- ✅ Input validation throughout the app

---

## 🗂️ Project Structure

```
Syntecxhub_Restaurant_Billing_System/
├── src/
│   ├── Main.java               # Entry point, console menu loop
│   ├── MenuItem.java           # Model for a single menu item
│   ├── Menu.java               # Loads/manages menu from DB
│   ├── Order.java              # Manages customer's current order
│   ├── OrderItem.java          # Single item in an order with quantity
│   ├── BillCalculator.java     # Subtotal, CGST, SGST, Grand Total
│   ├── ReceiptPrinter.java     # Formats and prints the receipt
│   ├── DatabaseConnection.java # JDBC connection to MySQL
│   └── DatabaseSetup.java      # Creates tables and seeds default data
├── lib/
│   └── mysql-connector-j-9.2.0.jar
└── out/                        # Compiled .class files
```

---

## 🛠️ Tech Stack

| Technology | Usage |
|---|---|
| Java | Core language |
| MySQL | Data persistence |
| JDBC | Java-MySQL connectivity |
| MySQL Workbench | DB management GUI |

---

## ⚙️ Setup & Installation

### Prerequisites
- Java JDK 8 or above
- MySQL Server running on `localhost:3306`
- `mysql-connector-j-9.2.0.jar` placed in `lib/` folder

### 1. Clone the repository
```bash
git clone https://github.com/PrashantBhanage/Syntecxhub_Restaurant_Billing_System.git
cd Syntecxhub_Restaurant_Billing_System
```

### 2. Configure Database
Make sure MySQL is running and update credentials in `DatabaseConnection.java` if needed:
```java
String url  = "jdbc:mysql://localhost:3306/syntecxhub_restaurant?createDatabaseIfNotExist=true";
String user = "root";
String pass = "your_password";
```

### 3. Compile
```bash
javac -cp ".:lib/mysql-connector-j-9.2.0.jar" src/*.java -d out/
```

### 4. Run
```bash
java -cp ".:out/:lib/mysql-connector-j-9.2.0.jar" Main
```

> **Windows users:** Replace `:` with `;` in the classpath

---

## 🖥️ Console Menu

```
========== WELCOME TO SYNTECXHUB RESTAURANT ==========
1. View Menu
2. Place Order
3. Remove Item from Order
4. View Current Order
5. Generate Bill & Receipt
6. Add Menu Item  (Admin)
7. Remove Menu Item  (Admin)
0. Exit
```

---

## 🧾 Sample Receipt

```
======================================================
           SYNTECXHUB RESTAURANT
         Date: 07-05-2026  Time: 14:32
======================================================
 #   Item                  Qty   Price      Total
------------------------------------------------------
 1   Paneer Butter Masala   2    ₹280.00   ₹560.00
 2   Garlic Naan            4     ₹50.00   ₹200.00
 3   Mango Lassi            2     ₹80.00   ₹160.00
------------------------------------------------------
 Subtotal:                                ₹920.00
 CGST  (9%):                               ₹82.80
 SGST  (9%):                               ₹82.80
------------------------------------------------------
 GRAND TOTAL:                            ₹1085.60
======================================================
      Thank you for dining with us! Visit Again!
======================================================
```

---

## 🗄️ Database Schema

```sql
CREATE TABLE menu_items (
    id       INT AUTO_INCREMENT PRIMARY KEY,
    name     VARCHAR(100) NOT NULL,
    category VARCHAR(50)  NOT NULL,
    price    DOUBLE       NOT NULL
);
```

---

## 👨‍💻 Author

**Prashant Bhanage**  
[GitHub](https://github.com/PrashantBhanage)

---

## 📄 License

This project is for educational purposes under **Syntecxhub**.
