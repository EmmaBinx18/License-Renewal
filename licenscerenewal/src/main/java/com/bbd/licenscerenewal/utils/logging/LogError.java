package com.bbd.licenscerenewal.utils.logging;

public class LogError implements ILogStrategy {
    @Override
    public void log(Object log, LogType type) {
        LogResourceHandler resourceHandler = LogResourceHandler.getInstance();
        resourceHandler.write("Error -->" + log,false);
    }
}
