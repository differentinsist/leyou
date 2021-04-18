package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
//写引导类   仔细看引导类的构成；写法是怎么样的？
@SpringBootApplication
@EnableEurekaServer
public class LeyouRegistryApplication {
    public static void main(String[] args){
        SpringApplication.run(LeyouRegistryApplication.class);
    }
}
