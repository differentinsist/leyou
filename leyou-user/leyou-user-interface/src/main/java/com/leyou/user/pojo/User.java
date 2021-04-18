package com.leyou.user.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Table(name = "tb_user")   //我的表名称是这个吗
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //用注解做数据校验；注解来自框架hibernate-validator
    @Length(min = 4, max = 30, message = "用户名必须在4-30位之间")
    private String username;// 用户名
    @Length(min = 4, max = 30, message = "密码必须在4-30位之间")
    @JsonIgnore  //作用：对象序列化为json字符串是忽略该属性(就是忽略密码;因为不用返回密码给别人看)
    private String password;// 密码      加了上面的注解；你返回这个user对象；就会忽略密码了
    @Pattern(regexp = "`1[356789]\\d{9}$", message = "手机号不合法")  //自定义校验规则？？
    private String phone;// 电话
    private Date created;// 创建时间
    @JsonIgnore
    private String salt;// 密码的盐值
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
