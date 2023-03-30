package ru.otus.grpc.client;

import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.grpc.proto.Server.Result;
import ru.otus.grpc.proto.Server.Task;
import ru.otus.grpc.proto.ServiceGrpc;

public class ClientApp {

    private static final Logger log = LoggerFactory.getLogger(ClientApp.class);
    private static final AtomicInteger lastFromServer = new AtomicInteger();

    public static void main(String[] args) throws Exception {
        var channel = ManagedChannelBuilder.forAddress("localhost", 15001)
            .usePlaintext()
            .build();

        var stub = ServiceGrpc.newStub(channel);
        log.info("START");

        stub.generate(
            Task.newBuilder()
                .setFirstValue(0)
                .setLastValue(30)
                .build(),
            new GenerateObserver());

        int cur = 0;
        for (int i = 0; i < 50; ++i) {
            cur = cur + lastFromServer.getAndSet(0) + 1;
            log.warn("currentValue:{}", cur);
            Thread.sleep(1000);
        }

        log.warn("COMPLETE");
    }

    private static class GenerateObserver implements StreamObserver<Result> {

        @Override
        public void onNext(Result value) {
            log.info("Server next {}", value.getValue());
            lastFromServer.set(value.getValue());
        }

        @Override
        public void onError(Throwable t) {
            log.warn("Server exception", t);
        }

        @Override
        public void onCompleted() {
            log.warn("Server completed");
        }
    }
}
