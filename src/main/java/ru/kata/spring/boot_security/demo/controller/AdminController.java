package ru.kata.spring.boot_security.demo.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/index")
public class AdminController {

    @GetMapping("")
    public String lisOfUsers() {
        return "admin/listofusers";
    }
}
