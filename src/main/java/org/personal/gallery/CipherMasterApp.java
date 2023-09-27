package org.personal.gallery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@SpringBootApplication(scanBasePackages = {
        "org.personal.gallery"
})

public class CipherMasterApp {
    public static void main(String[] args) {
        SpringApplication.run(CipherMasterApp.class, args);
    }

    @Bean(name = "customThreadPool")
    public Executor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setMaxPoolSize(5);
        executor.setCorePoolSize(2);
        executor.setThreadNamePrefix("customThread-");
        executor.initialize();
        return executor;
    }
}