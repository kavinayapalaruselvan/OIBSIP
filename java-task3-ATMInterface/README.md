ATM Interface
About the Project
ATM Interface is a console-based ATM simulation application developed using Java as part of the Oasis Infobyte Java Development Internship.

The application simulates the basic functionality of an Automated Teller Machine (ATM). Users can securely authenticate using their User ID and PIN and perform common banking operations such as checking their balance, depositing money, withdrawing money, transferring funds, and viewing transaction history.

The project is designed using Object-Oriented Programming (OOP) principles with multiple Java classes to provide a structured and maintainable application.

Features
User ID and PIN authentication
Maximum of 3 incorrect PIN attempts
Secure access control
ATM main menu
Balance inquiry
Cash deposit
Cash withdrawal
Fund transfer
Transaction history
Transaction tracking
Input validation
Account management
Logout functionality
User-friendly console interface
ATM Operations
After successful authentication, the user can access the following operations:

1. Transaction History
Displays the user's previous banking transactions.

2. Balance Inquiry
Displays the current available account balance.

3. Deposit
Allows the user to deposit money into their account.

4. Withdrawal
Allows the user to withdraw money from their account after checking the available balance.

5. Transfer
Allows the user to transfer money to another account.

6. Change PIN
Allows the user to change their PIN.

7. Logout
Allows the user to securely exit the ATM session.

Authentication
The application provides a secure login mechanism using:

User ID
PIN
The user is allowed a maximum of 3 incorrect PIN attempts. Access is denied if the user exceeds the maximum number of attempts.

Technologies Used
Java
Object-Oriented Programming (OOP)
Java Collections
Console-based Application
OOP Concepts Used
The project demonstrates several important Object-Oriented Programming concepts, including:

Classes and Objects
Encapsulation
Inheritance
Abstraction
Methods
Constructors
Data management using objects
Project Structure
The project contains the following Java files:

Account.java
Manages account-related information such as account details and balance.

ATM.java
Handles the main ATM functionality and user interaction.

Bank.java
Manages bank accounts and banking-related operations.

Transaction.java
Represents and stores transaction details such as deposits, withdrawals, and transfers.

Main.java
Contains the main method and starts the Smart ATM application.

How To Run
Open the project folder in Command Prompt or Terminal.

Compile the files:
javac*.java

Run the program:
java Main

Project Structure
SmartAtm/
│
├── Account.java
├── ATM.java
├── Bank.java
├── Main.java
├── Transaction.java
├── README.md
│
├── Screenshot 1.png
├── Screenshot 2.png
├── Screenshot 3.png
├── Screenshot 4.png
└── ...
Internship
Oasis Infobyte-Java Development Internship
Task 3:ATM Interface

Author
Kavinaya P
