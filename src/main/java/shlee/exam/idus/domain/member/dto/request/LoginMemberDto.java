package shlee.exam.idus.domain.member.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginMemberDto {
    @NotEmpty(message = "비밀번호는 필수값 입니다.")
    private String password;

    @NotEmpty(message = "이메일은 필수값 입니다.")
    @Email(message = "올바른 형식의 이메일을 입력해주세요.")
    private String email;
}
