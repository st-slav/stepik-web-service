package ru.mamsta.stepik.webservice.accountservice;

import ru.mamsta.stepik.webservice.dbservice.dataset.UserDataSet;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AccountService {

    private Map<UUID, UserDataSet> session = Collections.synchronizedMap(new HashMap<>());

    Lock lock = new ReentrantLock();

    public String autorization(UserDataSet user) {
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
            return session.remove(uuid) != null ? true : false;
        } finally {
            lock.unlock();
        }
    }

    public UserDataSet getAutorizationUser(String sessionId) {
        UUID uuid;
        try {
            lock.lock();
            uuid = UUID.fromString(sessionId);
        } finally {
            lock.unlock();
        }
        return session.get(uuid);
    }

    public boolean checkToken(String sessionId) {
        UUID uuid;
        try {
            lock.lock();
            uuid = UUID.fromString(sessionId);
        } finally {
            lock.unlock();
        }
        return session.containsKey(uuid);
    }
}
