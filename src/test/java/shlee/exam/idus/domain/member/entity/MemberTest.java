package shlee.exam.idus.domain.member.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import shlee.exam.idus.domain.member.enums.Sex;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class MemberTest {
    private final String name = "이상훈";
    private final String nickname = "karry";
    private final String password = "Dltkdgns1@";
    private final String phone = "01068283435";
    private final String email = "dltkdgns3435@kakao.com";
    private final Sex sex = Sex.M;

    @Nested
    @DisplayName("사용자 entity 생성 검증")
    class ValidationPostMemberDto {

        @Nested
        @DisplayName("정상 케이스")
        class SuccessCase {
            @Test
            @DisplayName("정상적인 값이면 entity 생성에 성공한다.")
            void createMemberEntity() {
                //given, when
                Member member = Member.builder()
                        .name(name)
                        .nickname(nickname)
                        .password(password)
                        .phone(phone)
                        .email(email)
                        .sex(sex)
                        .build();

                //then
                assertThat(member.getName()).isEqualTo(name);
                assertThat(member.getNickname()).isEqualTo(nickname);
                assertThat(member.getPassword()).isEqualTo(password);
                assertThat(member.getPhone()).isEqualTo(phone);
                assertThat(member.getEmail()).isEqualTo(email);
                assertThat(member.getSex()).isEqualTo(sex);
            }

            @Test
            @DisplayName("성별값이 없어도 entity 생성에 성공한다.")
            void createMemberEntitySuccessWithOutSex() {
                //given, when
                Member member = Member.builder()
                        .name(name)
                        .nickname(nickname)
                        .password(password)
                        .phone(phone)
                        .email(email)
                        .build();

                //then
                assertThat(member.getName()).isEqualTo(name);
                assertThat(member.getNickname()).isEqualTo(nickname);
                assertThat(member.getPassword()).isEqualTo(password);
                assertThat(member.getPhone()).isEqualTo(phone);
                assertThat(member.getEmail()).isEqualTo(email);
            }
        }

        @Nested
        @DisplayName("비정상 케이스")
        class FailCase {
            @Test
            @DisplayName("이름이 없으면 entity 생성에 실패한다.")
            void createMemberEntityFailByNameIsNull() {
                //given, when
                IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                        Member.builder()
                                .nickname(nickname)
                                .password(password)
                                .phone(phone)
                                .email(email)
                                .sex(sex)
                                .build()
                );

                //then
                assertThat(exception.getMessage()).isEqualTo("이름은 필수값 입니다.");
            }

            @Test
            @DisplayName("별명이 없으면 entity 생성에 실패한다.")
            void createMemberEntityFailByNicknameIsNull() {
                //given, when
                IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                        Member.builder()
                                .name(name)
                                .password(password)
                                .phone(phone)
                                .email(email)
                                .sex(sex)
                                .build()
                );

                //then
                assertThat(exception.getMessage()).isEqualTo("별명은 필수값 입니다.");
            }

            @Test
            @DisplayName("비밀번호가 없으면 entity 생성에 실패한다.")
            void createMemberEntityFailByPasswordIsNull() {
                //given, when
                IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                        Member.builder()
                                .name(name)
                                .nickname(nickname)
                                .phone(phone)
                                .email(email)
                                .sex(sex)
                                .build()
                );

                //then
                assertThat(exception.getMessage()).isEqualTo("비밀번호는 필수값 입니다.");
            }

            @Test
            @DisplayName("전화번호가 없으면 entity 생성에 실패한다.")
            void createMemberEntityFailByPhoneIsNull() {
                //given, when
                IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                        Member.builder()
                                .name(name)
                                .nickname(nickname)
                                .password(password)
                                .email(email)
                                .sex(sex)
                                .build()
                );

                //then
                assertThat(exception.getMessage()).isEqualTo("전화번호는 필수값 입니다.");
            }

            @Test
            @DisplayName("이메일이 없으면 entity 생성에 실패한다.")
            void createMemberEntityFailByEmailIsNull() {
                //given, when
                IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                        Member.builder()
                                .name(name)
                                .nickname(nickname)
                                .password(password)
                                .phone(phone)
                                .sex(sex)
                                .build()
                );

                //then
                assertThat(exception.getMessage()).isEqualTo("이메일은 필수값 입니다.");
            }
        }
    }

}