package com.bookshop.warehouse.controllers.Health;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthController {

    @GetMapping()
    @ResponseBody
    public ResponseEntity<String> health(){
        return new ResponseEntity<>("Application is Running", HttpStatus.OK);
    }
}
