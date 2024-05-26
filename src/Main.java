import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Base class for all products
class Product {
    private int productId;
    private String name;
    private double price;
    private int quantityInStock;

    // Constructor to initialize product attributes
    public Product(int productId, String name, double price, int quantityInStock) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantityInStock = quantityInStock;
    }

    // Getters and setters for product attributes
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    // Method to update stock quantity
    public void updateStock(int quantity) {
        this.quantityInStock += quantity;
    }

    // Method to get product details
    public String getDetails() {
        return "Product ID: " + productId + ", Name: " + name + ", Price: " + price + ", Quantity in stock: " + quantityInStock;
    }
}

// Subclass for perishable products
class PerishableProduct extends Product {
    private LocalDate expiryDate;

    // Constructor to initialize perishable product attributes
    public PerishableProduct(int productId, String name, double price, int quantityInStock, LocalDate expiryDate) {
        super(productId, name, price, quantityInStock);
        this.expiryDate = expiryDate;
    }

    // Getter and setter for expiry date
    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    // Overridden method to include expiry date in product details
    @Override
    public String getDetails() {
        return super.getDetails() + ", Expiry Date: " + expiryDate;
    }
}

// Subclass for non-perishable products
class NonPerishableProduct extends Product {
    private int warrantyPeriod; // in months

    // Constructor to initialize non-perishable product attributes
    public NonPerishableProduct(int productId, String name, double price, int quantityInStock, int warrantyPeriod) {
        super(productId, name, price, quantityInStock);
        this.warrantyPeriod = warrantyPeriod;
    }

    // Getter and setter for warranty period
    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    // Overridden method to include warranty period in product details
    @Override
    public String getDetails() {
        return super.getDetails() + ", Warranty Period: " + warrantyPeriod + " months";
    }
}

// Class to manage inventory of products
class Inventory {
    private List<Product> products;

    // Constructor to initialize the products list
    public Inventory() {
        this.products = new ArrayList<>();
    }

    // Method to add a product to the inventory
    public void addProduct(Product product) {
        products.add(product);
    }

    // Method to update product details
    public void updateProduct(int productId, String name, double price, int quantityInStock) {
        Product product = findProductById(productId);
        if (product != null) {
            product.setName(name);
            product.setPrice(price);
            product.setQuantityInStock(quantityInStock);
        } else {
            System.out.println("Product with ID " + productId + " not found.");
        }
    }

    // Method to view all products in the inventory
    public void viewAllProducts() {
        for (Product product : products) {
            System.out.println(product.getDetails());
        }
    }

    // Method to update stock quantity for a product
    public void updateStock(int productId, int quantity) {
        Product product = findProductById(productId);
        if (product != null) {
            product.updateStock(quantity);
        } else {
            System.out.println("Product with ID " + productId + " not found.");
        }
    }

    // Helper method to find a product by its ID
    private Product findProductById(int productId) {
        for (Product product : products) {
            if (product.getProductId() == productId) {
                return product;
            }
        }
        return null;
    }
}

// Main class to run the inventory management system
public class Main {
    public static void main(String[] args) {
        Inventory inventory = new Inventory();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        // Loop to display menu and get user input
        while (!exit) {
            System.out.println("Inventory Management System:");
            System.out.println("1. Add Product");
            System.out.println("2. Update Product");
            System.out.println("3. View All Products");
            System.out.println("4. Update Stock");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            // Switch case to handle user choice
            switch (choice) {
                case 1:
                    System.out.print("Enter product type (1 for Perishable, 2 for Non-Perishable): ");
                    int type = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    System.out.print("Enter product ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    System.out.print("Enter product name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter product price: ");
                    double price = scanner.nextDouble();
                    System.out.print("Enter product quantity in stock: ");
                    int quantity = scanner.nextInt();
                    scanner.nextLine(); // consume newline

                    // Add perishable or non-perishable product based on user input
                    if (type == 1) {
                        System.out.print("Enter expiry date (yyyy-mm-dd): ");
                        LocalDate expiryDate = LocalDate.parse(scanner.nextLine());
                        inventory.addProduct(new PerishableProduct(id, name, price, quantity, expiryDate));
                    } else {
                        System.out.print("Enter warranty period (in months): ");
                        int warranty = scanner.nextInt();
                        inventory.addProduct(new NonPerishableProduct(id, name, price, quantity, warranty));
                    }
                    break;

                case 2:
                    System.out.print("Enter product ID to update: ");
                    int updateId = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    System.out.print("Enter new product name: ");
                    String newName = scanner.nextLine();
                    System.out.print("Enter new product price: ");
                    double newPrice = scanner.nextDouble();
                    System.out.print("Enter new product quantity in stock: ");
                    int newQuantity = scanner.nextInt();
                    scanner.nextLine(); // consume newline

                    // Update product details
                    inventory.updateProduct(updateId, newName, newPrice, newQuantity);
                    break;

                case 3:
                    // View all products in the inventory
                    inventory.viewAllProducts();
                    break;

                case 4:
                    System.out.print("Enter product ID to update stock: ");
                    int stockId = scanner.nextInt();
                    System.out.print("Enter quantity to update: ");
                    int stockQuantity = scanner.nextInt();
                    scanner.nextLine(); // consume newline

                    // Update stock quantity for a product
                    inventory.updateStock(stockId, stockQuantity);
                    break;

                case 5:
                    // Exit the program
                    exit = true;
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }
}
