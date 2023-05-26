package ma.ac.emi.ginfo.restfull.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import ma.ac.emi.ginfo.restfull.BikeMeApplication;
import ma.ac.emi.ginfo.restfull.entities.Role;
import ma.ac.emi.ginfo.restfull.entities.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Date;
import java.util.Map;

@Service
@Slf4j
public class JwtTokenProvider {

    private static final String AUTH_STORE = "ecommerce-auth-store";
    @Value("${keystore.password}")
    private String keyStorePassword;
    @Value("${jwt.expirationTimeMs}")
    private int jwtExpirationTimeInMillis;
    private KeyStore keyStore;

    @PostConstruct
    public void loadKeyStore() {
        try {
            keyStore = KeyStore.getInstance("JKS");
            InputStream resourceAsStream = BikeMeApplication.class.getResourceAsStream("/auth.jks");
            keyStore.load(resourceAsStream, keyStorePassword.toCharArray());
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
            throw new IllegalArgumentException("Exception occured while loading keystore");
        }

    }

    public String generateToken(User principal) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationTimeInMillis);

        return Jwts.builder()
                .setSubject(principal.getEmail())
                .addClaims(Map.of("userId", principal.getId()))
                .addClaims(Map.of("isAdmin", principal.getRole().equals(Role.ADMIN)))
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(getPrivateKey())
                .compact();
    }

    private PrivateKey getPrivateKey() {
        try {
            return (PrivateKey) keyStore.getKey(AUTH_STORE, keyStorePassword.toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            throw new IllegalArgumentException("Exception occurred while retrieving public key from keystore");
        }
    }

    private PublicKey getPublicKey() {
        try {
            Certificate certificate = keyStore.getCertificate(AUTH_STORE);
            return certificate.getPublicKey();
        } catch (KeyStoreException e) {
            throw new IllegalArgumentException("Exception occurred while retrieving public key from keystore");
        }
    }

    String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(getPublicKey())
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(getPrivateKey()).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token", ex);
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token", ex);
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token", ex);
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.", ex);
        } catch (Exception ex){
            log.error("Something went wrong");
        }
        return false;
    }

    public String getKeyStorePassword() {
        return keyStorePassword;
    }

    public int getJwtExpirationTimeInMillis() {
        return jwtExpirationTimeInMillis;
    }

    public KeyStore getKeyStore() {
        return keyStore;
    }
}
