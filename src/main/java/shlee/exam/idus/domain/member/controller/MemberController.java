package shlee.exam.idus.domain.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import shlee.exam.idus.domain.member.dto.domain.MemberDetailInfo;
import shlee.exam.idus.domain.member.dto.request.LoginMemberDto;
import shlee.exam.idus.domain.member.dto.request.PostMemberDto;
import shlee.exam.idus.domain.member.service.MemberService;
import shlee.exam.idus.global.jwt.JwtToken;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("")
    public ResponseEntity<Long> joinMember(@Valid @RequestBody PostMemberDto postMemberDto) {
        return new ResponseEntity<>(memberService.joinMember(postMemberDto).getId(), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtToken> loginMember(@Valid @RequestBody LoginMemberDto loginMemberDto) {
        return new ResponseEntity<>(memberService.loginMember(loginMemberDto), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logoutMember() {
        String accessToken = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        String memberEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return new ResponseEntity<>(memberService.logoutMember(memberEmail, accessToken), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<MemberDetailInfo> readMemberDetail() {
        String memberEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("memberEmail = " + memberEmail);
        return new ResponseEntity<>(memberService.readMemberDetail(memberEmail), HttpStatus.OK);
    }
}
