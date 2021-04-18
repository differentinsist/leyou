package com.leyou.elasticsearch.test;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.bo.SpuBo;
import com.leyou.item.pojo.Spu;
import com.leyou.search.client.CategoryClient;
import com.leyou.search.client.GoodsClient;
import com.leyou.search.pojo.Goods;
import com.leyou.search.repository.GoodsRepository;
import com.leyou.search.service.SearchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ElasticsearchTest {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    private GoodsRepository goodsRepository;
    @Autowired
    private SearchService searchService;
    @Autowired
    private GoodsClient goodsClient;

    @Test
    public void test(){
        this.elasticsearchTemplate.createIndex(Goods.class);
        this.elasticsearchTemplate.putMapping(Goods.class);

        Integer page = 1;
        Integer rows = 100;

        //知道do...while()的用法
        do {
            //分页查询spu；获取分页结果集
            PageResult<SpuBo> result = this.goodsClient.querySpuByPage(null, null, page, rows);
//            PageResult<SpuBo> result = this.goodsClient.querySpuBoByPage(null, true, page, rows);

            //获取当前页的数据
            List<SpuBo> items = result.getItems();
            //处理List<SpuBo> 变成 List<Goods>
            List<Goods> goodsList = items.stream().map(spuBo -> {
                try {  //到这里不是抛异常；而是捉异常；因为是测试阶段了
                    return this.searchService.buildGoods(spuBo);  //旧的
//                    return this.searchService.buildGoods((Spu) spuBo);  //看文档改的
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }).collect(Collectors.toList());

            //执行新增数据的方法
            this.goodsRepository.saveAll(goodsList);

            rows = items.size();
            page++;
        } while (rows == 100);
    }


//
//    @Autowired
//    private CategoryClient categoryClient;
//    @Test
//    public void testQueryCategories(){
//        List<String> names = this.categoryClient.queryNamesByIds(Arrays.asList(1L, 2L, 3L));
//        names.forEach(System.out::println);
//    }

}
