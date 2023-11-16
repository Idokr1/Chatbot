# Chatbot Web Scraper
This application serves as a web scraping tool designed to extract product data, data about hotels, and the weather from websites that align with a user's specified keyword search.

Enables communication between a bot and a user using Dialogflow and Webhooks.

During the communication, Dialogflow detects the user's intentions and accordingly performs API operations from a server located in Replit and these requests make use of Scraping and Regex.

Each request represents real-time data that has been retrieved using Scraping.

## Examples:
* Amazon Products: Ask for "Products", then provide the name of a product, and the chatbot will provide you a list of items from Amazon's first page and their links.
* Yahoo- Weather: Ask "What is the weather/Weather", then provide the name of a city or state, and the chatbot will provide you with the current weather.
* Tripadvisor Hotels: Ask "Give me a list of hotels", then provide the name of a location, and the chatbot will provide you a list of hotels from Tripadvisor's first page and their links.

## Live:
[Chatbot API](https://idok-chatbot.runmydocker-app.com/swagger-ui.html) <br />
[ChatBOT API Replit](https://chatbot.idokrupik.repl.co/swagger-ui.html) <br />
[DialogFlow Chat](https://console.dialogflow.com/api-client/demo/embedded/c1cf1dc4-ba87-4c32-b191-b82259417327)

* Before running the DialogFlow Chat, Open the [Chatbot API](https://idok-chatbot.runmydocker-app.com/swagger-ui.html) and make sure the server is up and running

## Tools:
* Spring Boot
* Dialogflow
* Slack
* Docker
* Maven
* Webhook
* Scraping
* Regex
* RestAPI
