package ru.otus.grpc.server;

import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerApp {
    private static final Logger log = LoggerFactory.getLogger(ServerApp.class);

    public static void main(String[] args) throws Exception {
        var server = ServerBuilder
            .forPort(15001)
            .addService(new ServiceImpl())
            .build();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                server.shutdown();
                server.awaitTermination();
            } catch (Exception ignore) {
            }
        }));

        log.warn("START");

        server.start();
        server.awaitTermination();

        log.warn("COMPLETE");
    }
}
