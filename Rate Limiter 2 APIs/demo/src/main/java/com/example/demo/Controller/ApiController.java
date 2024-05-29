package com.example.demo.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    @GetMapping("/api1/random-number")
    public String getRandomNumber() {
        int randomNumber = (int) (Math.random() * 100);
        return "Random Number: " + randomNumber;
    }

    @GetMapping("/api2/random-string")
    public String getRandomString() {
        String randomString = java.util.UUID.randomUUID().toString();
        return "Random String: " + randomString;
    }
}

