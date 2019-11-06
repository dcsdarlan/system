package com.izicash.system.util.security;

import com.izicash.system.config.ContextConfig;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.core.env.Environment;

import java.util.Date;

public class JWTService {

    //public static final String SECRET = "izicash.com";
    /* 10 Dias */
    public static final long EXPIRATION_TIME = 860_000_000;
    public static final String TOKEN_PREFIX = "Bearer";
    public static final String HEADER_STRING = "authorization";

    public static String getToken(String payload){
        Environment env = ContextConfig.contextProvider()
                .getApplicationContext()
                .getBean(Environment.class);
        String secret = env.getProperty("app.secret");
        String jwt = Jwts.builder()
                .setSubject(payload)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
        return jwt;
    }
    public static String getPayload(String token){
        Environment env = ContextConfig.contextProvider()
                .getApplicationContext()
                .getBean(Environment.class);
        String secret = env.getProperty("app.secret");
        String payload = "";
        try {
            payload = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody()
                    .getSubject();
        } catch (Exception e) {

        }
        return payload;
    }
}
