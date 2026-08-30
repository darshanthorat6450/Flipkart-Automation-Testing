# 🛒 Flipkart Automation Testing

A Selenium WebDriver based automation testing project for automating the Flipkart shopping workflow using **Java, Selenium WebDriver, Maven, TestNG, and Page Object Model (POM)**.

The project demonstrates automated browser testing of an e-commerce application, including login, product search, product selection, cart operations, and navigation toward the checkout/payment stage.

---

## 📸 Project Screenshots

### Flipkart Automation Execution
_Add screenshots of the automated browser execution here._

### TestNG Results
_Add TestNG execution/report screenshot here._

---

## 📌 Project Overview

This project is designed to automate the major steps of an online shopping workflow on Flipkart.

The automation framework follows the **Page Object Model (POM)** design pattern to make the test code modular, reusable, maintainable, and easier to manage.

### Automated Workflow

```text
Launch Browser
      ↓
Open Flipkart
      ↓
Login
      ↓
Search for Product
      ↓
Select Product
      ↓
Add Product to Cart
      ↓
Open Cart
      ↓
Proceed to Checkout
      ↓
Navigate toward Payment
```

> ⚠️ The project does not perform an actual payment transaction.

---

## ✨ Features

- 🌐 Automated browser testing using Selenium WebDriver
- 🔐 Flipkart login automation
- 🔎 Product search automation
- 👟 Product selection
- 🛒 Add product to cart
- 📦 Cart validation/navigation
- 💳 Navigation toward checkout/payment page
- ⏳ Explicit waits using `WebDriverWait`
- 🧩 Page Object Model architecture
- 🧪 Test execution using TestNG
- 📦 Maven dependency management
- 🔄 Reusable page classes
- 📝 TestNG test suite configuration
- 💻 Designed for maintainable automation testing

---

## 🛠️ Technologies Used

| Technology | Purpose |
|---|---|
| Java | Programming language |
| Selenium WebDriver | Browser automation |
| TestNG | Test framework |
| Maven | Dependency and project management |
| Page Object Model | Test architecture/design pattern |
| WebDriverWait | Explicit synchronization |
| ChromeDriver | Chrome browser automation |
| Git & GitHub | Version control |

---

## 🏗️ Project Architecture

The project uses the **Page Object Model (POM)** design pattern. Each major page/functionality is represented by a separate Java class.

```text
automate_testing
│
├── src
│   └── test
│       └── java
│           └── darshan
│               │
│               ├── LoginPage.java
│               ├── HomePage.java
│               ├── SearchPage.java
│               ├── ProductPage.java
│               ├── CartPage.java
│               ├── CheckoutPage.java
│               ├── FlipkartTest.java
│               └── Test.java
│
├── pom.xml
├── testng.xml
├── .gitignore
└── README.md
```

---

## 📂 Page Classes

### 🔐 LoginPage.java
Responsible for handling the Flipkart login functionality.

**Responsibilities:**
- Opening the login interface
- Entering the mobile number
- Handling login-related elements
- Supporting OTP-based authentication

### 🏠 HomePage.java
Handles operations performed on the Flipkart home page.

**Responsibilities:**
- Navigating through the home page
- Accessing the search functionality
- Performing home-page related interactions

### 🔎 SearchPage.java
Handles product search operations.

**Responsibilities:**
- Entering product search keywords
- Submitting the search
- Handling search results

Example: `Search → Shoes`

### 🛍️ ProductPage.java
Handles interaction with the selected product.

**Responsibilities:**
- Selecting a product
- Opening product details
- Interacting with product-related controls
- Adding the product to the cart

### 🛒 CartPage.java
Handles shopping cart operations.

**Responsibilities:**
- Opening the cart
- Verifying cart navigation
- Managing cart-related interactions
- Proceeding toward checkout

### 💳 CheckoutPage.java
Handles the checkout stage.

**Responsibilities:**
- Navigating from cart to checkout
- Handling checkout-related elements
- Navigating toward the payment stage

