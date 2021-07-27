package com.example.demo.ws;

import com.example.demo.model.DummyMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

@Service
public class WsService {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final static Logger LOG = LoggerFactory.getLogger(WsService.class);
    private final ExecutorService commonThreadPoolExecutors;
    private static final CloseWebSocketFrame closeWebSocketFrame = new CloseWebSocketFrame();


    public WsService(ExecutorService commonThreadPoolExecutors) {
        this.commonThreadPoolExecutors = commonThreadPoolExecutors;
    }

    public CompletableFuture sendMessageAsync(DummyMessage message) {
        return CompletableFuture.runAsync(() -> {
            sendMessage(message);
        }, commonThreadPoolExecutors);
    }

    public void sendMessage(List<DummyMessage> messages) {
        messages.forEach(this::sendMessageAsync);
    }

    public void sendMessage(DummyMessage message) {
        try {
            sendMessage(objectMapper.writeValueAsString(message));
        } catch (JsonProcessingException e) {
            LOG.error(ExceptionUtils.getMessage(e));
        }
    }

    public void sendMessage(String message) {
        Map<String, Channel> channels = ChannelService.getChannels();
        List<Channel> filteredChannels = channels.values().stream().filter(Channel::isActive).collect(Collectors.toList());
        filteredChannels.forEach(channel -> channel.writeAndFlush(new TextWebSocketFrame(message)));
    }

    public void sendMessage(String id, String message) {
        Map<String, Channel> channels = ChannelService.getChannels();
        Optional<Channel> channel = Optional.ofNullable(channels.get(id));
        channel.ifPresent(ch -> ch.writeAndFlush(new TextWebSocketFrame(message)));
    }

    public Map<String, String> getChannels() {
        return ChannelService.getChannels()
                .values().stream()
                .collect(Collectors.toMap(ch -> ch.id().asLongText(), ch -> String.valueOf(ch.isActive())));
    }


    public void flushChannels() {
        ChannelService.getChannels().values()
                .forEach(c -> {
                    if (c.isActive() && c.isOpen()) {
                        c.writeAndFlush(closeWebSocketFrame);
                        c.close();
                    }
                });
        ChannelService.flush();
    }
}
