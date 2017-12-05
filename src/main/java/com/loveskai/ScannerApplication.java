package com.loveskai;

import com.loveskai.config.SpringConfig;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ScannerApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        context.registerShutdownHook();
        Scanner scanner = context.getBean(Scanner.class);
        scanner.start();
    }
}
