package com.example.usermanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin(origins = "http://localhost:8081" , allowCredentials = "true")
public class UsermanagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(UsermanagementApplication.class, args);
    }

}
