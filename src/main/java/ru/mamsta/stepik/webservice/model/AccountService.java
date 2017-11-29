package ru.mamsta.stepik.webservice.model;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AccountService {

    private Map<UUID, User> session = Collections.synchronizedMap(new HashMap<>());
    private Map<String, User> users = Collections.synchronizedMap(new HashMap<>());

    Lock lock = new ReentrantLock();

    public void registerUser(User user) {
        users.put(user.getLogin(), user);
    }

    public boolean containsUser(String login) {
        return users.containsKey(login);
    }

    public User getUser(String login) {
        return users.get(login);
    }

    public String autorization(User user) {
        try {
            lock.lock();
            UUID uuid = null;
            while (true) {
                uuid = UUID.randomUUID();
                if (!session.containsKey(uuid)) {
                    session.put(uuid, user);
                    break;
                }
            }
            return uuid.toString();
        } finally {
            lock.unlock();
        }
    }

    public boolean unautorisation(String sessionId) {
        try {
            lock.lock();
            UUID uuid = UUID.fromString(sessionId);
            return session.remove(sessionId) != null ? true : false;
        } finally {
            lock.unlock();
        }
    }

    public User getAutorizationUser(String sessionId) {
        UUID uuid;
        try {
            lock.lock();
            uuid = UUID.fromString(sessionId);
        } finally {
            lock.unlock();
        }
        return session.get(uuid);
    }
}
