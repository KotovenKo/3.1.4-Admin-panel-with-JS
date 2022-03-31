package ru.kata.spring.boot_security.demo.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.Service.RoleService;
import ru.kata.spring.boot_security.demo.Service.UserService;
import javax.annotation.PostConstruct;


@Component
public class UserTester {

   @Autowired
   UserService userService;

   @Autowired
   RoleService roleService;
   @Autowired
   PasswordEncoder passwordEncoder;

   @PostConstruct
   public void crateNewUsers() {

      //Creating Roles in Database
      roleService.addRole(new Role("ROLE_ADMIN"));
      roleService.addRole(new Role("ROLE_USER"));
      Role admin = roleService.getRoleByName("ROLE_ADMIN");
      Role user = roleService.getRoleByName("ROLE_USER");

      //Creating Users with Roles with encripted password
      User tom = new User("Tom", "Cruise", "1234", 34, "cruise@gmail.com");
      tom.getRoles().add(admin);

      User bruce = new User("Bruce", "LEE", "1234",35, "lee@gmail.com");
      bruce.getRoles().add(admin);

      User chack = new User("Chack", "Norris", "1234",33, "norris@gmail.com");
      chack.getRoles().add(user);

      User sylvester = new User("Sylvester", "Stallone", "1234",55, "stallone@gmail.com");
      sylvester.getRoles().add(user);

      User keanu = new User("Keanu", "Reeves", "1234",56, "reeves@gmail.com");
      keanu.getRoles().add(user);

      //Saving Users with Roles in DataBase
      userService.addUser(tom);
      userService.addUser(bruce);
      userService.addUser(chack);
      userService.addUser(sylvester);
      userService.addUser(keanu);

   }
}
