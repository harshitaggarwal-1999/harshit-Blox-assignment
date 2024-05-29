package com.cashback.Ratelimiter.Controllers;

import com.cashback.Ratelimiter.Services.GeneratorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class RandomNumberGenerator {

    private GeneratorService generatorService;

    public RandomNumberGenerator(GeneratorService generatorService) {
        this.generatorService = generatorService;
    }

    @GetMapping("/randomNumber")
    public ResponseEntity<String> getCashBack() {
        return new ResponseEntity<>(generatorService.getRandomNumber(), HttpStatus.OK);
    }
}
