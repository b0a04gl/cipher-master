package org.personal.gallery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@SpringBootApplication(scanBasePackages = {
        "org.personal.gallery"
})

public class CipherMasterApp {
    public static void main(String[] args) {
        SpringApplication.run(CipherMasterApp.class, args);
    }
}