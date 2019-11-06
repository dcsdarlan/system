package com.izicash.system.util.log;

import com.izicash.system.LogApplication;
import org.apache.log4j.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogService {

    private static Logger logger = Logger.getLogger(LogService.class);

    public static void info(String info) {
        logger.info(info);
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = new Date();
        String now = dateFormat.format(date);

        for(LogConnection logConnection : LogApplication.logsConnections) {
            logConnection.sendInfoLog(now, info);
        }
    }

    public static void error(String erro) {
        logger.info(erro);
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = new Date();
        String now = dateFormat.format(date);

        for(LogConnection logConnection : LogApplication.logsConnections) {
            logConnection.sendErrorLog(now, erro);
        }
    }
}
