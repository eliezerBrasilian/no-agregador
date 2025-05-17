package com.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.app.utils.AppUtils;

@RestController
@RequestMapping(AppUtils.baseUrl + "/hello")

public class HelloController {

    @GetMapping
    ResponseEntity<Object> hello() {
        return ResponseEntity.ok().body("Site está no ar!");
    }
}
