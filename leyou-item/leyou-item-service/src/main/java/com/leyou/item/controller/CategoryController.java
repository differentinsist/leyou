package com.leyou.item.controller;

import com.leyou.item.pojo.Category;
import com.leyou.item.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 根据父节点的id查询子节点（响应的是数组的数据模型；数组里面是一个个Category对象）
     * 写了后台；你就可以访问前端项目；看看数据能否渲染；假如你看不到数据；你可以直接通过后台的URL来直接访问后台看看是否有数据；懂？Postman
     * @param pid  为什么输入3就不行；是因为查不到或者没有对吗？但是也没有提供错误返回值啊？？用Postman测试
     * @return   注意没有使用@ResposeBody注解；那他返回的数据模型是JSON吗
     */
    @GetMapping("list")  //http://api.leyou.com/api/item/category/list?pid=0（实际访问路劲是这样的；你看他那个参数怎么写？）
    public ResponseEntity<List<Category>> queryCategoriesByPid(@RequestParam(value = "pid",defaultValue = "0")Long pid){
        try {
            if(pid == null || pid < 0) {
                //return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); //参数不合法;响应400  还可以优化成下面的
                //return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                return ResponseEntity.badRequest().build();
            }
            List<Category> categories = this.categoryService.queryCategoriesByPid(pid);//这里也要加this？
            //判断一下是否为空；就是有没有查到数据；如果你自己写你会怎么写？
            //if(categories==null || categories.size()==0)  这样写不好
            if (CollectionUtils.isEmpty(categories)){  //这样才显得你NB；判断一个集合是否为空
                //return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  //资源服务器未找到；就是没有查询到；我们就响应404
                return ResponseEntity.notFound().build();  //上面这句可以优化成这样
            }
            System.out.println("到这里了吗？？");
            return ResponseEntity.ok(categories);   //200 查询成功
        }catch (Exception e){
            e.printStackTrace();
        }
        //500 服务内部错误
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        //代码优化：catch单词以及后面的代码都可以不不要(包括最后一句的return)；因为异常的话默认就会返回500；所以你不写也可以；
    }


    //elasticsearch部分用到的
    @GetMapping   //不用方法级别的路径    看视频有这个路劲吗("names")??????没有
    public ResponseEntity<List<String>> queryNamesByIds(@RequestParam("ids")List<Long> ids){
        List<String> names = this.categoryService.queryNameByIds(ids);
        if (CollectionUtils.isEmpty(names)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(names);
    }
}
