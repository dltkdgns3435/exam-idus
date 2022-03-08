package shlee.exam.idus.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import shlee.exam.idus.domain.member.dto.request.PostMemberDto;
import shlee.exam.idus.domain.member.entity.Member;
import shlee.exam.idus.domain.member.repository.MemberRepository;
import shlee.exam.idus.global.exception.exceptions.MemberEmailDuplicateException;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    //회원 생성
    public Member joinMember(PostMemberDto postMemberDto){
        validMemberEmailDuplicate(postMemberDto.getEmail());

        return memberRepository.save(postMemberDto.toEntity());
    }

    //회원 아이디 중복검증
    private void validMemberEmailDuplicate(String email){
        if(memberRepository.existsByEmail(email))
            throw new MemberEmailDuplicateException("이미 사용중인 이메일 입니다.");
    }
}
