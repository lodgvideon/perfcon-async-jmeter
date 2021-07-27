package com.example;

import io.netty.channel.Channel;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class SessionHolder {

    private static final ConcurrentHashMap<String, Channel> sessions = new ConcurrentHashMap<>();


    public static void addSession(String key, Channel channel) {
        sessions.put(key, channel);
    }


    public static void closeSession(String key) {
        Optional.ofNullable(sessions.get(key))
                .ifPresent(c -> c.close());
        sessions.remove(key);
    }


    public static void closeAllSessions() {
        sessions.forEach((s, v) -> v.close());
        sessions.clear();

    }

    public static int getSize() {
        return sessions.size();
    }


}
