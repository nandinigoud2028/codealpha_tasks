# Hardcoded stock prices
stock_prices = {
    "AAPL": 180,
    "TSLA": 250,
    "GOOGL": 140,
    "MSFT": 420,
    "AMZN": 190
}

total_investment = 0

print("===== STOCK PORTFOLIO TRACKER =====")

# Number of stocks
n = int(input("Enter the number of stocks: "))

# Store portfolio details
portfolio = []

for i in range(n):
    stock = input("\nEnter stock name (AAPL/TSLA/GOOGL/MSFT/AMZN): ").upper()
    quantity = int(input("Enter quantity: "))

    if stock in stock_prices:
        price = stock_prices[stock]
        investment = price * quantity
        total_investment += investment

        portfolio.append((stock, quantity, price, investment))

        print("Stock Price:", price)
        print("Investment:", investment)

    else:
        print("Stock not found in the price list.")

# Display summary
print("\n===== PORTFOLIO SUMMARY =====")

for stock, quantity, price, investment in portfolio:
    print("Stock:", stock)
    print("Quantity:", quantity)
    print("Price:", price)
    print("Investment:", investment)
    print("----------------------")

print("Total Investment Value:", total_investment)

# Save result to a text file
with open("portfolio.txt", "w") as file:
    file.write("===== STOCK PORTFOLIO SUMMARY =====\n")

    for stock, quantity, price, investment in portfolio:
        file.write(f"Stock: {stock}\n")
        file.write(f"Quantity: {quantity}\n")
        file.write(f"Price: {price}\n")
        file.write(f"Investment: {investment}\n")
        file.write("----------------------\n")

    file.write(f"Total Investment Value: {total_investment}\n")

print("\nPortfolio saved successfully in portfolio.txt")