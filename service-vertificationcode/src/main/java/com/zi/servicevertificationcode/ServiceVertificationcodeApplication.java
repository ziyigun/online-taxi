package com.zi.servicevertificationcode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ServiceVertificationcodeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceVertificationcodeApplication.class, args);
    }

}
