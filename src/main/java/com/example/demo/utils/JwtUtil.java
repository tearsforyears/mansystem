package com.example.demo.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    private static final long EXPIRE_TIME = 7 * 60 * 60 * 1000;
    private static final String TOKEN_SECRET = "ssxxzyzybaba";

    public static String getJWTToken(Map<String, String> map) {
        // 过期时间
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        // 私钥及加密算法
        Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
        // 标准头信息
        HashMap<String, Object> header = new HashMap<>(2);
        header.put("typ", "JWT");
        header.put("alg", "HS256");
        // 附带username和userID生成签名
        JWTCreator.Builder builder = JWT.create().withHeader(header);
        for (String key : map.keySet()) {
            builder = builder.withClaim(key, map.get(key));
        }
        return builder.sign(algorithm);
    }


    public static boolean verity(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    public static String getTokenValue(String token, String key) {
        return JWT.decode(token).getClaim(key).asString();
    }
}
