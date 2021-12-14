package com.auction.config.jwt;

import com.auction.config.UserDetailsImpl;
import com.auction.exception.ExpiredJwtCustomException;
import com.auction.exception.IllegalArgumentCustomException;
import com.auction.exception.MalformedJwtCustomException;
import com.auction.exception.SignatureCustomException;
import com.auction.model.User;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

  @Value("${lot.app.jwtSecret}")
  private String jwtSecret;

  @Value("${lot.app.jwtExpirationMs}")
  private int jwtExpirationMs;

  public String generateJwtToken(Authentication authentication) {

    UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

    return Jwts.builder()
            .setSubject((userPrincipal.getUsername()))
            .setIssuedAt(new Date())
            .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact();
  }

  public String refreshToken(User user) {
    return Jwts.builder()
            .setSubject((user.getEmail()))
            .setIssuedAt(new Date())
            .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact();
  }

  public String getUserNameFromJwtToken(String token) {
    return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
  }

  public boolean validateJwtToken(String authToken) {
    try {
      Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
      return true;
    } catch (SignatureException e) {
      throw new SignatureCustomException("Invalid JWT signature: {}" + e.getMessage());
    } catch (MalformedJwtException e) {
      throw new MalformedJwtCustomException("Invalid JWT token: {}" + e.getMessage());
    } catch (ExpiredJwtException e) {
      throw new ExpiredJwtCustomException("JWT token is expired: {}" + e.getMessage());
    } catch (UnsupportedJwtException e) {
      throw new UnsupportedJwtException("JWT token is unsupported: {}" + e.getMessage());
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentCustomException("JWT claims string is empty: {}" + e.getMessage());
    }
  }
}
