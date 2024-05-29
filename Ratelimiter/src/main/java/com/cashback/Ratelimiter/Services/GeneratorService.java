package com.cashback.Ratelimiter.Services;

import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
public class GeneratorService {

    public String getRandomNumber(){
        int max = 9, min = 0;
        int cashback = new Random().nextInt(max-min+1)+min;
        return String.valueOf(cashback);
    }

    public String getRandomUUID(){
        return UUID.randomUUID().toString();
    }
}
