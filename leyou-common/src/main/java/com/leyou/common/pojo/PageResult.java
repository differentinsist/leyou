package com.leyou.common.pojo;

import java.util.List;

/**
 * 这个是分页结果的封装对象；你明白吗？就是对应那种页面；类似表格的页面；有多条内容；然后下面有页码等
 * 那内容可能是哪个对象我们还不知道；所以用泛型T来表示；这就很灵活了；只要用到分页的都可以调用我这个对象
 * 仔细想想；你会直接写这种吗
 * @param <T>
 */
public class PageResult<T> {

    private Long total;
    private Integer totalPage;
    private List<T> items;

    public PageResult() {
    }

    public PageResult(Long total, List<T> items) {
        this.total = total;
        this.items = items;
    }

    public PageResult(Long total, Integer totalPage, List<T> items) {
        this.total = total;
        this.totalPage = totalPage;
        this.items = items;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}
