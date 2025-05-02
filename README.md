# 💼 Java CLI Accounting Application

This is a **Java-based Command Line Accounting Application** built as a capstone project for the **Year Up United App Development program**.

It allows users to:

- Record financial transactions
- View spending history
- Generate reports (Month-to-Date, Year-to-Date, etc.)

All data is stored in a **CSV file**, simulating a basic ledger system.

The goal of this project is to demonstrate how Java can be used to build **simple, file-based applications** 
that solve real-world problems like tracking money.

---

## 📷 Application Screenshots

### 🧭 Main Menu
This is the main entry point of the application:

![Main Menu](<Screenshot%202025-05-01%20151603-1.png>)

---

### ➕ Add Deposit
User interface to add a new deposit:

![Add Deposit](<Screenshot%202025-05-01%20151735.png>)

---

### 📂 Accounting and Ledger Menu
This menu gives access to viewing and managing transaction history:

![Other Menus](<Screenshot%202025-05-01%20151751.png>)

---

### 📒 Ledger Screens
Viewing different sets of transactions:

- **All Transactions**
  ![Both Deposits and Payments](<Screenshot%202025-05-01%20151833.png>)

- **Only Deposits**
  ![Only Deposits](<Screenshot%202025-05-01%20152016.png>)

- **Only Payments**
  ![Only Payments](<Screenshot%202025-05-01%20152030.png>)

---

### 📊 Reports Menu
Where users can generate various reports:

![Reports Menu](<Screenshot%202025-05-01%20152042.png>)

#### 📅 Month to Date
![Month to Date](<Screenshot%202025-05-02%20091718.png>)

#### 📆 Previous Month
![Previous Month](<Screenshot%202025-05-01%20152055.png>)

#### 📈 Year to Date
![Year to Date](<Screenshot%202025-05-02%20091738.png>)

#### 📉 Previous Year
![Previous Year](<Screenshot%202025-05-02%20091753.png>)

#### 🔍 Search by Vendor Name
![Vendor Name](<Screenshot%202025-05-02%20091807.png>)

---

## ✨ Interesting Piece of Code

![Interesting Piece](<image-1-1.png>)

I found this piece of code especially interesting because it **condenses two blocks of logic into one**, 
using a **boolean `isDeposit`** value to decide whether the user is entering a deposit or a payment.

Instead of duplicating code for each option, this logic handles both cases in a clean and efficient way:

```java
if (isDeposit) {
    addTransaction("Add deposit", description, vendor, amount);
} else {
    addTransaction("Add payment", description, vendor, amount);
}







