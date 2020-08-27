package com.bbd.licenscerenewal.utils.logging;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

public class LogResourceHandler {

    String FilePath = "Events.log";

    private static LogResourceHandler instance = null;
    private UUID CorrelationId = null;

    private LogResourceHandler() { }

    public static LogResourceHandler getInstance() {

        if (instance == null){
            instance = new LogResourceHandler();
        }
        return instance;
    }

    public void write(String data, boolean status){

        File logs = new File(FilePath);

        try {
            FileWriter fw = new FileWriter(logs, true);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.newLine();

            if(data.length() > 500){
                data = data.substring(0, Math.min(data.length(), 500)) + "...";
            }

            bw.write("["+ getUUID(status) + "]" + "[" + new Date() + "]" + data);

            System.out.println("["+ getUUID(status) + "]" + "[" + new Date() + "]" + data);
            bw.close();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }

    }

    private UUID getUUID(boolean status){

        if(CorrelationId == null || status){
            CorrelationId = UUID.randomUUID();
        }

        return CorrelationId;
    }
}
