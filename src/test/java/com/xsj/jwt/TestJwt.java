package com.xsj.jwt;

import io.jsonwebtoken.*;
import org.junit.Test;

import java.util.Date;
import java.util.UUID;

/**
 * @author xingshenjie
 * @create 2022-05-02-11:14
 */
public class TestJwt {
    private static long tokenExpiration = 24*60*60*1000;  //24小时过期
    private static String tokenSignKey = "xsjtoken";
    /**
     * JWT的三个部分 头信息、body信息 、签名信息
     */
    //生成token
    @Test
    public void testJwt(){
       String token =  Jwts.builder()
                //头信息
                .setHeaderParam("typ", "JWT") //令牌类型
                .setHeaderParam("alg", "HS256") //签名算法

                //BODY 基本信息
                .setSubject("guli-user") //令牌主题
                .setIssuer("atguigu")//签发者
                .setAudience("atguigu")//接收者
                .setIssuedAt(new Date())//签发时间
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration)) //过期时间
                .setNotBefore(new Date(System.currentTimeMillis() + 20*1000)) //20秒后可用
                .setId(UUID.randomUUID().toString())
                //body 自己的信息， 注意不要设置密码
                .claim("nickname", "xsj")
                .claim("avatar", "1.jpg")

                //第三部分 签名信息
                .signWith(SignatureAlgorithm.HS256, tokenSignKey)//签名哈希
                .compact(); //转换成字符串

        System.out.println(token);

    }

    //解析token
    @Test
    public void parseJwt() {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJndWxpLXVzZXIiLCJpc3MiOiJhdGd1aWd1IiwiYXVkIjoiYXRndWlndSIsImlhdCI6MTY1MTQ2MjEwMywiZXhwIjoxNjUxNTQ4NTAzLCJuYmYiOjE2NTE0NjIxMjMsImp0aSI6IjczZmNhMGRiLWMwYTctNGNlZi05MThlLWExYTEzNjQ0OGFlNyIsIm5pY2tuYW1lIjoiSGVsZW4iLCJhdmF0YXIiOiIxLmpwZyJ9.YArQy4AasS0wgZ9bFjtLw4Z61vSFdO0vZADtfIxlaMM";
//        解析需要签名hash
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);

        Claims body = claimsJws.getBody();
        //
        String nickname = (String) body.get("nickname");

        System.out.println(nickname);
    }
}
