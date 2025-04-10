package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.service.EmailService;

@Controller
public class EmailController {

	@Autowired
	private EmailService emailService;

	@PostMapping("/sendEmail")
	public String sendEmailForm(
	        @RequestParam String isim,
	        @RequestParam String email,
	        @RequestParam String mesaj
	) {
	    String subject = "İletişim Formu - " + isim;
	    String text = "Gönderen: " + isim + "\nEmail: " + email + "\nMesaj:\n" + mesaj;

	    emailService.sendMessage("kguclu1054@gmail.com", subject, text);
	    
	    return "redirect:/success"; 
	}
	
	@GetMapping("/success")
    public String successPage() {
        return "message-success";
    }

}
