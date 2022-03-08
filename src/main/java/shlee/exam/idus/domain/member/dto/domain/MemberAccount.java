package shlee.exam.idus.domain.member.dto.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MemberAccount {
    private String email;
    private String password;
}
