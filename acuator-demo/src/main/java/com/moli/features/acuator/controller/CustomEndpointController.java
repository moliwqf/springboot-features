package com.moli.features.acuator.controller;

import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.web.annotation.WebEndpoint;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @author moli
 * @time 2024-08-01 16:00:48
 * @description 自定义endpoint，配置完，需要enable
 */
@RestController("custom")
@WebEndpoint(id = "date")
public class CustomEndpointController {

    @ReadOperation
    public ResponseEntity<String> currentDate() {
        return ResponseEntity.ok(LocalDateTime.now().toString());
    }
}
