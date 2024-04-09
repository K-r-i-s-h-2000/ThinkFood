package com.thinkpalm.thinkfood.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/think-food/demo-controller")
@SecurityRequirement(name="thinkfood")
public class DemoController {
    @GetMapping
    public ResponseEntity<String>sayHello(){
        return ResponseEntity.ok("hello from the secured endpoint");

    }
}
