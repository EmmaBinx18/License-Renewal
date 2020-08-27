package com.bbd.licenscerenewal.utils.logging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LogSQL implements ILogStrategy {
    @Override
    public void log(Object log, LogType type) {

        ObjectMapper objectMapper = new ObjectMapper();

        LogResourceHandler resourceHandler = LogResourceHandler.getInstance();

        if (type == LogType.PARAMETERS){

            try {
                log = objectMapper.writeValueAsString(log);

            } catch (JsonProcessingException ex) {
                log = "Internal Error : "+ ex.getMessage();
            }

            log = "(Parameter/s) " + log;
        }

        if (type == LogType.RESPONSE){

            try {
                log = objectMapper.writeValueAsString(log);

            } catch (JsonProcessingException ex) {
                log =  "Internal Error : "+ ex.getMessage();
            }

            log = "(Result/s) " + log;
        }

        if (type == LogType.QUERY){
            log = "(Statement) " + log;
        }

        if (type == LogType.COMPLETED){
            log = "(Completed Query)";
        }

        resourceHandler.write("{SQL} " + log, false);
    }
}
