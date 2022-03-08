package shlee.exam.idus.domain.member.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;
import shlee.exam.idus.domain.member.enums.Sex;

import javax.persistence.*;

@Getter
@SequenceGenerator(
        name = "member_id_seq",
        sequenceName = "idx_member"
)
@NoArgsConstructor
@Entity(name = "member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_id_seq")
    private Long id;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(length = 30, nullable = false)
    private String nickname;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 20, nullable = false)
    private String phone;

    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @Column(length = 1)
    @Enumerated(EnumType.STRING)
    private Sex sex;

    @Builder
    public Member(String name, String nickname, String password, String phone, String email, Sex sex) {
        Assert.notNull(name, "이름은 필수값 입니다.");
        Assert.notNull(nickname, "별명은 필수값 입니다.");
        Assert.notNull(password, "비밀번호는 필수값 입니다.");
        Assert.notNull(phone, "전화번호는 필수값 입니다.");
        Assert.notNull(email, "이메일은 필수값 입니다.");

        this.name = name;
        this.nickname = nickname;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.sex = sex;
    }
}
