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
        System.out.println(user.getRoles());
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

//    @PutMapping("{id}")
//    public String update( @PathVariable("id") long id, @RequestParam("stringrole") String stringrole, @ModelAttribute(value = "user") @Valid User user, BindingResult bindingResult) {
//        if(bindingResult.hasErrors()){
//            return "admin/edit";
//        } else {
//            Role role = roleService.getRoleByName(stringrole);
//            user.getRoles().add(role);
//            userService.update(id, user);
//            return "redirect:/admin";
//        }
//    }

//    For bootstrap modal view

    @GetMapping("/find")
    @ResponseBody
    public User findUser(Long id, Model model) {
        User user = userService.getUser(id);
        System.out.println(user.getName()+" "+ user.getRoles());
        model.addAttribute("updatedUser", user);
        return user;
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public String update ( User user){
        System.out.println("in update controller method");
        System.out.println(user.getPassword());
        System.out.println(user.getName());
        System.out.println(user.getSecondName());
        userService.update(1,user);
        return "redirect:/admin";
    }


    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") long id) {
        userService.delete(id);
        return "redirect:/admin";
    }

}
