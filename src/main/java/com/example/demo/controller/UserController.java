package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home(Model model) {
        List<User> allUsers = userService.getAllUsersInOrder();
        model.addAttribute("allUsers", allUsers);
        return "index.html";
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute User user, Model model) {
        userService.saveUser(user);
        model.addAttribute("successMessage", "✅ User added successfully!");
        return "redirect:/"; // Redirect to refresh the page and show updated list
    }

    @PostMapping("/fetch")
    public String fetchUser(@RequestParam String id, Model model) {
        User user = userService.getUser(id);
        if (user != null) {
            model.addAttribute("user", user);
        } else {
            model.addAttribute("errorMessage", "❌ No user found with ID: " + id);
        }
        List<User> allUsers = userService.getAllUsersInOrder();
        model.addAttribute("allUsers", allUsers);
        return "index.html";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam String id, Model model) {
        userService.deleteUser(id);
        model.addAttribute("successMessage", "🗑️ User deleted successfully!");
        List<User> allUsers = userService.getAllUsersInOrder();
        model.addAttribute("allUsers", allUsers);
        return "index.html";
    }
}
