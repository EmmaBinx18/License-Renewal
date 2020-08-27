package com.bbd.licenscerenewal.utils.logging;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class LogRequest implements ILogStrategy {
    @Override
    public void log(Object log, LogType type) {
        LogResourceHandler resourceHandler = LogResourceHandler.getInstance();

        if (type == LogType.COMPLETED){
            log = "(Completed Request) + (" + log + ")";
            resourceHandler.write("{Request} " + log,false);
        }
        else{
            String uri = ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString();
            resourceHandler.write("{Request} [" + uri + "] : " + log,true);
        }
    }
}
