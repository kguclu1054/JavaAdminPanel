package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;

@Controller
public class AdminController {

	
	@Autowired 
	private UserService userService;
	
    @GetMapping("/adminLogin")
    public String loginPage(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "Kullanıcı adı veya şifre yanlış!");
        }
        return "adminLogin"; 
    }
    
    

    @PostMapping("/adminLogin")
    public String loginAdmin(@RequestParam("username") String username,
                             @RequestParam("password") String password,
                             HttpSession session) {
        User user = userService.authenticateUser(username, password);
        if (user != null) {
            session.setAttribute("user", user); 
            return "redirect:/adminPaneli";
        } else {
            return "redirect:/adminLogin?error=true";
        }
    }
    
    @GetMapping("/adminPaneli")
    public String adminPaneli(HttpSession session, Model model) {
        if (session.getAttribute("user") == null) {
            return "redirect:/adminLogin"; 
        }
        model.addAttribute("username", ((User) session.getAttribute("user")).getUsername());
        return "adminPaneli"; 
    }

    

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); 
        return "redirect:/";
    }
}



