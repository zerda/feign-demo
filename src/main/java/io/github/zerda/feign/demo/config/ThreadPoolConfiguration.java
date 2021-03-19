package io.github.zerda.feign.demo.config;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.jvm.ExecutorServiceMetrics;
import lombok.extern.java.Log;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@Log
public class ThreadPoolConfiguration {
    @Bean(name = "threadPoolTaskExecutor")
    public Executor threadPoolTaskExecutor(MeterRegistry registry) {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(2);
        taskExecutor.setMaxPoolSize(2);
        taskExecutor.setQueueCapacity(4);;
        taskExecutor.initialize();
        return ExecutorServiceMetrics.monitor(registry, taskExecutor.getThreadPoolExecutor(), "http-remote-call-executor");
    }
}
