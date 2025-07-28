package jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.expression.ExpressionException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtils {
    // This class is a placeholder for JWT utility methods.
    // You can implement methods for generating, validating, and parsing JWT tokens here.

    //Getting JWT token
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    @Value("${spring.app.jwtExpirationMs}")
    private int jwtExpirationMs;
    @Value("${spring.app.jwtSecret}")
    private String jwtSecret;

    public String getJwtFromHeader(HttpServletRequest request) {

        String header = request.getHeader("Authorization");
        logger.debug("Authorization header: {}", header);
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7); // remove bearer prefix
        }
        return null; // return null if no token is found
    }

    //Generate token
    public String generateToken(UserDetails userDetails) {
        // Implement token generation logic
        String username = userDetails.getUsername();
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date((new Date().getTime() + jwtExpirationMs)))
                .signWith(key()) // Use a secure key
                .compact();
    }

    //Getting username from twt token
    public String getUsernameFromToken(String token) {
        // Implement logic to extract username from token

        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build().parseSignedClaims(token)
                .getPayload().getSubject();
    }

    //Generate signing key
    public Key key() {
        // Implement logic to generate a secure signing key
        // For example, you can use a secret key or a public/private key pair
        return Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(jwtSecret)
        );
    }

    //Validate token
    public boolean validateToken(String token) {
        // Implement token validation logic
        try {
            System.out.println("Validating token: " + token);
            Jwts.parser()
                    .verifyWith((SecretKey) key())
                    .build()
                    .parseSignedClaims(token);
            return true;

        }catch (MalformedJwtException exception){
            logger.error("JWT token is malformed: {}", exception.getMessage());
        }catch (ExpiredJwtException exception){
            logger.error("JWT token is expired: {}", exception.getMessage());
        }catch (IllegalArgumentException exception){
            logger.error("JWT token is empty: {}", exception.getMessage());
        }catch (UnsupportedJwtException exception){
            logger.error("JWT token is unsupported: {}", exception.getMessage());
        }
        return true;
    }


}
