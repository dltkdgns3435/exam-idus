package shlee.exam.idus.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import shlee.exam.idus.domain.member.dto.domain.MemberAccount;
import shlee.exam.idus.domain.member.repository.MemberRepository;
import shlee.exam.idus.global.exception.exceptions.MemberNotFoundException;

@Service
@RequiredArgsConstructor
public class MemberDetailService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        MemberAccount account = memberRepository.findMemberAccountByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 사용자 입니다."));

        return User.builder()
                .username(account.getEmail())
                .password(account.getPassword())
                .authorities(new SimpleGrantedAuthority("ROLE_MEMBER"))
                .build();
    }
}
