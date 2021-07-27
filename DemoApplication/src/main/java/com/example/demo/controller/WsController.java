package com.example.demo.controller;

import com.example.demo.ws.WsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
public class WsController {
    private final WsService wsService;

    @Autowired
    public WsController(WsService wsService) {
        this.wsService = wsService;
    }

    @RequestMapping(value = "/ws/send", method = RequestMethod.GET)
    public void sendMessage(@RequestParam(name = "id", required = false) Optional<String> id,
                            @RequestParam(name = "message", required = true) String message) {
        if (id.isPresent()) {
            wsService.sendMessage(id.get(), message);
        } else {
            wsService.sendMessage(message);
        }
    }

    @RequestMapping(value = "/ws/get", method = RequestMethod.GET)
    public Map<String, String> getChannels() {
        return wsService.getChannels();
    }

    @RequestMapping(value = "/ws/flush", method = RequestMethod.GET)
    public void flushChannels() {
        wsService.flushChannels();
    }
}
