package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.Service.UserSecurityService;
import ru.kata.spring.boot_security.demo.model.User;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserSecurityService userSecurityService;

    @GetMapping("")
    public String show(Principal principal, Model model) {
        String name = principal.getName();
        User user = userSecurityService.findUserByName(name);

        model.addAttribute("user", user);
        return "user/user_page";
    }
}
