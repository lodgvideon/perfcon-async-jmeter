package com.example.demo.controller;

import com.example.demo.DummyMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {

    @GetMapping("/ping")
    public void ping(){
        long pingTime = System.currentTimeMillis();
        DummyMessage pingMessage = DummyMessage.of(pingTime, "New Ping Message");

    }
}
