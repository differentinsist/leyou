package com.leyou.item.controller;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.pojo.Brand;
import com.leyou.item.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("brand")
public class BrandController {

    @Autowired  //引入也是写private；set函数那些才是public
    private BrandService brandService;

    /** @RequestParam注解的使用方法？？  你看我在B站黑马电商那个项目好像没用过这个注解；为什么？
     * 根据查询条件查询并分页品牌信息
     * Request URL: http://api.leyou.com/api/item/brand/page?key=&page=1&rows=5&sortBy=id&desc=false
     * 上面这种请求路径的参数我们java怎么接受？
     * 对于密不知道他参数的默认值是什么的；你就可以写不是必须的required=false是吗？？
     * 注意this的使用
     * key:是品牌查询模糊查询的参数
     * page:是页码？？
     * rows:是当前显示多少条数据？？
     * sortBy:你要通过什么字段排序？
     * desc:是升序还是降序
     */
    @GetMapping("page")  //参数brand/page?key=&page=1&rows=5&sortBy=id&desc=false
    public ResponseEntity<PageResult<Brand>> queryBrandByPage(
            @RequestParam(value = "key", required = false)String key,
            @RequestParam(value = "page",defaultValue = "1")Integer page,
            @RequestParam(value = "rows",defaultValue = "5")Integer rows,
            @RequestParam(value = "sortBy", required = false)String sortBy,
            @RequestParam(value = "desc", required = false)Boolean desc
    ){
        //老师的习惯是先写表现层的方法；然后alt+回车到service写这个未定义的方法
        PageResult<Brand> result = this.brandService.queryBrandsByPage(key, page, rows, sortBy, desc);//这个的返回结果就是集合了
        //if(result == null || CollectionUtils.isEmpty(result.getItems())){  //这行代码还可以优化成下面那样;因为我们在service层写了return new PageResult;所以不可能为空;即是不用判断
        if (CollectionUtils.isEmpty(result.getItems())){ //注意是判断集合是否为空？而不是PageResult这个对象是否为null;集合是此对象里面的的一个属性
            //知道上面为什么是getItems了吧；意思是看看是否从数据库中查到有内容在里面；可以去看看PageResult封装对象
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result); //返回一个封装的对象；里面有集合
    }

    /**
     * 新增品牌；本来前端是传递过来的是JSON类型的对象的；但是前端使用qs工具才开分解了为单个一个个对象。所以表现层就可以分开来接收
     * 如果是JSON类型的对象；那我们括号里面的参数只能使用一个对象来接收；不能拆开；就是使用一个以上的参数来接收。
     */
    @PostMapping
    public ResponseEntity<Void> saveBrand(Brand brand, @RequestParam("cids")List<Long> cids){
        this.brandService.saveBrand(brand, cids);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    /**
     * 工具分类的id查询品牌的列表
     * @param cid
     * @return
     */
    @GetMapping("cid/{cid}")
    public ResponseEntity<List<Brand>> queryBrandsByCid(@PathVariable("cid")Long cid){
        List<Brand> brands = this.brandService.queryBrandsByCid(cid);
        if (CollectionUtils.isEmpty(brands)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(brands);
    }


    //elasticSearch模块用到
    @GetMapping("{id}")
    public ResponseEntity<Brand> queryBrandById(@PathVariable("id")Long id){
        Brand brand = this.brandService.queryBrandById(id);
        if (brand == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(brand);
    }
}








