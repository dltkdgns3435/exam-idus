package shlee.exam.idus.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shlee.exam.idus.domain.member.dto.domain.MemberAccount;
import shlee.exam.idus.domain.member.dto.domain.MemberDetailInfo;
import shlee.exam.idus.domain.member.dto.request.LoginMemberDto;
import shlee.exam.idus.domain.member.dto.request.PostMemberDto;
import shlee.exam.idus.domain.member.entity.DeniedToken;
import shlee.exam.idus.domain.member.entity.Member;
import shlee.exam.idus.domain.member.repository.DeniedTokenRepository;
import shlee.exam.idus.domain.member.repository.MemberRepository;
import shlee.exam.idus.global.exception.exceptions.MemberEmailDuplicateException;
import shlee.exam.idus.global.exception.exceptions.MemberNotFoundException;
import shlee.exam.idus.global.exception.exceptions.MemberPasswordNotMatchFountException;
import shlee.exam.idus.global.jwt.JwtToken;
import shlee.exam.idus.global.jwt.JwtTokenProvider;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final DeniedTokenRepository deniedTokenRepository;

    //회원 생성
    @Transactional
    public Member joinMember(PostMemberDto postMemberDto) {
        validMemberEmailDuplicate(postMemberDto.getEmail());
        postMemberDto.validation();
        postMemberDto.encodePassword(passwordEncoder);

        return memberRepository.save(postMemberDto.toEntity());
    }

    //회원 아이디 중복검증
    private void validMemberEmailDuplicate(String email) {
        if (memberRepository.existsByEmail(email))
            throw new MemberEmailDuplicateException("이미 사용중인 이메일 입니다.");
    }

    //회원 로그인
    public JwtToken loginMember(LoginMemberDto loginMemberDto) {
        MemberAccount account = memberRepository.findMemberAccountByEmail(loginMemberDto.getEmail())
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 사용자 입니다."));

        validPassword(loginMemberDto.getPassword(), account.getPassword());

        return jwtTokenProvider.createToken(loginMemberDto.getEmail());
    }

    //비밀번호 검증
    private void validPassword(String requestPassword, String accountPassword) {
        if (!passwordEncoder.matches(requestPassword, accountPassword))
            throw new MemberPasswordNotMatchFountException("비밀번호가 일치하지 않습니다.");
    }

    //회원 로그아웃
    public String logoutMember(String memberEmail, String accessToken) {
        return deniedTokenRepository.save(DeniedToken.builder()
                .token(accessToken)
                .memberEmail(memberEmail)
                .build()
        ).getMemberEmail();
    }

    public MemberDetailInfo readMemberDetail(String memberEmail) {
        return memberRepository.findMemberDetailInfoByEmail(memberEmail)
                .orElseThrow(() -> new MemberNotFoundException("권한이 없습니다."));
    }

}
