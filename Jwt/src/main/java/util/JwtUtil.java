package util; /**
 * @Author: 乐哥
 * @Date: 2023/4/10
 * @Time: 21:37
 * @Description: Jwt生成器与解析
 */

import com.s3.bean.MemberInfoBean;
import com.s3.bean.UserInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

/**
 * JWT工具类
 */
public class JwtUtil {

    //有效期为
    public static final Long JWT_TTL = 60 * 60 *1000L;// 60 * 60 *1000  有效期:一个小时
    //设置秘钥明文
    public static final String JWT_KEY = "yltService";



    /**
     * 生成jtw
     * @param subject token中要存放的数据（json格式）
     * @param ttlMillis token超时时间
     * @return
     */
    public static String createJWT(String userId, String subject, Long ttlMillis) {
        JwtBuilder builder = getJwtBuilder(userId,subject, ttlMillis, String.valueOf(UUID.randomUUID()));// 设置过期时间
        return builder.compact();
    }

    private static JwtBuilder getJwtBuilder(String userId,String subject, Long ttlMillis, String uuid) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        if(ttlMillis==null){
            ttlMillis=JwtUtil.JWT_TTL;
        }
        long expMillis = nowMillis + ttlMillis;
        Date expDate = new Date(expMillis);
        return Jwts.builder()
                //设置token类型
                .setHeaderParam("typ", "JWT")
                //设置加密算法
                .setHeaderParam("alg", "HS256")
                //TODO:将用户信息存入token
                //.claim("user","user")
                .claim("user",userId)
                .setId(uuid)              //唯一的ID
                .setSubject(subject)   // 主题  可以是JSON数据
                .setIssuer("yjlyyds")     // 签发者
                .setIssuedAt(now)      // 签发时间
                .signWith(signatureAlgorithm, JWT_KEY) //使用HS256对称加密算法签名, 第二个参数为秘钥
                .setExpiration(expDate);//过期时间

    }

    /**
     * 创建token
     * @param id
     * @param subject
     * @param ttlMillis
     * @return
     */
    public static String createJWT(String userId,String id, String subject, Long ttlMillis) {
        JwtBuilder builder = getJwtBuilder(userId,subject, ttlMillis, id);// 设置过期时间
        return builder.compact();
    }

    /**
     * 解析
     *
     * @param jwt
     * @return
     * @throws Exception
     */
    public static Claims parseJWT(String jwt) throws Exception {
        return Jwts.parser()
                .setSigningKey(JWT_KEY)
                .parseClaimsJws(jwt)
                .getBody();
    }
}


