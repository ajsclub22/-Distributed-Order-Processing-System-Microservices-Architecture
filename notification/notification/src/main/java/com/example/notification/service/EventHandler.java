package com.example.notification.service;

import com.example.notification.events.NotificationEvent;

public interface EventHandler {


    void handleNotification(NotificationEvent event);
}
