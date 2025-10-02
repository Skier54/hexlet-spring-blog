package hexlet.code.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    @Value("${app.welcome-message}")
    private String welcomeMessage;

    @Value("${app.admin-email}")
    private String adminEmail;

    @GetMapping("/welcome")
    public String welcome() {
        return welcomeMessage;
    }

    @GetMapping("/admin_email")
    public String getEmail() {
        return adminEmail;
    }
}
