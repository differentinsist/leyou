package com.leyou.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.pojo.PageResult;
import com.leyou.item.mapper.BrandMapper;
import com.leyou.item.pojo.Brand;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class BrandService {

    @Autowired
    private BrandMapper brandMapper;

    /**  业务逻辑你知道怎么写吗
     * 根据查询条件分页并排序查询品牌信息
     * @param key  其实这个项目还有很多问题；比如分页显示全部数据会报错；比如模糊查询也会报错没反应；自己慢慢测试慢慢优化
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @return
     */
    public PageResult<Brand> queryBrandsByPage(String key, Integer page, Integer rows, String sortBy, Boolean desc) {
        //初始化example对象  （去看Mybatis还是SpringMVC教程？文档？）
        Example example = new Example(Brand.class);  //都是通用Mapper的知识；回去看看Mybatis
        Example.Criteria criteria = example.createCriteria();
        //根据name模糊查询；或者根据首字母查询(先判断有没有空；就是人家要不要模糊查询)
        //if(!StringUtils.isBlank(key))  注意是isBlank还是isNotBlank;
        if(StringUtils.isNotBlank(key)){  //org.apache.commons.lang.StringUtils 前面是不是要加！
            criteria.andLike("name","%" + key + "%").orEqualTo("letter", key);
        }
        //添加分页条件
        PageHelper.startPage(page, rows); //这里没有处理rows；当前端点显示All时；也就是为-1的时候就会报错；就是说页面显示想要显示全部数据在一页的时候就会报错
        //我的想法是知道数据库一共有多少条数据；让rows关于总数就是全部数据；但是好像不太好。
        //添加排序条件(先判断是否为空先；为空的话就是不要求排序；那就不用排序)
        if(StringUtils.isNotBlank(sortBy)) {
            //判断为true还是false；true就desc；false就asc（就是升降序）
            example.setOrderByClause(sortBy + " " + (desc ? "desc" : "asc"));
        }
        List<Brand> brands = this.brandMapper.selectByExample(example);
        //包装成pageInfo
        PageInfo<Brand> pageInfo = new PageInfo<>(brands);
        //包装成分页结果集返回
        return new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 新增品牌
     * @param brand
     * @param cids
     */
    @Transactional
    public void saveBrand(Brand brand, List<Long> cids) {
        /** 加了事务注解之后就不用判断了；因为失败就会回滚；所以不用判断了
        //先新增数据到tb_brand表
        Boolean flag = this.brandMapper.insertSelective(brand) == 1;//数据库影响条数是否是1条？？代表成功？
        //再新增数据到中间表tb_category_brand
        if(flag){
            cids.forEach(cid -> {
                this.brandMapper.insertCategoryAndBrand(cid, brand.getId());
            });
        }  **/

        //先新增数据到tb_brand表
        this.brandMapper.insertSelective(brand); //数据库影响条数是否是1条？？代表成功？
        //再新增数据到中间表tb_category_brand
        cids.forEach(cid -> {
            this.brandMapper.insertCategoryAndBrand(cid, brand.getId());
        });
    }

    public List<Brand> queryBrandsByCid(Long cid) {

        return this.brandMapper.selectBrandsByCid(cid);
    }

    //根据分类id查询品牌列表
    public Brand queryBrandById(Long id) {
        return this.brandMapper.selectByPrimaryKey(id);
    }
}
