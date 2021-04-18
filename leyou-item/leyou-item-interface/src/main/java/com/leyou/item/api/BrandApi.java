package com.leyou.item.api;


import com.leyou.item.pojo.Brand;
import org.springframework.web.bind.annotation.*;


@RequestMapping("brand")
public interface BrandApi {

    //elasticSearch模块用到
    @GetMapping("{id}")//和视频的一样代码
    public Brand queryBrandById(@PathVariable("id")Long id);
}








