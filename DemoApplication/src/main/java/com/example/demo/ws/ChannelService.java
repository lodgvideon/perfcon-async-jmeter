package com.example.demo.ws;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class ChannelService {
    private static final Map<String, Channel> channels = new ConcurrentHashMap<>();

    public synchronized static Map<String, Channel> getChannels() {
        return channels;
    }

    public static synchronized void addChannel(Channel channel) {
        channels.put(channel.id().asLongText(), channel);
    }

    public synchronized static void flush() {
        channels.clear();
    }
}
