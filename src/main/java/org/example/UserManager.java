package org.example;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class UserManager  implements Serializable {
    private static final long serialVersionUID = 1L; // Для обеспечения совместимости версий
    private static Map<String, User> users = new HashMap<>();

    public Map<String, User> getUsers() {
        try {
            if (loadFromFile() == null || loadFromFile().isEmpty())
                users = new HashMap<>();
            else
                users = loadFromFile();
            return users;
        } catch (Exception e) {
            users = new HashMap<>();
            return users;
        }
    }

    // Сохранение пользователей в файл
    public void saveToFile() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Users"))) {
            out.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace(); // Обработка ошибок
        }
    }

    // Загрузка пользователей из файла
    public static Map<String, User> loadFromFile() throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("Users"))) {
            return (Map<String, User>) in.readObject();
        } catch (Exception e) {
            users = new HashMap<>();
            return users;
        }
    }
}
