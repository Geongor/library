package com.geongo.library.controllers;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TestRestController {
    @GetMapping("/test")
    public Map<String, String> test(){
        Map map = new HashMap();
        map.put("Test", "testMSG");
        return map;
    }
}
