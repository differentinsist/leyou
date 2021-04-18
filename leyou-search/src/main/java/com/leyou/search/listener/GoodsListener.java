package com.leyou.search.listener;

import com.leyou.search.service.SearchService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

//监听MQ消息
@Component
public class GoodsListener {

    @Autowired
    private SearchService searchService;

    //监听新增商品和修改商品的消息？？
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "LEYOU.SEARCH.SAVE.QUEUE",durable = "true"),
            exchange=@Exchange(value="LEYOU.ITEM.EXCHANGE",ignoreDeclarationExceptions="true",type= ExchangeTypes.TOPIC),
            key={"item.insert","item.update"}
    ))
    public void save(Long id) throws IOException {   //相当于item-service那里新增了商品;这里就会监听到消息；然后就及时更新我的页面
        if (id == null){
            return;
        }
        this.searchService.save(id);
    }


    //监听删除操作的MQ消息
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "LEYOU.SEARCH.DELETE.QUEUE",durable = "true"),
            exchange=@Exchange(value="LEYOU.ITEM.EXCHANGE",ignoreDeclarationExceptions="true",type= ExchangeTypes.TOPIC),
            key={"item.delete"}
    ))
    public void delete(Long id) throws IOException {   //相当于item-service那里新增了商品;这里就会监听到消息；然后就及时更新我的页面
        if (id == null){
            return;
        }
        this.searchService.delete(id);
    }


}
