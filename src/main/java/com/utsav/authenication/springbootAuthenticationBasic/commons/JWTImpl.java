package com.utsav.authenication.springbootAuthenticationBasic.commons;

import com.utsav.authenication.springbootAuthenticationBasic.additional.ApplicationConstants;
import com.utsav.authenication.springbootAuthenticationBasic.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JWTImpl {
    private static String secretKey = ApplicationConstants.SECRET_KEY;

    public static Claims parseJwtTokenImpl(String token){
        String secretKeyToSend = evaluateSecretKeyToSend();
        return parseJwtToken(token);
    }
    private static Claims parseJwtToken(String jwtToken) {
        String secretKeyTosend = evaluateSecretKeyToSend();
        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(secretKeyTosend)
                .build()
                .parseClaimsJws(jwtToken);

        return claimsJws.getBody();
    }

    public static String generateToken(Map<String, ?> payLoad, Map<String, Object> header, String secretKey) {
        Date now = new Date();
//        Date expiryDate = new Date(now.getTime() + expiration);
//        Map<String, ?> payLoad = getPayLoad(username);
        return Jwts.builder()
                .setHeader(header)
                .setClaims(payLoad)
//                .claim("roles", roles)
                .setIssuedAt(now)
//                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }


    public static String buildToken(User user, Date date) {
        Map<String, ?> payLoadMap = getPayload(user, new Date());
        Map<String, Object> headerMap = getHeaderMap();
        String secretKeyToSend = evaluateSecretKeyToSend();
        return generateToken(payLoadMap, headerMap, secretKeyToSend);
    }

    private static String evaluateSecretKeyToSend() {
        return Base64.encodeBase64String(secretKey.getBytes(StandardCharsets.UTF_8));
    }


    private static Map<String, Object> getHeaderMap() {
        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("alg", "HS512");
        headerMap.put("typ", "JWT");
        return headerMap;
    }


    private static Map<String,Object> getPayload(User user, Date date) {
        Map<String, Object> payLoadMap = new HashMap<>();
        payLoadMap.put("userEmail", user.getEmail());
        payLoadMap.put("userId", user.getId());
        payLoadMap.put("userFullName", user.getFullName());
        payLoadMap.put("generatedDate", date);
        List<String> roles = user.getRoles().
                stream().
                filter(r->"N".equals(r.getIsDeleted()))
                .map(r->r.getRole()).collect(Collectors.toList());
        payLoadMap.put("userRoles", roles);
        return payLoadMap;
    }

}
