package com.example.api.ObjectMapper;

import com.example.api.ObjectMapper.JsonParserService;
import com.example.api.ObjectMapper.Library;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/json")
public class JsonParsorController {

    @Autowired
    private JsonParserService jsonParserService;

    @PostMapping("/parse")
    public Library parseJson(@RequestBody String jsonString) throws IOException {
        try {
            return jsonParserService.parseJson(jsonString);
        }catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }
}
