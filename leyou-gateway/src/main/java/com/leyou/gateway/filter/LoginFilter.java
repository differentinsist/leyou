package com.leyou.gateway.filter;

import com.leyou.common.utils.CookieUtils;
import com.leyou.common.utils.JwtUtils;
import com.leyou.gateway.config.FilterProperties;
import com.leyou.gateway.config.JwtProperties;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component  // 这样都在一个容器里面了
@EnableConfigurationProperties({JwtProperties.class, FilterProperties.class})
public class LoginFilter extends ZuulFilter {

    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private FilterProperties filterProperties;


    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 10;
    }

    @Override
    public boolean shouldFilter() {

        //获取白名单
        List<String> allowPaths = this.filterProperties.getAllowPaths();

        //初始化运行上下文
        RequestContext context = RequestContext.getCurrentContext();
        //获取request对象
        HttpServletRequest request = context.getRequest();

        //获取请求的路劲
        String url = request.getRequestURI().toString();

        for (String allowPath : allowPaths){
            if (StringUtils.contains(url,allowPath)){
                return false;
            }
        }

        return true ;
    }

    @Override
    public Object run() throws ZuulException {

        //初始化运行上下文
        RequestContext context = RequestContext.getCurrentContext();
        //获取request对象
        HttpServletRequest request = context.getRequest();

        String token = CookieUtils.getCookieValue(request, this.jwtProperties.getCookieName());

//        if (StringUtils.isBlank(token)){
//            context.setSendZuulResponse(false);
////            context.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value);
//            context.setResponseStatusCode(HttpStatus.SC_UNAUTHORIZED);  //这里和老师的不一样
//        }

        try {
            JwtUtils.getInfoFromToken(token,this.jwtProperties.getPublicKey());
        } catch (Exception e) {
            e.printStackTrace();
            context.setSendZuulResponse(false);
//            context.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());  //老师的是这个
            context.setResponseStatusCode(HttpStatus.SC_UNAUTHORIZED);  //这里和老师的不一样
        }

        return null;
    }
}
