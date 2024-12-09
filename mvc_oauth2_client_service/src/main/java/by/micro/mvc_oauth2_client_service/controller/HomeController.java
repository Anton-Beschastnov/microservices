package by.micro.mvc_oauth2_client_service.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {
    @Value("${config.name}")
    private String name;
    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String admin(Principal principal, Model model) {
        System.out.println(name);
        model.addAttribute("username", principal.getName());
        return "admin";
    }

    @GetMapping("/user")
    public String user(Principal principal, Model model) {
        model.addAttribute("username", principal.getName());
        return "user";
    }
}
