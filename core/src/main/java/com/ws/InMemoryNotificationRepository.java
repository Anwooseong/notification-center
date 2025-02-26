package com.ws;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryNotificationRepository implements NotificationRepository {

    private final Map<String, Notification> store = new ConcurrentHashMap<>();

    @Override
    public Optional<Notification> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Notification save(Notification notification) {
        return store.put(notification.id, notification);
    }

    @Override
    public Notification deleteById(String id) {
        return store.remove(id);
    }
}
