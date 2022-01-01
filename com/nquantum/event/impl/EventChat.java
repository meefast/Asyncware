package com.nquantum.event.impl;

import com.nquantum.event.Event;

public class EventChat extends Event {
    public static String message;

    public EventChat(String message) {
        this.message = message;
    }

    public static String getMessage() {
        return message;
    }

    public static void setMessage(String message) {
        EventChat.message = message;
    }
}
