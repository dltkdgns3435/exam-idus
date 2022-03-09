package shlee.exam.idus.domain.member.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Builder
@Getter
@ToString
@RedisHash("denied_token")
public class DeniedToken implements Serializable {
    @Id
    private String token;
    private String memberEmail;
}
