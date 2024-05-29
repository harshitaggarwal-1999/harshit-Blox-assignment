package com.cashback.Ratelimiter.Controllers;

import com.cashback.Ratelimiter.Services.GeneratorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RandomUUIDGenerator {

    private GeneratorService generatorService;

    public RandomUUIDGenerator(GeneratorService generatorService) {
        this.generatorService = generatorService;
    }

    @GetMapping("/randomString")
    public ResponseEntity<String> nameWinner(){
        return new ResponseEntity<>(generatorService.getRandomUUID(), HttpStatus.OK);
    }
}
