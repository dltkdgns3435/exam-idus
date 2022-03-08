package shlee.exam.idus.domain.member.dto.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import shlee.exam.idus.domain.member.entity.Member;
import shlee.exam.idus.domain.member.enums.Sex;
import shlee.exam.idus.global.exception.exceptions.RequestValidationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PostMemberDtoTest {
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final String name = "이상훈";
    private final String nickname = "karry";
    private final String password = "Dltkdgns1@";
    private final String phone = "01068283435";
    private final String email = "dltkdgns3435@kakao.com";
    private final String sex = "M";

    @Nested
    @DisplayName("사용자 생성 dto 검증")
    class ValidationPostMemberDto {

        @Nested
        @DisplayName("정상 케이스")
        class SuccessCase {
            @Test
            @DisplayName("정상적인 값이면 검증에 성공한다.")
            void validation() {
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
                postMemberDto.validation();
                postMemberDto.encodePassword(passwordEncoder);
                Member member = postMemberDto.toEntity();

                //then
                assertThat(member.getName()).isEqualTo(name);
                assertThat(member.getNickname()).isEqualTo(nickname);
                assertThat(passwordEncoder.matches(password, member.getPassword())).isTrue();
                assertThat(member.getPhone()).isEqualTo(phone);
                assertThat(member.getEmail()).isEqualTo(email);
                assertThat(member.getSex()).isEqualTo(Sex.valueOf(sex));
            }
        }

        @Nested
        @DisplayName("비정상 케이스")
        class FailCase {
            @Test
            @DisplayName("이름이 한글, 영문 대소문자가 아니면 실패한다.")
            void validationFailByName() {
                //given
                PostMemberDto postMemberDto = PostMemberDto.builder()
                        .name("123")
                        .nickname(nickname)
                        .password(password)
                        .phone(phone)
                        .email(email)
                        .sex(sex)
                        .build();

                //when
                RequestValidationException exception = assertThrows(RequestValidationException.class, postMemberDto::validation);

                //then
                assertThat(exception.getMessage()).isEqualTo("이름은 한글, 영문 대소문자만 입력 가능합니다.");

            }

            @Test
            @DisplayName("별명이 영문 소문자가 아니면 실패한다.")
            void validationFailByNickname() {
                //given
                PostMemberDto postMemberDto = PostMemberDto.builder()
                        .name(name)
                        .nickname("KARRY")
                        .password(password)
                        .phone(phone)
                        .email(email)
                        .sex(sex)
                        .build();

                //when
                RequestValidationException exception = assertThrows(RequestValidationException.class, postMemberDto::validation);

                //then
                assertThat(exception.getMessage()).isEqualTo("별명은 영문 소문자만 입력 가능합니다.");

            }

            @Test
            @DisplayName("비밀번호가 영문 대문자, 영문 소문자, 특수 문자, 숫자 각 1개 이상씩 포함되지 않으면 실패한다.")
            void validationFailByPassword() {
                //given
                PostMemberDto postMemberDto = PostMemberDto.builder()
                        .name(name)
                        .nickname(nickname)
                        .password("1q2w3e4r")
                        .phone(phone)
                        .email(email)
                        .sex(sex)
                        .build();

                //when
                RequestValidationException exception = assertThrows(RequestValidationException.class, postMemberDto::validation);

                //then
                assertThat(exception.getMessage()).isEqualTo("비밀번호는 영문 대문자, 영문 소문자, 특수 문자, 숫자 각 1개 이상씩 포함해야 합니다.");

            }

            @Test
            @DisplayName("휴대폰번호가 숫자가 아니면 실패한다.")
            void validationFailByPhone() {
                //given
                PostMemberDto postMemberDto = PostMemberDto.builder()
                        .name(name)
                        .nickname(nickname)
                        .password(password)
                        .phone("010-6828-3435")
                        .email(email)
                        .sex(sex)
                        .build();

                //when
                RequestValidationException exception = assertThrows(RequestValidationException.class, postMemberDto::validation);

                //then
                assertThat(exception.getMessage()).isEqualTo("휴대폰번호는 '-' 제외 숫자만 입력해주세요.");

            }

            @Test
            @DisplayName("이메일 형식이 바르지 않으면 실패한다.")
            void validationFailByEmail() {
                //given
                PostMemberDto postMemberDto = PostMemberDto.builder()
                        .name(name)
                        .nickname(nickname)
                        .password(password)
                        .phone(phone)
                        .email("dltkdgns3435@kakao")
                        .sex(sex)
                        .build();

                //when
                RequestValidationException exception = assertThrows(RequestValidationException.class, postMemberDto::validation);

                //then
                assertThat(exception.getMessage()).isEqualTo("올바른 형식의 이메일을 입력해주세요.");

            }
        }
    }
}