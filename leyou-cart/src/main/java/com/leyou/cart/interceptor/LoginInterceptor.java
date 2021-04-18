package com.leyou.cart.interceptor;

import com.leyou.cart.config.JwtProperties;
import com.leyou.common.pojo.UserInfo;
import com.leyou.common.utils.CookieUtils;
import com.leyou.common.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//拦截器；写好拦截器后；还要把拦截器配置到spring容器中（写个配置类；或者XML文件中配置）


@Component  //加入spring容器中
@EnableConfigurationProperties(JwtProperties.class)
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private JwtProperties jwtProperties;

    //学会使用ThreadLocal；百度
    private static final ThreadLocal<UserInfo> THREAD_LOCAL = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取cookie中的token
        String token = CookieUtils.getCookieValue(request,this.jwtProperties.getCookieName());
        // 解析token，获取用户信息
        UserInfo userInfo = JwtUtils.getInfoFromToken(token,this.jwtProperties.getPublicKey());

        if (userInfo == null){
            return false;
        }

        //把userInfo放进线程局部变量
        THREAD_LOCAL.set(userInfo);

        return true;
    }


    //为什么要写这个方法？因为我们上面的变量THREAD_LOCAL是私有的；外面别人用不到；所以写一个获取的方法；记得
    public static UserInfo getUserInfo(){
        return  THREAD_LOCAL.get();
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //清空线程局部变量.因为使用的是Tomcat的线程池；线程不会结束；也就不会释放线程的局部变量；所以需要手动设置
        THREAD_LOCAL.remove();

    }
}
