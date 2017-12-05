package com.loveskai.config;

import com.loveskai.Scanner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    @Bean
    public Scanner scannerApplication() {
        return new Scanner();
    }
}
