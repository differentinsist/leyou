package com.leyou.search.client;

import com.leyou.item.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;

//feign远程调用接口你还记得吗？
@FeignClient("item-service")
public interface GoodsClient extends GoodsApi {

//    /**  不在这里写了；去leyou-interface那里写了 然后我们只需要继承GoodsApi就行了（导入依赖）
//     * 根据spuid查询spuDetail
//     * @param spuId
//     * @return
//     */
//    @GetMapping("spu/detail/{spuId}")
//    public SpuDetail querySpuDetailBySpuId(@PathVariable("spuId")Long spuId);
}
