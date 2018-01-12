package com.nowcoder.toutiao.configuration;

import com.nowcoder.toutiao.interceptor.LoginRequiredInterceptor;
import com.nowcoder.toutiao.interceptor.PassportInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Component
public class ToutiaoWebConfiguration extends WebMvcConfigurerAdapter {//把拦截器加入spring流程中


    @Autowired
    PassportInterceptor passportInterceptor;

    @Autowired
    LoginRequiredInterceptor loginRequiredInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(passportInterceptor);
        registry.addInterceptor(loginRequiredInterceptor).addPathPatterns("/setting*");//只有访问setting页面才会拦截,注册登录拦截器
        super.addInterceptors(registry);
    }
}
