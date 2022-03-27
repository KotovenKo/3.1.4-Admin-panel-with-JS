package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.Service.RoleService;
import ru.kata.spring.boot_security.demo.Service.UserService;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("")
    public String lisOfUsers(Model model) {
        List<User> users = userService.getUsers();
        model.addAttribute("users", userService.getUsers());
        return "admin/listofusers";
    }

    @GetMapping("/usersbycounter")
    public String usersByCounter(@RequestParam(value = "counter") int counter, Model model) {
        model.addAttribute("users", userService.getNumberOfUsers(counter));
        return "admin/usersbycounter";
    }

    @GetMapping("/new")
    public String createUserPage(Model model) {
        model.addAttribute("user", new User());
        String stringrole = "ROLE_USER";
        model.addAttribute("stringrole", stringrole);
        return "admin/new";
    }

    @PostMapping("")
    public String createCar(@RequestParam("stringrole") String stringrole, @Valid @ModelAttribute(value = "user") User user,
                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/new";
        } else {
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            Role role = roleService.getRoleByName(stringrole);
            user.setPassword(encodedPassword);
            user.getRoles().add(role);
            userService.addUser(user);
            return "redirect:/admin";
        }
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        return "admin/edit";
    }

    @PutMapping("{id}")
    public String update( @PathVariable("id") long id, @RequestParam("stringrole") String stringrole, @ModelAttribute(value = "user") @Valid User user, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "admin/edit";
        } else {
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            Role role = roleService.getRoleByName(stringrole);
            user.setPassword(encodedPassword);
            user.getRoles().add(role);
            userService.addUser(user);
            return "redirect:/admin";
        }
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") long id) {
        userService.delete(id);
        return "redirect:/admin";
    }

}
