package com.example;

import com.example.demo.model.DummyMessage;

import java.util.concurrent.ConcurrentHashMap;


public final class MessageHolder {
    private static final Object lock = new Object();
    public static final ConcurrentHashMap<String, DummyMessage> holder = new ConcurrentHashMap<>();


    public static DummyMessage getEvent(String key) {
        return holder.get(key);
    }

    public static void removeEvent(DummyMessage value) {
        holder.remove(value.getMessageId());

    }


    public static void put(DummyMessage callEvent) {
        holder.put(callEvent.getMessageId(), callEvent);
    }


    public static void clear() {
        holder.clear();
    }

}




