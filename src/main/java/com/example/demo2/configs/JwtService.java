package com.example.demo2.configs;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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
    private static final String KEY ="775ACB95D36926A5B224671AB1153";


    public String extractUsername(String jwtToken){
        return extractClaim(jwtToken,Claims::getSubject);

    }
    private Claims extarctAllInfos(String jwtToken){
        return Jwts.parserBuilder()
                .setSigningKey(getSignInkey())
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();

    }

    private Key getSignInkey() {
        byte[] key_bytes = Decoders.BASE64.decode(KEY);
        return Keys.hmacShaKeyFor(key_bytes);
    }
    public <T> T extractClaim(String jwtToken, Function<Claims,T> claimsResolver){
        final Claims claims = extarctAllInfos(jwtToken);
        return claimsResolver.apply(claims);
    }
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(),userDetails);

    }
    private String generateToken(Map<String,Object> extractClaims,
    UserDetails userDetails)
    {
            return Jwts.builder().setClaims(extractClaims)
                    .setSubject(userDetails.getUsername())
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
                    .signWith(getSignInkey(), SignatureAlgorithm.HS256)
                    .compact();

    }
    public boolean validateToken(String jwtToken,UserDetails userDetails){
        final String username = extractUsername(jwtToken);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(jwtToken);

    }

    private boolean isTokenExpired(String jwtToken) {
        return extractExpiration(jwtToken).before(new Date());
    }

    private Date extractExpiration(String jwtToken) {
        return extractClaim(jwtToken,Claims::getExpiration);
    }
}
