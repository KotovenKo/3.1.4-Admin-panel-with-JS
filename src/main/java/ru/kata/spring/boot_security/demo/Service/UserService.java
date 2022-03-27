package ru.kata.spring.boot_security.demo.Service;


import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {

    List<User> getUsers();

    void addUser(User user);

    List<User> getNumberOfUsers(int counter);

    User getUser(long id);

    void update(long id, User user);

    void delete(long id);

}
