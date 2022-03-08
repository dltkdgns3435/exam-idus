package shlee.exam.idus.global.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import shlee.exam.idus.domain.member.service.MemberDetailService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final long ACCESS_TOKEN_VALID_TIME = 60 * 60 * 1000L;//엑세스 토큰 만료시간: 1시간

    @Value("${jwt-secret-key}")
    private String JWT_SECRET_KEY;

    private final MemberDetailService memberDetailService;

    /**
     * TOKEN 생성
     */
    public JwtToken createToken(String email) {
        return JwtToken.builder()
                .accessToken(this.generateToken(email, ACCESS_TOKEN_VALID_TIME))
                .build();
    }

    private String generateToken(String name, long expiredTime) {
        Claims claims = Jwts.claims().setSubject(name);
        claims.put("ROLE", "MEMBER");
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expiredTime))
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET_KEY)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = memberDetailService.loadUserByUsername(getUserName(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUserName(String token) {
        return Jwts.parser()
                .setSigningKey(JWT_SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public Optional<String> resolveToken(HttpServletRequest req) {
        return Optional.ofNullable(req.getHeader("accessToken"));
    }

    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(JWT_SECRET_KEY).parseClaimsJws(jwtToken);

            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
