package com.carlyu.configuration;

import com.carlyu.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class LoginConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /*静态资源的位置*/
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        //registry.addResourceHandler("/**").addResourceLocations("classpath:/templates/");
        /*放行swagger*/
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        final String[] adds = new String[]{
                "/admin/*",
                "/seller/**/*"
        };
        String[] excludes = new String[]{
                "/",
                "/error",
                "/seller/logout",
                "/admin/login",
                "/api/*"};
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns(adds)
                .excludePathPatterns(excludes);

    }

}