package com.izicash.system;

import com.izicash.system.util.log.LogConnection;
import com.izicash.system.util.log.LogService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class LogApplication implements ApplicationRunner {

    public static ArrayList<LogConnection> logsConnections = new ArrayList<>();

    @Value("${app.name}")
    private String systemName;

    @Value("${app.version}")
    private String systemVersion;

    @Value("${app.build}")
    private String systemBuild;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        LogService.info("Aplicação: " + systemName);
        LogService.info("Versão: " + systemVersion);
        LogService.info("Build: " + systemBuild);
        LogService.info("Executando Logs!");
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            try {
                ServerSocket logsServerSocket = new ServerSocket(2020);
                LogService.info("Aguadando conexoes de logs na porta 2020!");
                while(true) {
                    Socket logClient = logsServerSocket.accept();
                    String ipLog = logClient.getInetAddress().getHostAddress();
                    LogService.info("Nova conexao de log: " + ipLog + "!");
                    LogConnection logConnection = new LogConnection(logClient);
                    ExecutorService ex = Executors.newSingleThreadExecutor();
                    ex.submit(logConnection);
                }
            } catch (IOException e) {
                LogService.error(Arrays.toString(e.getStackTrace()));
            }
        });
    }
}
