package com.zi.apipassenger.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    //加载顺序不同，因此需要提前注入拦截器
    @Bean
    public JwtInterceptor jwtInterceptor(){
        return  new JwtInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor())
                //拦截的路径
                .addPathPatterns("/**")
                //不拦截的路径
                .excludePathPatterns("/noAuthTest")
                .excludePathPatterns("/vertification-code")
                .excludePathPatterns("/vertification-code-check")
                .excludePathPatterns("/token-refresh");

    }
}
