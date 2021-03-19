package io.github.zerda.feign.demo.api;

import io.github.zerda.feign.demo.feign.HttpEchoClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

@RestController
public class TestController {
    private final HttpEchoClient client;
    private final Executor executor;

    public TestController(HttpEchoClient client,
                          @Qualifier("threadPoolTaskExecutor") Executor executor) {
        this.client = client;
        this.executor = executor;
    }

    @GetMapping("/api/v1/remote")
    public String remote(@RequestParam(value = "delay", defaultValue = "10000") int delay)
            throws ExecutionException, InterruptedException {
        CompletableFuture<String> call = CompletableFuture.supplyAsync(() -> client.echo(delay), executor);
        return call.get();
    }

    @GetMapping("/api/v1/local")
    public String local() {
        return "OK";
    }
}
