package com.miguel.UTILS;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

public class YourJwtUtil {
    private static final long EXPIRATION_TIME = 3600000; // 1 hora en milisegundos
    private String jwtSecret;

    public YourJwtUtil(String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    // Método modificado para usar el ID del administrador en lugar del nombre de usuario
    public String generateToken(Long adminId) {
        byte[] decodedKey = Base64.getDecoder().decode(this.jwtSecret);
        SecretKey secretKey = Keys.hmacShaKeyFor(decodedKey);

        return Jwts.builder()
                .setSubject(adminId.toString()) // Usamos el ID del admin como 'subject'
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }


    // Método para verificar la validez del token
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(Base64.getDecoder().decode(this.jwtSecret)).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Extraer el ID del administrador del token
    public Long getAdminIdFromToken(String token) {
        return Long.parseLong(Jwts.parser()
                .setSigningKey(Base64.getDecoder().decode(this.jwtSecret))
                .parseClaimsJws(token)
                .getBody()
                .getSubject()); // Extraemos el 'subject' que es el ID del administrador
    }
}
