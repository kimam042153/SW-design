package service;

import model.User;

import java.util.List;

public class UserService {

    public User login(String userId, String password) {
        List<User> users = DataStore.getUsers();
        for (User u : users) {
            if (u.getUserId().equals(userId)) {
                if (!u.getPassword().equals(password)) {
                    throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
                }
                return u;
            }
        }
        throw new IllegalArgumentException("존재하지 않는 계정입니다.");
    }

    public User register(String userId, String password, String name) {
        List<User> users = DataStore.getUsers();
        for (User u : users) {
            if (u.getUserId().equals(userId)) {
                throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
            }
        }
        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException("비밀번호는 8자 이상이어야 합니다.");
        }
        User newUser = new User(userId, password, name, "STUDENT");
        users.add(newUser);
        return newUser;
    }
}
