package shlee.exam.idus.global.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import shlee.exam.idus.domain.member.repository.DeniedTokenRepository;
import shlee.exam.idus.global.exception.exceptions.MemberAlreadyLogoutException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final DeniedTokenRepository deniedTokenRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional<String> tokenOpn = jwtTokenProvider.resolveToken(request);

        try {
            if(tokenOpn.isPresent()){
                String token = tokenOpn.get();
                boolean ieDenied = deniedTokenRepository.findById(token).isPresent();

                if (jwtTokenProvider.validateToken(token) && !ieDenied) {
                    Authentication auth = jwtTokenProvider.getAuthentication(token);
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
                else if(ieDenied){
                    request.setAttribute("exception", new MemberAlreadyLogoutException("이미 로그아웃된 사용자 입니다."));
                }
                else {
                    SecurityContextHolder.clearContext();
                }
            }
        }catch (Exception e) {
            request.setAttribute("exception", e);
        }


        filterChain.doFilter(request, response);
    }
}
