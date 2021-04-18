package com.leyou.item.service;

import com.leyou.item.mapper.CategoryMapper;
import com.leyou.item.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 根据父节点查询子节点（菜单）
     * 你要学习通用Mapper常用方法有哪些？
     * @param pid
     * @return
     */
    public List<Category> queryCategoriesByPid(Long pid) {
        Category record = new Category();
        record.setParentId(pid);  //为什么要设置值？和我直接写的项目不一样啊？？因为没写SQL语句？参数传递？
        return this.categoryMapper.select(record);  //记得加this我之前都没加过？？
    }


    public List<String> queryNameByIds(List<Long> ids){
        List<Category> categories = this.categoryMapper.selectByIdList(ids);//第九天第九个视频就说到
        return categories.stream().map(category -> category.getName()).collect(Collectors.toList());
    }
}
