package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.Service.UserSecurityService;
import ru.kata.spring.boot_security.demo.Service.UserService;
import ru.kata.spring.boot_security.demo.model.User;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminRestController {


    @Autowired
    private UserService userService;

    @Autowired
    private UserSecurityService userSecurityService;




    @CrossOrigin
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = userService.getUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserbyId(@PathVariable(value = "id") long id){
        User user = userService.getUser(id);
        if(user == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("")
    public ResponseEntity<?> createUser(@RequestBody User user){
        HttpHeaders headers = new HttpHeaders();
        System.out.println("Creation of user");
        System.out.println(user.getName());
        System.out.println(user.getRoles());
        if(user == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        userService.addUser(user);
        return new ResponseEntity<>(user, headers, HttpStatus.CREATED);
    }

    @CrossOrigin(allowedHeaders = "Content-Type")
    @PutMapping("/{id}")
    public ResponseEntity<?> editUser(@PathVariable long id, @RequestBody User user){
        userService.update(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @CrossOrigin(allowedHeaders = "Content-Type")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable long id){
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/registered")
    public ResponseEntity<User> getregisterUser(Principal principal){
        User registerUser = userSecurityService.findUserByName(principal.getName());
        return new ResponseEntity<>(registerUser,HttpStatus.OK);
    }







}
