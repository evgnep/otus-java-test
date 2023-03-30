package ru.otus.grpc.server;

import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.grpc.proto.Server.Result;
import ru.otus.grpc.proto.Server.Task;
import ru.otus.grpc.proto.ServiceGrpc;

public class ServiceImpl extends ServiceGrpc.ServiceImplBase {
    private static final Logger log = LoggerFactory.getLogger(ServiceImpl.class);

    @Override
    public void generate(Task request, StreamObserver<Result> responseObserver) {
        new Generator(request, responseObserver);
    }

    private static class Generator extends Thread {
        private final int start;
        private final int end;
        private final StreamObserver<Result> responseObserver;

        Generator(Task request, StreamObserver<Result> responseObserver) {
            start = request.getFirstValue() + 1;
            end = request.getLastValue();
            this.responseObserver = responseObserver;

            log.info("Request: {} {}", start, end);

            if (start > end) {
                log.info("Complete");
                responseObserver.onCompleted();
            }
            else
                start();
        }

        @Override
        public void run() {
            try {
                for (int cur = start; cur < end; ++cur) {
                    log.info("Next: {}", cur);
                    responseObserver.onNext(Result.newBuilder().setValue(cur).build());
                    Thread.sleep(2000);
                }
                log.info("Complete");
                responseObserver.onCompleted();
            } catch (Exception e) {
                log.warn("Exception", e);
                responseObserver.onError(e);
            }
        }
    }

}
