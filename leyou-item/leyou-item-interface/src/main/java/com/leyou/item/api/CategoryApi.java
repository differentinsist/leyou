package com.leyou.item.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("category")
public interface CategoryApi {


    //elasticsearch部分用到的
    @GetMapping   //不用方法级别的路径
    public List<String> queryNamesByIds(@RequestParam("ids")List<Long> ids);
}
