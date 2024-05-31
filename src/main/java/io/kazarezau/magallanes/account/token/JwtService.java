package io.kazarezau.magallanes.account.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.kazarezau.magallanes.account.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import static io.jsonwebtoken.Jwts.parser;

@Service
public class JwtService {

    @Value("${token.signing.key}")
    private String jwtSigningKey;

    public String generateToken(User userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("username", userDetails.getUsername())
                .claim("email", userDetails.getEmail().email())
                .claim("role", userDetails.getAuthorities())
                .claim("id", userDetails.getId())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 100000 * 60 * 24))
                .signWith(key())
                .compact();
    }

    private SecretKey key() {
        return Keys.hmacShaKeyFor(jwtSigningKey.getBytes(StandardCharsets.UTF_8));
    }

    public String getUsername(String token) {
        return extractClaims(token).get("username", String.class);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = getUsername(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaims(token).getExpiration();
    }


    private Claims extractClaims(String token) {
        return parser()
                .verifyWith(key())
                .decryptWith(key())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
