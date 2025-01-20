package org.example;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

class User implements Serializable {
    private final String login;
    private final String password;
    final Map<String, Double> incomes = new HashMap<>();
    final Map<String, Double> expenses = new HashMap<>();
    private final Map<String, Double> budgets = new HashMap<>();
    User(String login, String password) {
        this.login = login;
        this.password = password;
    }
    boolean checkPassword(String password) {
        return this.password.equals(password);
    }
    void addIncome(String category, double amount) {
        incomes.merge(category, amount, Double::sum);
    }
    void addExpense(String category, double amount) {
        expenses.merge(category, amount, Double::sum);
    }
    void setBudget(String category, double budget) {
        budgets.put(category, budget);
    }
    void viewData() {
        System.out.println("Доходы:");
        incomes.forEach((category, amount) ->
                System.out.println(category + ": " + amount));
        System.out.println("Расходы:");
        expenses.forEach((category, amount) ->
                System.out.println(category + ": " + amount));
        System.out.println("Бюджеты:");
        budgets.forEach((category, budget) -> {
            double spent = expenses.getOrDefault(category, 0.0);
            System.out.println(category + ": Бюджет: " + budget + ", Потрачено: " + spent + ", Остаток: " + (budget - spent));
        });
    }
    void saveData() {
        System.out.println("Данные сохранены.");
    }
}
