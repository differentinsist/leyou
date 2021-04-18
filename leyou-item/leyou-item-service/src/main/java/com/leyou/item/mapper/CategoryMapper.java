package com.leyou.item.mapper;

import com.leyou.item.pojo.Category;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

//@Mapper 不用在每个接口都加这个注解；因为我们开启了接口扫描
public interface CategoryMapper extends Mapper<Category>,SelectByIdListMapper<Category,Long> {
    //继承通用Mapper就相当于有了他的单表查询方法；我们就可以直接用；子类继承父类的的方法？但是这个是接口啊？？基础不行？
    //接口可以继承一个类？？不是应该继承接口吗？说错了Mapper也是接口；
}
