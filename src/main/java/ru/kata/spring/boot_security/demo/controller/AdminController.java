package ru.kata.spring.boot_security.demo.controller;


import com.fasterxml.jackson.annotation.JsonIgnoreType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.Service.RoleService;
import ru.kata.spring.boot_security.demo.Service.UserSecurityService;
import ru.kata.spring.boot_security.demo.Service.UserService;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.model.UserRoles;

import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private UserSecurityService userSecurityService;


    @GetMapping("")
    public String lisOfUsers(Model model, Principal principal) {
        User registeredUser = userSecurityService.findUserByName(principal.getName());
        List<User> users = userService.getUsers();
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("registeredUser", registeredUser);
        return "admin/listofusers";
    }

    @GetMapping("/usersbycounter")
    public String usersByCounter(@RequestParam(value = "counter") int counter, Model model) {
        model.addAttribute("users", userService.getNumberOfUsers(counter));
        return "admin/usersbycounter";
    }

    @GetMapping("/new")
    public String createUserPage(Model model, Principal principal) {
        User registeredUser = userSecurityService.findUserByName(principal.getName());
        model.addAttribute("user", new User());
        Set<Role> roles = roleService.getRoles();
        System.out.println(roles);
        model.addAttribute("listOfRoles", roles);
        model.addAttribute("registeredUser", registeredUser);
        return "admin/new";
    }

    @PostMapping("")
    public String createCar(Model model, Principal principal, @Valid @ModelAttribute(value = "user") User user,
                            BindingResult bindingResult) {
        User registeredUser = userSecurityService.findUserByName(principal.getName());
        if (bindingResult.hasErrors()) {
            model.addAttribute("registeredUser", registeredUser);
            return "admin/new";
        } else {

            userService.addUser(user);
            return "redirect:/admin";
        }
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        return "admin/edit";
    }

    //    For bootstrap modal view
    @GetMapping("/find")
    @ResponseBody
    public UserRoles findUser(Long id, Model model) {
        User foundUser = userService.getUser(id);
        Set<Role> allRoles = roleService.getRoles();
        return new UserRoles(allRoles, foundUser);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public String update(@RequestParam(value = "roleForm") String roleForm, User user) {
        if (roleForm.contains("ROLE_USER")) {
            user.getRoles().add(roleService.getRoleByName("ROLE_USER"));
        }
        if (roleForm.contains("ROLE_ADMIN")) {
            user.getRoles().add(roleService.getRoleByName("ROLE_ADMIN"));
        }
        userService.update(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/delete")
    public String delete(User user, Principal principal) {
        User egisteredUser = userSecurityService.findUserByName(principal.getName());
        if (user.getId() == egisteredUser.getId()) {
            userService.delete(user.getId());
            return "redirect:/login";
        }
        userService.delete(user.getId());
        return "redirect:/admin";
    }

}
