package org.cefet.sd.tasks;

import java.util.concurrent.locks.ReentrantLock;

public abstract class Task {
    protected final ReentrantLock lock;

    public Task(ReentrantLock lock) {
        this.lock = lock;
    }

    public abstract void handle(String message);
}
