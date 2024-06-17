package cloud.client.cloudClient.jwt;


import cloud.client.cloudClient.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    public static final String SECRET_KEY = "871918f7498977e0305cb472a35138c6ae907a62f2e8157289161fa8ce55819d";
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractName(String token){
        return extractClaim(token, claims -> claims.get("name", String.class));
    }


    public <T> T extractClaim(String token, Function<Claims, T> claimResolver){
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }


    public String generateToken(User userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }




    public String generateToken(Map<String, Object> extraClaims, User userDetails){
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        Date expiryDate = new Date(nowMillis + 1000 * 60 * 24);

        return Jwts.builder()
                .addClaims(extraClaims)
                .claim("sub", userDetails.getUsername())
                .claim("iat", now)
                .claim("name", userDetails.getName())
                .claim("exp", expiryDate)
                .signWith(getSignIngKey())
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parser()
                .setSigningKey(getSignIngKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignIngKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}

