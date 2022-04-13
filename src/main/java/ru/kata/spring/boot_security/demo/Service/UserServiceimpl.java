package ru.kata.spring.boot_security.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.DAO.UserDAOCrudRepo;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class UserServiceimpl implements UserService {


    @Autowired
    UserDAOCrudRepo userDAOCrudRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public List<User> getUsers() {
        return userDAOCrudRepo.findAll();
    }

    @Override
    public void addUser(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userDAOCrudRepo.save(user);
    }

    @Override
    public List<User> getNumberOfUsers(int counter) {
        return userDAOCrudRepo.findFirst(counter);
    }

    @Override
    public User getUser(long id) {
        return userDAOCrudRepo.findUserById(id);
    }

    @Override
    public void update(User user) {
        if (user.getPassword().equals("")) {
            User userInDb = userDAOCrudRepo.findUserById(user.getId());
            user.setPassword(userInDb.getPassword());
            userDAOCrudRepo.save(user);
        } else {
            User userInDb = userDAOCrudRepo.findUserById(user.getId());
            String password = userInDb.getPassword();
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userDAOCrudRepo.save(user);
        }
    }


    public void delete(long id) {
        userDAOCrudRepo.delete(getUser(id));
    }
}
