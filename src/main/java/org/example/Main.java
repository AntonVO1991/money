package org.example;
import java.util.*;

public class Main {
    static UserManager um = new UserManager();
    private static final Map<String,  User> users = um.getUsers();
    private static  User currentUser = null;
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                if (currentUser == null) {
                    System.out.println("Введите команду, по выбору (login, register, exit):");
                    String command =
                            scanner.nextLine().trim().toLowerCase();
                    switch (command) {
                        case "register":
                            registerUser(scanner);
                            break;
                        case "login":
                            loginUser(scanner);
                            break;
                        case "exit":
                            return;
                        default:
                            System.out.println("Неизвестная команда.");
                    }
                } else {
                    System.out.println("Введите команду, по выбору (add-expense, set-budget, add-income, view-data, logout, exit):");
                    String command = scanner.nextLine().trim().toLowerCase();
                    switch (command) {
                        case "add-income":
                            addIncome(scanner);
                            break;
                        case "add-expense":
                            addExpense(scanner);
                            break;
                        case "set-budget":
                            setBudget(scanner);
                            break;
                        case "view-data":
                            viewData();
                            break;
                        case "logout":
                            logoutUser();
                            break;
                        case "exit":
                            saveData();
                            return;
                        default:
                            System.out.println("Неизвестная команда.");
                    }
                }
            }
        }
    }
    private static void registerUser(Scanner scanner) {
        System.out.println("Введите логин:");
        String login = scanner.nextLine().trim();
        if (users.containsKey(login)) {
            System.out.println("Пользователь с таким логином уже существует.");
            return;
        }
        System.out.println("Введите пароль:");
        String password = scanner.nextLine().trim();
        users.put(login, new User(login, password));
        System.out.println("Пользователь успешно зарегистрирован.");
    }
    private static void loginUser(Scanner scanner) {
        System.out.println("Введите логин:");
        String login = scanner.nextLine().trim();
        System.out.println("Введите пароль:");
        String password = scanner.nextLine().trim();
        User user = users.get(login);
        if (user != null && user.checkPassword(password)) {
            currentUser = user;
            System.out.println("Успешный вход.");
        } else {
            System.out.println("Неверный логин или пароль.");
        }
    }
    private static void logoutUser() {
        saveData();
        currentUser = null;
        System.out.println("Вы вышли из аккаунта.");
    }
    private static void addIncome(Scanner scanner) {
        System.out.println("Введите категорию дохода:");
        String category = scanner.nextLine().trim();
        System.out.println("Введите сумму дохода:");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Очистка буфера
        currentUser.addIncome(category, amount);
        System.out.println("Доход добавлен.");
    }
    private static void addExpense(Scanner scanner) {
        System.out.println("Введите категорию расхода:");
        String category = scanner.nextLine().trim();
        System.out.println("Введите сумму расхода:");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Очистка буфера
        if(!andCheckDataState(amount)) {
            System.out.println("Нельзя. Ваши расходы больше доходов");
        } else {
            currentUser.addExpense(category, amount);
            System.out.println("Расход добавлен.");
        }
    }
    private static boolean andCheckDataState(double amount) {
        double totalincomesSum = currentUser.incomes.values().stream().reduce(0.0, Double::sum);
        double totalexpensesSum = amount + currentUser.expenses.values().stream().reduce(0.0, Double::sum);
        if(totalincomesSum < totalexpensesSum)
            return false;
        return true;
    }
    private static void setBudget(Scanner scanner) {
        System.out.println("Введите категорию:");
        String category = scanner.nextLine().trim();
        System.out.println("Введите сумму бюджета:");
        double budget = scanner.nextDouble();
        scanner.nextLine(); // Очистка буфера
        currentUser.setBudget(category, budget);
        System.out.println("Бюджет установлен.");
    }
    private static void viewData() {
        currentUser.viewData();
    }
    private static void saveData() {
        if (currentUser != null) {
            currentUser.saveData();
            um.saveToFile();
        }
    }
}