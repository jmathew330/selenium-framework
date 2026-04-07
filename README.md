# Selenium Test Automation Framework (Java)

A scalable and maintainable **UI automation framework** built using **Java, Selenium WebDriver, TestNG, and Allure Reports**, following **Page Object Model (POM)** best practices.  
This project automates core user flows including **login, product inventory, cart management, and checkout validation**.

---

## 🚀 Tech Stack

- **Language:** Java  
- **UI Automation:** Selenium WebDriver  
- **Test Framework:** TestNG  
- **Design Pattern:** Page Object Model (POM)  
- **Reporting:** Allure Reports  
- **Logging:** Log4j2  
- **Driver Management:** WebDriverManager  
- **Build Tool:** Maven  

---

## 📂 Project Structure

```
src/
├─ main/
│  ├─ java/
│  │  ├─ base/
│  │  │  ├─ BaseTest.java
│  │  │  └─ BasePage.java
│  │  ├─ pages/
│  │  │  ├─ LoginPage.java
│  │  │  ├─ InventoryPage.java
│  │  │  ├─ CartPage.java
│  │  │  ├─ CheckoutInfoPage.java
│  │  │  ├─ CheckoutOverviewPage.java
│  │  │  └─ CheckoutCompletePage.java
│  │  └─ utils/
│  │     ├─ ConfigReader.java
│  │     └─ LoginTestDataReader.java
│
├─ test/
│  └─ java/
│     └─ tests/
│        ├─ LoginTests.java
│        ├─ ProductsTests.java
│        └─ CheckoutTests.java
│
└─ resources/
   ├─ config.properties
   └─ testdata/
```
---

## 🧠 Framework Design Highlights

- **Centralized browser setup** via `BaseTest`
- **Reusable page-level actions** via `BasePage`
- **Explicit waits** handled in base layer
- **Clean separation** of test logic and UI logic
- **Configuration-driven execution** (browser, headless mode, waits, etc.)
- **Rich Allure reporting** with epics, features, stories, and severity levels
- **Data-driven testing** using TestNG `@DataProvider`

---

## 🧪 Test Coverage

### 🔐 Login Module
- Valid user login
- Locked-out user validation
- Invalid credentials
- Empty username/password validation

### 🛒 Products / Inventory Module
- Inventory page load validation
- Product name & price verification
- Sorting (A–Z, Z–A, price low–high, high–low)
- Add/remove single & multiple products
- Cart badge count verification

### 💳 Checkout Module
- End-to-end checkout flow
- Checkout form validation
- Missing required field validation
- Final confirmation message verification
