package com.leyou.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration  //声明这是一个配置类
public class LeyouCorsConfiguration {

//    @Bean
//    public CorsFilter corsFilter(){
//        //初始化cors配置对象
//        CorsConfiguration configuration = new CorsConfiguration();
//        //允许跨域的域名；如果要携带cookie、就不能写*。星代表所有的域名都可以跨域访问
//        //configuration.addAllowedOrigin("*");
//        configuration.addAllowedOrigin("http://manage.leyou.com");
//        configuration.setAllowCredentials(true); //允许携带cookie
//        configuration.addAllowedMethod("*"); //代表所有的请求方法都可以：GET POST PUT Delete。。。
//        configuration.addAllowedHeader("*"); //允许携带任何头信息
//        //初始化cors配置源对象
//        UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();
//        configurationSource.registerCorsConfiguration("/**",configuration);
//        //返回corsFilter实例，参数：cors配置源对象
//        return new CorsFilter(configurationSource);
//    }

    @Bean
    public CorsFilter corsFilter(){
        //初始化cors配置对象
        CorsConfiguration configuration = new CorsConfiguration();
        //允许跨域的域名；如果要携带cookie；就不能写* ；*代表所有的域名都可以跨域访问
        configuration.addAllowedOrigin("http://www.leyou.com"); //搜索服务也会跨域；所以这里也要设置允许跨域
        configuration.addAllowedOrigin("http://manage.leyou.com");

        configuration.setAllowCredentials(true);//允许携带cookie
        configuration.addAllowedMethod("*");//代表所有的请求方法:GET、POST、PUT等
        configuration.addAllowedHeader("*");
        //初始化cors配置源对象
        UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();
        configurationSource.registerCorsConfiguration("/**", configuration);
        //返回conrsFilter实例，参数：cors配置源对象
        return new CorsFilter(configurationSource);
    }
}
