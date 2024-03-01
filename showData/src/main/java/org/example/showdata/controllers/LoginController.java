package org.example.showdata.controllers;

import jakarta.servlet.http.HttpSession;
import org.example.showdata.entites.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;


@Controller
@RequestMapping("/showData")
public class LoginController {


    @GetMapping
    public String loginPage() {
        return "login";
    }


    @PostMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, Model model, HttpSession session) {
        RestTemplate restTemplate = new RestTemplate();
        User user = new User(username, password);

        ResponseEntity<Boolean> response = restTemplate.postForEntity("http://authentication-service:8081/authentication/login", user, Boolean.class);

        boolean isValid = Boolean.TRUE.equals(response.getBody());

        if (isValid) {
            session.setAttribute("validUser", true);
            return "redirect:/retrieveData";
        }
        model.addAttribute("errorMessage", "Invalid Credentials , Please Try Again.");
        return "login";
    }

    @PostMapping("/logout")
    public String logOut(HttpSession session){
        session.invalidate();
        return "login";
    }
}
