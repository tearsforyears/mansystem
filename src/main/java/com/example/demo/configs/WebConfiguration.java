package com.example.demo.configs;

import com.example.demo.utils.JsonReponseBuilder;
import com.example.demo.utils.JwtUtil;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author zhanghaoyang
 */
@Configuration // 相当于一个独立的beans.xml注入整体的xml中
public class WebConfiguration {

    @Bean // 自己注册过滤器的生成方法
    public FilterRegistrationBean testFilterRegistration() {

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new TokenFilter());
        registration.addUrlPatterns("/info/*", "/manage/*");
        registration.setName("TokenFilter");
        registration.setOrder(1);
        return registration;
    }

    class TokenFilter implements Filter { // 内部类
        @Override
        public void destroy() {

        }

        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
                throws IOException, ServletException {
            HttpServletRequest req = (HttpServletRequest) request;
            String token = request.getParameter("token");
            System.out.println("过滤器处理请求" + req.getRequestURI() + " token:" + token);
            if (token != null && JwtUtil.verity(token)) {
                filterChain.doFilter(request, response);
            } else {
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(new JsonReponseBuilder().addItems(
                        "state", "200",
                        "msg", "token refuse"
                ).build());
            }
        }

        @Override
        public void init(FilterConfig arg0) throws ServletException {
        }
    }
}