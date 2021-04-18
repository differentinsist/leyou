package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.leyou.item.mapper") //在引导类加了这个扫描的注解；就不用在每个mapper接口都加@Mapper注解
public class LeyouItemApplication {
    public static void main(String[] args){
        SpringApplication.run(LeyouItemApplication.class);
    }
}
