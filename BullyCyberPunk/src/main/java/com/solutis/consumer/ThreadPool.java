package com.solutis.consumer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {

    private final ExecutorService executor;

    private int nThreads;

    public ThreadPool(int nThreads) {
        this.nThreads = nThreads;
        this.executor = Executors.newFixedThreadPool(nThreads);
    }

    public synchronized void execute(Runnable runnable){
        nThreads--;
        executor.execute(runnable);
    }

    public synchronized void shutdown(){
        nThreads++;
    }

    public boolean waitThread(){
        return nThreads == 0;
    }
}
