package com.bbd.licenscerenewal.utils.logging;

public class Logger {
    private ILogStrategy logStrategy;

    public Logger(ILogStrategy logStrategy) {
        this.logStrategy = logStrategy;
    }

    public void log(Object log, LogType type){
        logStrategy.log(log, type);
    }
}