> No actual payment is performed.

### 🧪 FlipkartTest.java
Acts as the main TestNG test class. It coordinates the complete automation workflow:

```text
Login → Home → Search → Product → Cart → Checkout
```

---

## 🧪 Testing Framework

This project uses **TestNG** for test execution. TestNG provides:

- Test case management
- Test execution
- Assertions
- Test suite configuration
- Test reports
- Integration with Maven

The test suite is configured using `testng.xml`.

---

## ⏳ Synchronization

The framework uses Selenium's `WebDriverWait` to synchronize browser interactions with dynamically loaded web elements.

```java
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
wait.until(ExpectedConditions.elementToBeClickable(element));
```

Using explicit waits helps reduce failures caused by elements not being immediately available.

---

## 📦 Maven

The project uses Maven for dependency management. The main project configuration is available in `pom.xml`.

To download dependencies:

```bash
mvn clean install
```

---

## 🚀 How to Run the Project

### 1. Clone the Repository
```bash
git clone https://github.com/darshanthorat6450/Flipkart-Automation-Testing.git
```

### 2. Open the Project
Open the project in:
- Eclipse
- IntelliJ IDEA
- VS Code

Make sure Maven dependencies are downloaded successfully.

### 3. Configure WebDriver
Make sure Chrome and the required ChromeDriver/Selenium setup are available. If the project uses a local ChromeDriver path, update the driver configuration according to your system.

### 4. Run the Test
You can run the TestNG suite using:
```bash
testng.xml
```

Alternatively, run the main TestNG class:
```bash
FlipkartTest.java
```

---

## 🔑 Login and OTP

Flipkart may require OTP verification during login. The automation framework can pause for OTP input when required.

> **For security reasons:** Do not store your mobile number, password, OTP, or other credentials directly in the GitHub repository.

---

## 📊 Test Flow

| Step | Test Action |
|---|---|
| 1 | Launch Chrome |
| 2 | Open Flipkart |
| 3 | Login |
| 4 | Handle OTP if required |
| 5 | Search for shoes |
| 6 | Select a product |
| 7 | Add product to cart |
| 8 | Open cart |
| 9 | Proceed to checkout |
| 10 | Navigate toward payment page |
| 11 | Stop before actual payment |

---

## 🎯 Project Objectives

- Understand Selenium WebDriver automation
- Implement the Page Object Model
- Automate an e-commerce workflow
- Learn TestNG-based test execution
- Implement explicit waits
- Manage dependencies using Maven
- Build a maintainable automation framework
- Practice real-world web application testing

---

## 📈 Future Enhancements

- [ ] Data-driven testing
- [ ] Parameterized TestNG tests
- [ ] Extent Reports
- [ ] Screenshot capture on test failure
- [ ] Logging using Log4j/SLF4J
- [ ] Cross-browser testing
- [ ] Selenium Grid
- [ ] CI/CD using GitHub Actions
- [ ] Docker-based test execution
- [ ] Environment/configuration files
- [ ] Improved test data management

---

## 🧑‍💻 Skills Demonstrated

- Java
- Selenium WebDriver
- TestNG
- Maven
- Page Object Model
- WebDriverWait / Explicit Waits
- Web Automation
- Functional Testing
- Git & GitHub

---

## ⚠️ Disclaimer

This project is created for **educational and testing purposes**. It does not perform real purchases or actual payment transactions.

The automation behavior may change if Flipkart modifies its website structure, UI, locators, authentication flow, or security mechanisms.

---

## 👨‍💻 Author

**Darshan Thorat**
Computer Engineering Student
GitHub: [darshanthorat6450](https://github.com/darshanthorat6450)

---

## ⭐ If You Like This Project

If you find this project useful for learning Selenium automation, consider supporting it:

- ⭐ Star the repository
- 🍴 Fork the repository
- 🐛 Report issues
- 💡 Suggest improvements
