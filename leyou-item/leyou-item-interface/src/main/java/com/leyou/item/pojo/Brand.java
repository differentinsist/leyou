package com.leyou.item.pojo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="tb_brand")
public class Brand {
    @Id   //声明属性为主键
    @GeneratedValue(strategy = GenerationType.IDENTITY) //表示主键自动生成策略;一般和@id一起使用
    private Long id;
    private String name; //品牌名称
    private String image; //品牌图片
    private Character letter; //数据库是char类型；所以java中使用封装类型的Character

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Character getLetter() {
        return letter;
    }

    public void setLetter(Character letter) {
        this.letter = letter;
    }
}
