package com.leyou.item.api;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.bo.SpuBo;
import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

//看第12天第4个视频
//@RequestMapping("/goods")  //这行是不需要的；记得注释掉
public interface GoodsApi {

    @GetMapping("{id}")
    public Spu querySpuById(@PathVariable("id")Long id);


    /**（和视频的代码一样）
     * 根据spuId查询sku集合
     * @param spuId
     * @return
     */
    @GetMapping("sku/list")
    public List<Sku> querySkusBySpuId(@RequestParam("id")Long spuId);
//    public List<Sku> querySkusBySpuId(@RequestParam("id")Long id);


    /**（和视频代码一样）
     * 根据条件分页查询Spu
     * @param key
     * @param saleable
     * @param page
     * @param rows
     * @return
     */
    @GetMapping("spu/page")    //key=&saleable=true&page=1&rows=5 (根据这个写参数)
    public PageResult<SpuBo> querySpuByPage(
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "saleable", required = false) Boolean saleable,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows
    );

    /**（和视频的一样代码）
     * 根据spuid查询spuDetail
     * @param spuId
     * @return
     * 得回去学一下feign是怎么实现远程调用的？是因为URL相同吗？
     */
    @GetMapping("spu/detail/{spuId}")
    public SpuDetail querySpuDetailBySpuId(@PathVariable("spuId")Long spuId);


    //为了给其他服务调用而写的接口;上面的也是；给feign实现远程调用；思路是在item-service表现层有接口
    //然后在复制方法过来；就能实现远程调用
    //用法是哪个微服务调用；本身服务也需要创建Client(接口)；例如GoodsClient；
    // 然后extends继承item-service里面的api模块
    @GetMapping("sku/{skuId}")
    public Sku querySkuBySkuId(@PathVariable("skuId")Long skuId);

}

















