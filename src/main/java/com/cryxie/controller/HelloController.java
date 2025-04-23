package com.cryxie.controller;

import com.cryxie.utils.AppUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AppUtils.baseUrl + "/hello")

public class HelloController {

    @GetMapping
    ResponseEntity<Object> hello() {
        return ResponseEntity.ok().body("Site está no ar!");
    }
}
