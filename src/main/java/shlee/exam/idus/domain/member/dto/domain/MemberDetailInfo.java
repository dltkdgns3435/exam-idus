package shlee.exam.idus.domain.member.dto.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import shlee.exam.idus.domain.member.enums.Sex;

@Getter
@Builder
@AllArgsConstructor
public class MemberDetailInfo {
    private String name;
    private String nickname;
    private String phone;
    private String email;
    private Sex sex;
}
