package com.example.demo.service;

import com.example.demo.utils.JsonReponseBuilder;
import com.example.demo.utils.PhoneCodeUtil;
import com.example.demo.utils.SpringContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;


/**
 * 无状态的工具服务类 需要redis服务的支持
 * 这个类提供短信服务 而非站内信服务 站内信服务请移步至NoticeService
 *
 * @author zhanghaoyang
 */
@Scope("prototype")
@Service
public class MessageService {


    @Value("${aliyunkey.ACCESSKEYID}")
    private String ACCESSKEYID;
    @Value("${aliyunkey.ACCESSSECRET}")
    private String ACCESSSECRET;
    @Value("${aliyunkey.TEMPLATENAME}")
    private String TEMPLATENAME;

    /**
     * redis控制api
     * 特别说明下 stringRedisTemplate 可以共用而并不直接持有资源本身
     * template只是对redis-client的一层封装 真正处理并发请求的交由lettuce的线程池完成
     */
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /// 过期时间单位为秒
    private static final int expired = 30;


    /**
     * 发送短信然后返回验证码给前控制层进行进行路由控制
     * 这个服务的设计用到了redis作为中间件去缓存短信验证码
     * redis存的带过期时间的string的key是codePrefix+手机号
     * value是验证码本身 一个手机号不能频繁调用短信服务
     *
     * @param phone 手机
     * @Param codePrefix 前缀
     * @Return code
     */
    public String sendMessage(String phone, String codePrefix) {
        PhoneCodeUtil.setConfig(ACCESSKEYID, ACCESSSECRET, TEMPLATENAME);
        ValueOperations<String, String> vo = stringRedisTemplate.opsForValue();
        String retCode = "";
        String redisPhoneCode = vo.get(codePrefix + phone);
        if (!(redisPhoneCode == null || "".equals(redisPhoneCode))) {
            // redis中含有短信验证码 说明已经在过期时间内发送过短信了 我们直接返回
            retCode = redisPhoneCode;
        } else {
//             如果没有我就发短信 把手机号和code存到redis里面去 设置过期时间
//            retCode = PhoneCodeUtil.sendMsg(phone);

            retCode = "7777";
//             测试用默认为7777
            vo.set(codePrefix + phone, retCode, expired, TimeUnit.SECONDS);
        }
        return retCode;
    }

    /**
     * 获取存在redis里相应手机的验证码
     * @param phone  手机号
     * @param prefix 存redis的key前缀
     * @return
     */
    public String getPhoneCodeInRedis(String phone, String prefix) {
        ValueOperations<String, String> vo = stringRedisTemplate.opsForValue();
        return vo.get(prefix + phone);
    }
}
