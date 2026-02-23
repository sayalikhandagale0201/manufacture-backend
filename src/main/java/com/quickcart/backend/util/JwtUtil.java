package com.quickcart.backend.util;

import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;

@Component // <-- ये सबसे जरूरी है
public class JwtUtil {

    private final String SECRET_KEY = "yourSecretKey123"; // अपनी secret डालो

    // Token generate करने के लिए
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 घंटे
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // Token validate करने के लिए
    public boolean validateToken(String token, String username) {
        String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    public String extractUsername(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }

    private boolean isTokenExpired(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getExpiration()
                .before(new Date());
    }
}
