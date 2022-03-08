package shlee.exam.idus.domain.member.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import shlee.exam.idus.domain.member.dto.domain.MemberAccount;
import shlee.exam.idus.domain.member.dto.request.LoginMemberDto;
import shlee.exam.idus.domain.member.dto.request.PostMemberDto;
import shlee.exam.idus.domain.member.entity.Member;
import shlee.exam.idus.domain.member.enums.Sex;
import shlee.exam.idus.domain.member.repository.MemberRepository;
import shlee.exam.idus.global.exception.exceptions.MemberEmailDuplicateException;
import shlee.exam.idus.global.exception.exceptions.MemberNotFountException;
import shlee.exam.idus.global.exception.exceptions.MemberPasswordNotMatchFountException;
import shlee.exam.idus.global.jwt.JwtToken;
import shlee.exam.idus.global.jwt.JwtTokenProvider;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {
    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final String name = "이상훈";
    private final String nickname = "karry";
    private final String password = "Dltkdgns1@";
    private final String phone = "01068283435";
    private final String email = "dltkdgns3435@kakao.com";
    private final String sex = "M";

    @Nested
    @DisplayName("사용자 생성 테스트")
    class JoinMember {

        @Nested
        @DisplayName("정상 케이스")
        class SuccessCase {
            @Test
            @DisplayName("사용자 생성에 성공한다.")
            void joinMember() {
                //given
                PostMemberDto postMemberDto = PostMemberDto.builder()
                        .name(name)
                        .nickname(nickname)
                        .password(password)
                        .phone(phone)
                        .email(email)
                        .sex(sex)
                        .build();

                //when
                when(memberRepository.existsByEmail(any())).thenReturn(false);
                when(memberRepository.save(any())).thenReturn(Member.builder()
                        .name(name)
                        .nickname(nickname)
                        .password(password)
                        .phone(phone)
                        .email(email)
                        .sex(Sex.valueOf(sex))
                        .build()
                );
                when(passwordEncoder.encode(password)).thenReturn("encoded-password");

                Member createdMember = memberService.joinMember(postMemberDto);

                //then
                assertThat(createdMember.getName()).isEqualTo(name);
                assertThat(createdMember.getNickname()).isEqualTo(nickname);
                assertThat(createdMember.getPassword()).isEqualTo(password);
                assertThat(createdMember.getPhone()).isEqualTo(phone);
                assertThat(createdMember.getEmail()).isEqualTo(email);
                assertThat(createdMember.getSex()).isEqualTo(Sex.valueOf(sex));
            }
        }

        @Nested
        @DisplayName("비정상 케이스")
        class FailCase {
            @Test
            @DisplayName("이미 사용중인 이메일이면 사용자 생성에 실패한다.")
            void joinMemberFailByEmailDuplicate() {
                PostMemberDto postMemberDto = PostMemberDto.builder()
                        .name(name)
                        .nickname(nickname)
                        .password(password)
                        .phone(phone)
                        .email(email)
                        .sex(sex)
                        .build();

                //when
                when(memberRepository.existsByEmail(any())).thenReturn(true);

                MemberEmailDuplicateException exception = assertThrows(MemberEmailDuplicateException.class, () -> memberService.joinMember(postMemberDto));

                //then
                assertThat(exception.getMessage()).isEqualTo("이미 사용중인 이메일 입니다.");

            }
        }
    }


    @Nested
    @DisplayName("사용자 로그인 테스트")
    class LoginMember {
        @Nested
        @DisplayName("정상 케이스")
        class SuccessCase {
            @Test
            @DisplayName("로그인에 성공한다.")
            void loginMember() {
                //given
                LoginMemberDto loginMemberDto = LoginMemberDto.builder()
                        .email(email)
                        .password(password)
                        .build();

                //when
                when(memberRepository.findMemberAccountByEmail(email))
                        .thenReturn(Optional.of(MemberAccount.builder()
                                .email(email)
                                .password(password)
                                .build()
                        ));
                when(passwordEncoder.matches(any(), any())).thenReturn(true);
                when(jwtTokenProvider.createToken(email))
                        .thenReturn(JwtToken.builder()
                                .accessToken("access-token")
                                .build()
                        );
                JwtToken jwtToken = memberService.loginMember(loginMemberDto);

                //then
                assertThat(jwtToken.getAccessToken()).isEqualTo("access-token");
            }
        }

        @Nested
        @DisplayName("비정상 케이스")
        class FailCase {
            @Test
            @DisplayName("회원이 존재하지 않으면 로그인에 실패한다.")
            void loginMemberFailByNotFoundMember() {
                //given
                LoginMemberDto loginMemberDto = LoginMemberDto.builder()
                        .email(email)
                        .password(password)
                        .build();

                //when
                when(memberRepository.findMemberAccountByEmail(email))
                        .thenReturn(Optional.empty());

                MemberNotFountException exception = assertThrows(MemberNotFountException.class, () -> memberService.loginMember(loginMemberDto));

                //then
                assertThat(exception.getMessage()).isEqualTo("존재하지 않는 사용자 입니다.");
            }

            @Test
            @DisplayName("비밀번호가 일치하지 않으면 로그인에 실패한다.")
            void loginMember() {
                //given
                LoginMemberDto loginMemberDto = LoginMemberDto.builder()
                        .email(email)
                        .password(password + "wrong")
                        .build();

                //when
                when(memberRepository.findMemberAccountByEmail(email))
                        .thenReturn(Optional.of(MemberAccount.builder()
                                .email(email)
                                .password(password)
                                .build()
                        ));

                MemberPasswordNotMatchFountException exception = assertThrows(MemberPasswordNotMatchFountException.class, () -> memberService.loginMember(loginMemberDto));

                //then
                assertThat(exception.getMessage()).isEqualTo("비밀번호가 일치하지 않습니다.");
            }
        }
    }


}