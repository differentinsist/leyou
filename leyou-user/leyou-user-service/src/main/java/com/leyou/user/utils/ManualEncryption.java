package com.leyou.user.utils;

//这个类是用来手动加密密码的，比如我在数据库手动添加了密码，然后也是手动加的盐，但是你从前端
// 登陆他走接口验证，是一种加密过的密码；所以手动的也在这里加密一下；不然密码正确也登陆不进去
public class ManualEncryption {

    public static void main(String[] args){
        //获取盐
        String salt = CodecUtils.generateSalt();
        System.out.println("==========salt:"+salt);

        //最原始的密码
        String password = "administrator";//拿你在数据库手动设置的密码过来这里就行

        //那盐和密码一起放到MD5中加密 （得到的就是加密后的密码）
        String passwd = CodecUtils.md5Hex(password,salt);
        System.out.println("===加密后的密码:"+passwd);

    }
}
