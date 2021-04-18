package com.leyou.upload.service;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class UploadService {

    /**   图片上传为什么我们要设置成绕过网关呢？因为图片上传么有必要也要经过网关；这样会增加网关的压力
     * 图片上传的业务逻辑
     * @param file  就是图片格式一般是.jpg但是他文件名有可能是有几个点比如xx.xx.jpg这种;我们就要判断最后一个是.jpg
     * @return  字符串处理你可以首先想到StringUtils
     */
    private static final List<String> CONTENT_TYPES = Arrays.asList("image/gif", "image/jpeg"); //静态的应用场景
    //上面是定义静态变量；我项目中很少使用静态的东西；定义一个List的静态变量；里面包含多个成员；以前是List list = new List()
    //写在他要有多个成员；所以把new List() 改为 Arrays.asList()就能初始化多个
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadService.class);//报错时的日志
    @Autowired
    private FastFileStorageClient storageClient; //使用FastFDS改造
    public String uploadImage(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        //originalFilename.split(".");//可以这样切割；但还有更好的
        //校验文件类型
        String contentType = file.getContentType();//这样获取到前端Headers里面的那个Content-Type后面的字符时是什么来判断
        if (!CONTENT_TYPES.contains(contentType)){
            LOGGER.info("文件(即图片)类型不合法: {}", originalFilename);
            return null;
        }
        try {
            //校验文件内容(意思是判断.jpg等名称的文件是不是图片文件;有些名字是但是不是图片格式)
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
            if (bufferedImage == null ){
                LOGGER.info("文件(即图片)内容不合法: {}", originalFilename);
                return null;
            }
            //保存到服务器(就是用户自己上传的图片；我们把它保存到服务器)
            //file.transferTo(new File("D:\\OrmProject\\image\\"+ originalFilename));//原来的。
            String ext = StringUtils.substringAfterLast(originalFilename,".");//使用FastFDS改造
            StorePath storePath = this.storageClient.uploadFile(file.getInputStream(), file.getSize(),ext,null);
            //返回url；进行回显
            //return "http://image.leyou.com/" + originalFilename;
            return "http://image.leyou.com/" + storePath.getFullPath();
        } catch (IOException e) {
            LOGGER.info("服务器内部错误：" + originalFilename);
            e.printStackTrace();
        }
        return null;//为什么返回null
    }
}
