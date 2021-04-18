package com.leyou.user.api;

import com.leyou.user.pojo.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

//授权中心需要用到
public interface UserApi {

    @GetMapping("query")                   //看接口URL参数要求；我们使用这个@RequestParam注解
    public User queryUser(@RequestParam("username") String username, @RequestParam("password") String password);


}
