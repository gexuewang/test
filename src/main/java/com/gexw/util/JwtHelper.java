package com.gexw.util;

import io.jsonwebtoken.*;
import org.springframework.util.StringUtils;

import java.util.Date;

public class JwtHelper {

    //过期时间
    private static long tokenExpiration = 24 * 60 * 60 * 1000;
    //签名秘钥
    private static String tokenSignKey = "123456";

    //根据参数生成token
    public static String createToken(Integer userId, String userName) {
        String token = Jwts.builder()
                .setSubject("ZZ-USER")//设置主题
                .setIssuedAt(new Date())//设置签发时间
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))//设置过期时间
                .claim("userId", userId)
                .claim("userName", userName)
                .signWith(SignatureAlgorithm.HS512, tokenSignKey)//设置签名算法和密钥
                .compressWith(CompressionCodecs.GZIP)
                .compact();
        return token;
    }

    //根据参数生成token
    public static void resolveToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(tokenSignKey) // 设置签名密钥
                .parseClaimsJws(token) // 解析JWT字符串为Claims对象
                .getBody(); // 获取Claims中的内容
        //System.out.println("Subject: " + claims.getSubject()); // 输出主题信息
        //System.out.println("Issued at: " + claims.getIssuedAt()); // 输出签发时间
        //System.out.println("Expiration: " + claims.getExpiration()); // 输出过期时间
    }

    //根据token解析验证
    public static boolean isTokenExpired(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(tokenSignKey)
                    .parseClaimsJws(token).getBody();
            return claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return true;//token过期失效
        }
    }

    //根据token字符串得到用户id
    public static Integer getUserId(String token) {
        if (StringUtils.isEmpty(token)) return null;

        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        Integer userId = (Integer) claims.get("userId");
        return userId;
    }

    //根据token字符串得到用户名称
    public static String getUserName(String token) {
        if (StringUtils.isEmpty(token)) return "";

        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        return (String) claims.get("userName");
    }

    public static void main(String[] args) {
        //生成token
        String token = JwtHelper.createToken(1, "15810923699");
        System.out.println(token);
        //根据token获取相关用户信息
        System.out.println(JwtHelper.getUserId(token));
        System.out.println(JwtHelper.getUserName(token));
        System.out.println(JwtHelper.isTokenExpired(token) ? "token过期失效" : "token可用");
        JwtHelper.resolveToken(token);
    }
}

