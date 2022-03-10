package shlee.exam.idus.domain.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.filter.CharacterEncodingFilter;
import shlee.exam.idus.domain.member.dto.request.LoginMemberDto;
import shlee.exam.idus.domain.member.dto.request.PostMemberDto;
import shlee.exam.idus.domain.member.service.MemberService;
import shlee.exam.idus.global.exception.handler.CommonControllerAdvice;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MemberControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private MemberController memberController;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MemberService memberService;

    private final String email = "dltkdgns3435@kakao.com";
    private final String name = "이상훈";
    private final String nickname = "lee";
    private final String password = "z4319495A!";
    private final String phone = "01068283435";
    private final String sex = "M";

    @BeforeAll
    public void setupMvcBuilder() {
        mvc = MockMvcBuilders
                .standaloneSetup(memberController)
                .setControllerAdvice(new CommonControllerAdvice())
                .addFilter(new CharacterEncodingFilter("utf-8", true))
                .build();
    }

    @BeforeAll
    public void setupMember() {
        PostMemberDto postMemberDto = PostMemberDto.builder()
                .name(name)
                .nickname(nickname)
                .password(password)
                .phone(phone)
                .email(email)
                .sex(sex)
                .build();
        memberService.joinMember(postMemberDto);
    }

    @Nested
    @DisplayName("회원가입")
    class JoinMember {
        @Nested
        @DisplayName("정상 케이스")
        class SuccessCase {
            @Test
            @DisplayName("회원가입에 성공한다.")
            void joinMember() throws Exception {
                //given
                PostMemberDto postMemberDto = PostMemberDto.builder()
                        .name("아이디어스")
                        .nickname("idus")
                        .password("passWord1!")
                        .phone("01022223333")
                        .email("back@backpack.com")
                        .build();

                String requestBody = objectMapper.writeValueAsString(postMemberDto);

                //when
                MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/member")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                ).andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();

                //then
                assertThat(result.getResponse().getContentAsString()).isNotNull();
                System.out.println("result.getResponse().getContentAsString() = " + result.getResponse().getContentAsString());
            }
        }

        @Nested
        @DisplayName("비정상 케이스")
        class FailCase {
            @Test
            @DisplayName("같은 이메일로 중복 회원가입할 수 없다.")
            void joinMemberFailByMemberEmailDuplicate() throws Exception {
                //given
                PostMemberDto postMemberDto = PostMemberDto.builder()
                        .name(name)
                        .nickname(nickname)
                        .password(password)
                        .phone(phone)
                        .email(email)
                        .build();

                String requestBody = objectMapper.writeValueAsString(postMemberDto);

                //when
                MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/member")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                ).andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();

                //then
                assertThat(result.getResponse().getContentAsString()).isEqualTo("이미 사용중인 이메일 입니다.");
                System.out.println("result.getResponse().getContentAsString() = " + result.getResponse().getContentAsString());
            }

        }
    }

    @Nested
    @DisplayName("로그인")
    class LoginMember {
        @Nested
        @DisplayName("정상 케이스")
        class SuccessCase {
            @Test
            @DisplayName("로그인에 성공한다.")
            void loginMember() throws Exception {
                //given
                LoginMemberDto loginMemberDto = LoginMemberDto.builder()
                        .password(password)
                        .email(email)
                        .build();

                String requestBody = objectMapper.writeValueAsString(loginMemberDto);

                //when
                MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/member/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

                //then
                assertThat(result.getResponse().getContentAsString()).isNotNull();
                System.out.println("result.getResponse().getContentAsString() = " + result.getResponse().getContentAsString());
            }
        }

        @Nested
        @DisplayName("비정상 케이스")
        class FailCase {
            @Test
            @DisplayName("패스워드가 틀리면 로그인에 실패한다.")
            void loginMemberFailByWrongPassword() throws Exception {
                //given
                LoginMemberDto loginMemberDto = LoginMemberDto.builder()
                        .password(password + "wrong")
                        .email(email)
                        .build();

                String requestBody = objectMapper.writeValueAsString(loginMemberDto);

                //when
                MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/member/login")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                ).andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();

                //then
                assertThat(result.getResponse().getContentAsString()).isEqualTo("비밀번호가 일치하지 않습니다.");
                System.out.println("result.getResponse().getContentAsString() = " + result.getResponse().getContentAsString());
            }

            @Test
            @DisplayName("이메일이 틀리면 로그인에 실패한다.")
            void loginMemberFailByWrongMemberEmail() throws Exception {
                //given
                LoginMemberDto loginMemberDto = LoginMemberDto.builder()
                        .password(password)
                        .email(email + "wrong")
                        .build();

                String requestBody = objectMapper.writeValueAsString(loginMemberDto);

                //when
                MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/member/login")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                ).andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();

                //then
                assertThat(result.getResponse().getContentAsString()).isEqualTo("존재하지 않는 사용자 입니다.");
                System.out.println("result.getResponse().getContentAsString() = " + result.getResponse().getContentAsString());
            }
        }
    }


    @Nested
    @DisplayName("로그아웃")
    class LogoutMember {
        @Nested
        @DisplayName("정상 케이스")
        class SuccessCase {

            @Test
            @WithMockUser(username = email)
            @DisplayName("로그아웃에 성공한다.")
            void logoutMember() throws Exception {
                //given, when
                MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/member/logout")
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

                //then
                assertThat(result.getResponse().getContentAsString()).isEqualTo(email);
                System.out.println("result.getResponse().getContentAsString() = " + result.getResponse().getContentAsString());

            }
        }
    }

    @Nested
    @DisplayName("내정보 상세 조회")
    class ReadMemberDetail {
        @Nested
        @DisplayName("정상 케이스")
        class SuccessCase {

            @Test
            @WithMockUser(username = email)
            @DisplayName("내정보 상세 조회에 성공한다.")
            void readMemberDetail() throws Exception {
                //given, when
                MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/member")
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

                //then
                assertThat(result.getResponse().getContentAsString()).isNotNull();
                System.out.println("result.getResponse().getContentAsString() = " + result.getResponse().getContentAsString());

            }
        }

        @Nested
        @DisplayName("비정상 케이스")
        class FailCase {

            @Test
            @WithMockUser(username = email + "wrong")
            @DisplayName("인증값은 존재하나 사용자가 존재하지 않는경우 조회에 실패한다.")
            void readMemberDetail() throws Exception {
                //given, when
                MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/member")
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();

                //then
                assertThat(result.getResponse().getContentAsString()).isNotNull();
                System.out.println("result.getResponse().getContentAsString() = " + result.getResponse().getContentAsString());

            }
        }
    }
}