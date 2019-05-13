/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package com.packtpub.rest.ch6.util;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.spec.SecretKeySpec;

/**
 * Helper class with required functions for JWT Authentication
 *
 * @author Balachandar
 */
public class JWTAuthHelper {

    private static final Logger logger = Logger.getLogger(JWTAuthHelper.class.getName());

    public static Key generateKey() {
        String keyString = "jwttestkey";
        Key key = new SecretKeySpec(keyString.getBytes(), 0, keyString.getBytes().length, "DES");
        return key;
    }

    public static boolean isValidToken(String token) {
        try {

            Key key = generateKey();
            Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            logger.log(Level.INFO, "Valid Token:{0}", token);

        } catch (ExpiredJwtException | MalformedJwtException | SignatureException | UnsupportedJwtException | IllegalArgumentException e) {

            logger.log(Level.SEVERE, "Invalid Token:" + token, e);
            return false;
        }
        return true;
    }

    public static String issueToken(String login, String issuer) {
        Key key = generateKey();
        String jwtToken = Jwts.builder()
                .setSubject(login)
                .setIssuer(issuer)
                .setIssuedAt(new Date())
                .setExpiration(toDate(LocalDateTime.now().plusMinutes(15L)))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
        logger.log(Level.INFO, "Generated token :{0} for the key :{1}", new Object[]{jwtToken, key});
        return jwtToken;

    }

    private static Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
