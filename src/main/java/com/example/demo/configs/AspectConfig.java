package com.example.demo.configs;

import com.example.demo.utils.MD5;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;

import java.lang.annotation.Annotation;

/**
 * 这个类用来配置一些切面完成 特定方法的增强
 * 需要注意的是 切面拦截的方法的类 都是由spring管辖而非自己new出来的
 * (new出来的东西需要虚拟机拦截方法 大概也就c++/.dll能实现?)
 *
 * @author zhanghaoyang
 */
@Configuration
@Aspect
public class AspectConfig {
    /**
     * 单独给User类的setPassWord增强
     * 完成密码加盐
     * 需要注意的是密码加盐这一过程是由spring所管辖的对象才会有这样的效果的
     */
    @Around("execution(* com.example.demo.repositroy.User.setPassWord(..))")
    public void addSalt(ProceedingJoinPoint jp) throws Throwable {
        String password = (String) jp.getArgs()[0];
        // 密码加盐
        jp.proceed(new Object[]{MD5.GetMD5Salt(password)});
    }
}
