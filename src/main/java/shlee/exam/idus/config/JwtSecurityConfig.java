package shlee.exam.idus.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import shlee.exam.idus.domain.member.repository.DeniedTokenRepository;
import shlee.exam.idus.global.jwt.JwtAuthFilter;
import shlee.exam.idus.global.jwt.JwtTokenProvider;

@RequiredArgsConstructor
public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private final JwtTokenProvider jwtTokenProvider;
    private final DeniedTokenRepository deniedTokenRepository;

    @Override
    public void configure(HttpSecurity http){
        JwtAuthFilter jwtAuthFilter = new JwtAuthFilter(jwtTokenProvider, deniedTokenRepository);
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
