package esprit.pi.demo.Security.Jwt;

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
public class JwtServiceImp  implements JwtService {
    private static final String SECRET_KEY="6F/OF1KTe44KQYmpcrX9uGcrk86BHc7DhguS9piN663r60c8CRn23DTwAwDNKp/dGO3e48PxS20p9HvyNZFEVUDn3LabwYbtqR8s/OVkSiqA2ImJyTN9ZxyITi6A6keJN56I+mzDpcXMQCrw6tCArLG+PMOjYIQbKpATjEVh4iiV91+xzh8z16S4prpJ8NJBN94Jx6rN59wQbl2mb0D8PW2xq7YSZ2p6h09SmJ3jF3j4CPiSBbRxF3AqpLcShrz/MWQ2r/Bpc26iv1cwlkcphn7WuYmM8eoGHni0QvCeO4BgjDZpaBZd+AkozoH8IvdLlj40X00IuczC6P56PoTQcmNcFVri97V01Jv3BBI6J4o=\n";
    private static final long jwtExpiration = 86400000; //a day
    private static final long refreshExpiration=604800000; //7 days

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username =extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    @Override
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims ::getSubject);
    }

    @Override
    public String generateRefreshToken(UserDetails userDetails) {
        return buildToken(new HashMap<>(),userDetails,refreshExpiration);
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    @Override
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims,userDetails,jwtExpiration);

    }
    @Override
    public String buildToken (Map<String, Object> extraClaims, UserDetails userDetails,long expiration){
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInkey(), SignatureAlgorithm.HS256)
                .compact();
    }



    @Override
    public Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInkey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    @Override
    public Key getSignInkey() {
        byte[] keyBytes= Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
