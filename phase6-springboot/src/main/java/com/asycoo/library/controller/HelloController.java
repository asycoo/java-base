package com.asycoo.library.controller;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 实操 6.1 — 第一个 REST API
 *
 * GET http://localhost:8080/api/hello
 */
@RestController
@RequestMapping("/api")
public class HelloController {

    @GetMapping("/hello")
    public Map<String, String> hello() {
        return Map.of(
                "message", "Hello Spring Boot",
                "project", "library-api"
        );
    }
}
