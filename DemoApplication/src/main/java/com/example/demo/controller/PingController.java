package com.example.demo.controller;

import com.example.demo.model.DummyMessage;
import com.example.demo.ws.WsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class PingController {
    //Сервис который мы используем в примере.

    private final WsService wsService;

    public PingController(WsService wsService) {
        this.wsService = wsService;
    }

    @GetMapping("/ping")
    public void ping(@RequestParam String id) {
        long pingTime = System.currentTimeMillis();
        //Создаем DummyMessage
        DummyMessage pingMessage = new DummyMessage (id, pingTime, "New Ping Message");
        log.info("Sending Event to WS {}", pingMessage);
        //И ассинхронно отправляем уведомление.
        //Поток при этом не блокируется - так как отправка сообщений исполняется в другом пуле потоков.
        wsService.sendMessageAsync(pingMessage);
    }
}
