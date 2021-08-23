package com.carlyu.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebAppConfig implements WebMvcConfigurer {

    /*
     * 静态资源本地映射
     * img是虚拟路径
     * 浏览器访问:localhost:8080/img/xxx文件
     * */
    /*public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/api/**");
                //.addResourceLocations("file:D:/javaspace/tomcatPath/")
                //.setCachePeriod(31556926);
    }*/
    //前端跨域
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")//设置允许跨域的路径
                .allowedOrigins("*")//设置允许跨域请求的域名
                //.allowCredentials(true)//是否允许证书 不再默认开启
                .allowedMethods("GET", "POST", "PUT", "DELETE")//设置允许的方法
                .maxAge(3600);//跨域允许时间
    }
}