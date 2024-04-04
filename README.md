# Steam Portfolio App
This project is a Spring Boot application, which allows the user to search for items on the Steam Marketplace, check their prices and make an portfolio. The data for this project is taken from the Steam Marketplace API and saved into a local PostgreSQL database.

The app is usable using Postman for now, I'm planning to do a small UI later on.
# Current Features
- gather information about all the items currently listed on Steam Market and save them into a PostgreSQL database
- check current price, median price and sell volume of items in multiple currencies
- create a portfolio, which allows you to track item quantities, price of the item at the time of the buy, allowing you to check current price, as well as volume of items sold and quantity of the item currently listed on the marketplace
- ability to create multiple portfolios

# Planned Features
- add an endpoint with historical portfolio values, allowing the user to check previous portfolio values, as well as get the price difference
- add ability to go back to previous portfolio snapshots to check prices of items at that time
- add ability to automatically scan inventory and get inventory value
- add an UI to make the app more accessible

# Note
Please note that the API I'm using has a pretty strict call limit for no-error answers, at about 5 requests per minute, which makes the application run pretty slowly. At a 20 item portfolio, the wait time is 8 minutes. I can't really do much about it at the current stage, since raising the request limit locks the application out of usage for some time.
