package com.testtask.TaskManagementSystem.config;

import com.testtask.TaskManagementSystem.entity.RefreshToken;
import com.testtask.TaskManagementSystem.entity.User;
import com.testtask.TaskManagementSystem.exceptions.JwtAuthenticationException;
import com.testtask.TaskManagementSystem.exceptions.UserNotFoundException;
import com.testtask.TaskManagementSystem.repository.RefreshTokenRepository;
import com.testtask.TaskManagementSystem.repository.UsersRepository;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Класс для создания и работы с токеном
 */
@Component
@RequiredArgsConstructor
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String secret;
    private static final Duration jwtLifetime = Duration.ofMillis(30*60*1000);

    private static final Duration refreshJwtLifetime = Duration.ofMillis(30*24*60*1000);
    private static final String TOKEN_TYPE = "token_type";
    private static final String REFRESH_TOKEN_TYPE = "refresh";
    private static final String ACCESS_TOKEN_TYPE = "access";
    private final RefreshTokenRepository refreshTokenRepository;
    private final UsersRepository usersRepository;

    /**
     * Метод генерации токена
     */
    public String generateToken(UserDetails userDetails,
                                Duration expirationTime,
                                String tokenType) {
        Date issuedDate = new Date();
        Date expiredDate = new Date(issuedDate.getTime() + expirationTime.toMillis());
        return Jwts.builder()
                .setClaims(generateClaims(userDetails, tokenType))
                .setSubject(userDetails.getUsername())
                .setIssuedAt(issuedDate)
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    /**
     * Метод для создания токена доступа
     */
    public String createAccessToken(UserDetails userDetails) {
        return generateToken(
                userDetails,
                jwtLifetime,
                ACCESS_TOKEN_TYPE
        );
    }

    /**
     * Метод для создания рефреш-токена
     */
    public String createRefreshToken(UserDetails userDetails) {
        return generateToken(
                userDetails,
                refreshJwtLifetime,
                REFRESH_TOKEN_TYPE
        );
    }

    /**
     * Метод проверки валидности токена доступа
     */
    public boolean isAccessTokenValid(String token, UserDetails userDetails) {
        return isTokenValid(token, userDetails)
                && ACCESS_TOKEN_TYPE.equals(getTokenType(token));
    }

    /**
     * Метод проверки валидности рефреш-токена
     */
    public boolean isRefreshTokenValid(String token, UserDetails userDetails) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token).orElseThrow(() -> new JwtAuthenticationException("рефреш-токен не найден в бд"));
        User user = usersRepository.findByUsername(userDetails.getUsername()).orElseThrow(() -> new UserNotFoundException("пользователь не найден"));
        return refreshToken.getUser().equals(user) && isTokenValid(token, userDetails) && REFRESH_TOKEN_TYPE.equals(getTokenType(token));
    }

    /**
     * Метод получения токена доступа по рефреш-токену
     */
    public String refreshToken(String refreshToken, UserDetails userDetails) {
        if (isRefreshTokenValid(refreshToken, userDetails)) {
            return createAccessToken(userDetails);
        }
        throw new JwtAuthenticationException("Рефреш токен не валиден!");
    }

    /**
     * Метод получения типа токена из токена
     */
    public String getTokenType(String token) {
        return (String) getAllClaimsFromToken(token).get(TOKEN_TYPE);
    }


    /**
     * Метод для создания claims для токена
     */
    private Map<String, Object> generateClaims(UserDetails userDetails, String tokenType) {
        Map<String, Object> claims = new HashMap<>();
            claims.put(TOKEN_TYPE, tokenType);
            claims.put("username", userDetails.getUsername());
            List<String> rolesList = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
            claims.put("roles", rolesList);
            return claims;
    }

    /**
     * Метод для проверки валидности токена
     */
    private boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsername(token);
        return username.equals(userDetails.getUsername()) && !getExpirationDate(token).before(new Date());
    }

    /**
     * Метод для получения даты протухания токена из токена
     */
    private Date getExpirationDate(String token) throws JwtAuthenticationException {
        return getClaim(token, Claims::getExpiration);
    }

    /**
     * Метод получения claims
     */
    private <T> T getClaim(String token, Function<Claims, T> claimsResolver) throws JwtAuthenticationException {
        try {
            return claimsResolver.apply(getAllClaimsFromToken(token));
        } catch (ExpiredJwtException e) {
            genUserException("Token expired.");
        } catch (UnsupportedJwtException e) {
            genUserException("Token unsupported.");
        } catch (MalformedJwtException e) {
            genUserException("Malformed token.");
        } catch (Exception e) {
            genUserException("Invalid token.");
        }
        return null;
    }

    /**
     * Метод для создания исключения с разными сообщениями
     */
    private void genUserException(String message) throws JwtAuthenticationException {
        throw new JwtAuthenticationException("Unauthorized error: " + message);
    }



    /**
     * Метод получения логина из токена
     */
    public String getUsername(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    /**
     * Метод получения ролей из токена
     */
    public List<String> getRoles(String token) {
        return getAllClaimsFromToken(token).get("roles", List.class);
    }

    /**
     * Метод преобразования токена в класс Claims для дальнейшего получения логина и ролей из токена
     */
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }
}
