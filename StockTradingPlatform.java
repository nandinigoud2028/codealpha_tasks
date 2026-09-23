import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

// ---------------- STOCK CLASS ----------------
class Stock {
    private String symbol;
    private String companyName;
    private double price;

    public Stock(String symbol, String companyName, double price) {
        this.symbol = symbol;
        this.companyName = companyName;
        this.price = price;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getCompanyName() {
        return companyName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void displayStock() {
        System.out.println(
            symbol + " | " + companyName + " | Price: $" +
            String.format("%.2f", price)
        );
    }
}

// ---------------- TRANSACTION CLASS ----------------
class Transaction {
    private String type;
    private String stockSymbol;
    private int quantity;
    private double price;

    public Transaction(String type, String stockSymbol, int quantity, double price) {
        this.type = type;
        this.stockSymbol = stockSymbol;
        this.quantity = quantity;
        this.price = price;
    }

    public void displayTransaction() {
        System.out.println(
            type + " | Stock: " + stockSymbol +
            " | Quantity: " + quantity +
            " | Price: $" + String.format("%.2f", price) +
            " | Total: $" + String.format("%.2f", quantity * price)
        );
    }
}

// ---------------- PORTFOLIO CLASS ----------------
class Portfolio {
    // Stores stock symbol and quantity
    private HashMap<String, Integer> stocks;

    public Portfolio() {
        stocks = new HashMap<>();
    }

    // Add shares to portfolio
    public void addStock(String symbol, int quantity) {
        int currentQuantity = stocks.getOrDefault(symbol, 0);
        stocks.put(symbol, currentQuantity + quantity);
    }

    // Remove shares from portfolio
    public boolean removeStock(String symbol, int quantity) {
        int currentQuantity = stocks.getOrDefault(symbol, 0);

        if (currentQuantity < quantity) {
            return false;
        }

        if (currentQuantity == quantity) {
            stocks.remove(symbol);
        } else {
            stocks.put(symbol, currentQuantity - quantity);
        }

        return true;
    }

    // Get quantity of a particular stock
    public int getQuantity(String symbol) {
        return stocks.getOrDefault(symbol, 0);
    }

    // Display portfolio
    public void displayPortfolio(HashMap<String, Stock> market) {
        System.out.println("\n========== MY PORTFOLIO ==========");

        if (stocks.isEmpty()) {
            System.out.println("Portfolio is empty.");
            return;
        }

        double totalValue = 0;

        for (String symbol : stocks.keySet()) {
            int quantity = stocks.get(symbol);
            Stock stock = market.get(symbol);

            double value = quantity * stock.getPrice();
            totalValue += value;

            System.out.println(
                symbol + " | Quantity: " + quantity +
                " | Current Price: $" + String.format("%.2f", stock.getPrice()) +
                " | Value: $" + String.format("%.2f", value)
            );
        }

        System.out.println("----------------------------------");
        System.out.println(
            "Total Portfolio Value: $" +
            String.format("%.2f", totalValue)
        );
    }

    // Calculate total portfolio value
    public double getPortfolioValue(HashMap<String, Stock> market) {
        double total = 0;

        for (String symbol : stocks.keySet()) {
            int quantity = stocks.get(symbol);
            Stock stock = market.get(symbol);

            total += quantity * stock.getPrice();
        }

        return total;
    }
}

// ---------------- USER CLASS ----------------
class User {
    private String name;
    private double cash;
    private Portfolio portfolio;
    private ArrayList<Transaction> transactions;

    // Used to track portfolio value over time
    private ArrayList<Double> portfolioHistory;

    public User(String name, double initialCash) {
        this.name = name;
        this.cash = initialCash;
        this.portfolio = new Portfolio();
        this.transactions = new ArrayList<>();
        this.portfolioHistory = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public double getCash() {
        return cash;
    }

    // ---------------- BUY STOCK ----------------
    public void buyStock(Stock stock, int quantity) {

        if (quantity <= 0) {
            System.out.println("Quantity must be greater than 0.");
            return;
        }

        double totalCost = stock.getPrice() * quantity;

        if (cash < totalCost) {
            System.out.println("Not enough money.");
            return;
        }

        cash = cash - totalCost;

        portfolio.addStock(stock.getSymbol(), quantity);

        Transaction transaction = new Transaction(
            "BUY",
            stock.getSymbol(),
            quantity,
            stock.getPrice()
        );

        transactions.add(transaction);

        System.out.println("\nStock purchased successfully!");
        System.out.println("Total cost: $" + String.format("%.2f", totalCost));
    }

    // ---------------- SELL STOCK ----------------
    public void sellStock(Stock stock, int quantity) {

        if (quantity <= 0) {
            System.out.println("Quantity must be greater than 0.");
            return;
        }

        boolean success =
            portfolio.removeStock(stock.getSymbol(), quantity);

        if (!success) {
            System.out.println("You do not have enough shares.");
            return;
        }

        double totalMoney = stock.getPrice() * quantity;

        cash = cash + totalMoney;

        Transaction transaction = new Transaction(
            "SELL",
            stock.getSymbol(),
            quantity,
            stock.getPrice()
        );

        transactions.add(transaction);

        System.out.println("\nStock sold successfully!");
        System.out.println("Money received: $" +
            String.format("%.2f", totalMoney));
    }

    // ---------------- DISPLAY ACCOUNT ----------------
    public void displayAccount(HashMap<String, Stock> market) {

        double portfolioValue =
            portfolio.getPortfolioValue(market);

        double totalAccountValue =
            cash + portfolioValue;

        System.out.println("\n========== ACCOUNT ==========");
        System.out.println("User: " + name);
        System.out.println(
            "Cash: $" + String.format("%.2f", cash)
        );
        System.out.println(
            "Portfolio Value: $" +
            String.format("%.2f", portfolioValue)
        );
        System.out.println(
            "Total Account Value: $" +
            String.format("%.2f", totalAccountValue)
        );
    }

    // ---------------- DISPLAY TRANSACTIONS ----------------
    public void displayTransactions() {

        System.out.println("\n========== TRANSACTION HISTORY ==========");

        if (transactions.isEmpty()) {
            System.out.println("No transactions yet.");
            return;
        }

        for (Transaction transaction : transactions) {
            transaction.displayTransaction();
        }
    }

    // ---------------- SAVE PORTFOLIO VALUE ----------------
    public void savePortfolioValue(HashMap<String, Stock> market) {

        double portfolioValue =
            portfolio.getPortfolioValue(market);

        double totalValue = cash + portfolioValue;

        portfolioHistory.add(totalValue);
    }

    // ---------------- DISPLAY PERFORMANCE ----------------
    public void displayPerformance() {

        System.out.println("\n========== PORTFOLIO PERFORMANCE ==========");

        if (portfolioHistory.isEmpty()) {
            System.out.println("No performance data available.");
            return;
        }

        for (int i = 0; i < portfolioHistory.size(); i++) {

            System.out.println(
                "Record " + (i + 1) +
                ": $" +
                String.format("%.2f", portfolioHistory.get(i))
            );
        }

        if (portfolioHistory.size() >= 2) {

            double firstValue = portfolioHistory.get(0);
            double lastValue =
                portfolioHistory.get(portfolioHistory.size() - 1);

            double change = lastValue - firstValue;

            System.out.println("----------------------------------");
            System.out.println(
                "Change: $" + String.format("%.2f", change)
            );
        }
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }
}

// ---------------- MAIN CLASS ----------------
public class StockTradingPlatform {

    static Scanner scanner = new Scanner(System.in);

    static HashMap<String, Stock> market =
        new HashMap<>();

    static User user;

    public static void main(String[] args) {

        // Create sample stocks
        market.put(
            "AAPL",
            new Stock("AAPL", "Apple Inc.", 180.00)
        );

        market.put(
            "GOOG",
            new Stock("GOOG", "Google", 140.00)
        );

        market.put(
            "AMZN",
            new Stock("AMZN", "Amazon", 155.00)
        );

        market.put(
            "MSFT",
            new Stock("MSFT", "Microsoft", 400.00)
        );

        market.put(
            "TSLA",
            new Stock("TSLA", "Tesla", 250.00)
        );

        // Create user
        user = new User("Student", 10000);

        // Save initial account value
        user.savePortfolioValue(market);

        System.out.println("======================================");
        System.out.println("       STOCK TRADING PLATFORM");
        System.out.println("======================================");
        System.out.println("Welcome, " + user.getName() + "!");
        System.out.println("Starting Cash: $10,000");

        int choice;

        do {

            displayMenu();

            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {

                case 1:
                    displayMarket();
                    break;

                case 2:
                    buyStock();
                    break;

                case 3:
                    sellStock();
                    break;

                case 4:
                    user.getPortfolio().displayPortfolio(market);
                    break;

                case 5:
                    user.displayAccount(market);
                    break;

                case 6:
                    user.displayTransactions();
                    break;

                case 7:
                    updateStockPrices();
                    break;

                case 8:
                    user.savePortfolioValue(market);
                    System.out.println(
                        "Portfolio value saved."
                    );
                    break;

                case 9:
                    user.displayPerformance();
                    break;

                case 10:
                    System.out.println(
                        "Thank you for using the Stock Trading Platform!"
                    );
                    break;

                default:
                    System.out.println(
                        "Invalid choice. Please try again."
                    );
            }

        } while (choice != 10);

        scanner.close();
    }

    // ---------------- MENU ----------------
    public static void displayMenu() {

        System.out.println("\n========== MENU ==========");
        System.out.println("1. View Market Data");
        System.out.println("2. Buy Stock");
        System.out.println("3. Sell Stock");
        System.out.println("4. View Portfolio");
        System.out.println("5. View Account");
        System.out.println("6. View Transaction History");
        System.out.println("7. Update Stock Prices");
        System.out.println("8. Save Portfolio Performance");
        System.out.println("9. View Portfolio Performance");
        System.out.println("10. Exit");
        System.out.println("==========================");
    }

    // ---------------- MARKET DATA ----------------
    public static void displayMarket() {

        System.out.println("\n========== MARKET DATA ==========");

        for (Stock stock : market.values()) {
            stock.displayStock();
        }
    }

    // ---------------- BUY FUNCTION ----------------
    public static void buyStock() {

        scanner.nextLine();

        System.out.print("Enter stock symbol: ");
        String symbol = scanner.nextLine().toUpperCase();

        Stock stock = market.get(symbol);

        if (stock == null) {
            System.out.println("Stock not found.");
            return;
        }

        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();

        user.buyStock(stock, quantity);
    }

    // ---------------- SELL FUNCTION ----------------
    public static void sellStock() {

        scanner.nextLine();

        System.out.print("Enter stock symbol: ");
        String symbol = scanner.nextLine().toUpperCase();

        Stock stock = market.get(symbol);

        if (stock == null) {
            System.out.println("Stock not found.");
            return;
        }

        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();

        user.sellStock(stock, quantity);
    }

    // ---------------- UPDATE PRICES ----------------
    public static void updateStockPrices() {

        System.out.println("\n========== UPDATE PRICES ==========");

        for (Stock stock : market.values()) {

            double oldPrice = stock.getPrice();

            // Generate a random price change
            double change =
                (Math.random() * 20) - 10;

            double newPrice = oldPrice + change;

            // Prevent negative price
            if (newPrice < 1) {
                newPrice = 1;
            }

            stock.setPrice(newPrice);

            System.out.println(
                stock.getSymbol() +
                " price changed from $" +
                String.format("%.2f", oldPrice) +
                " to $" +
                String.format("%.2f", newPrice)
            );
        }

        System.out.println("Market prices updated!");
    }
}

