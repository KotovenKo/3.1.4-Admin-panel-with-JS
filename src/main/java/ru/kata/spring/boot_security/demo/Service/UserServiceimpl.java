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
    public void update(long id, User user) {
        System.out.println("in update service method");
        System.out.println(user.getId());
        System.out.println(user.getPassword());
        System.out.println(user.getName());
        System.out.println(user.getSecondName());
        if (user.getPassword().equals("")) {
            System.out.println("in update with old password");
            User userInDb = userDAOCrudRepo.findUserById(user.getId());
            user.setPassword(userInDb.getPassword());
            userDAOCrudRepo.save(user);
        } else {
            User userInDb = userDAOCrudRepo.findUserById(user.getId());
            String password = userInDb.getPassword();
            if (passwordEncoder.matches(password, user.getPassword())) {

                System.out.println("in update service method with new password");
//            String encodedPassword = passwordEncoder.encode(user.getPassword());
//            user.setPassword(encodedPassword);
                userDAOCrudRepo.save(user);
            }
        }
    }

    public void delete(long id) {
        userDAOCrudRepo.delete(getUser(id));
    }
}
