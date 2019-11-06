package com.izicash.system.util.log;


import com.izicash.system.LogApplication;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Arrays;

public class LogConnection implements Runnable {
    protected Socket log;
    protected PrintStream logPrintStream;

    public LogConnection(Socket client){
        this.log = client;
    }
    @Override
    public void run() {
        try {
            System.out.println("Configurando conexao de log!");
            logPrintStream = new PrintStream(log.getOutputStream());
            LogApplication.logsConnections.add(LogConnection.this);
            logPrintStream.print("Aguardando logs...\n");
        } catch (IOException e) {
            LogService.error(Arrays.toString(e.getStackTrace()));
        }
        return;
    }
    private boolean isConnected(){
        if(!this.log.isConnected()){
            LogApplication.logsConnections.remove(log);
            return false;
        }
        return true;
    }
    public void sendInfoLog(String time, String log) {
        if(this.isConnected()) {
            try {
                logPrintStream.print(time + " - INFOR: " + log + "\n");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void sendErrorLog(String time, String log) {
        if(this.isConnected()) {
            try {
                logPrintStream.print(time + " - ERROR: " + log  + "\n");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
