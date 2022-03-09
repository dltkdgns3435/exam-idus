package shlee.exam.idus.domain.member.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.context.annotation.Import;
import shlee.exam.idus.config.RedisConfig;
import shlee.exam.idus.domain.member.entity.DeniedToken;

import static org.assertj.core.api.Assertions.assertThat;

@Import(RedisConfig.class)
@DataRedisTest
class DeniedTokenRepositoryTest {
    @Autowired
    private DeniedTokenRepository deniedTokenRepository;

    @Test
    @DisplayName("jwt 토큰 관리 redis 저장, 조회 테스트")
    void save_find() {
        //given
        final String token = "access-token-1234";
        final String memberEmail = "dltkdgns3435@kakao.com";


        //when
        deniedTokenRepository.save(DeniedToken.builder()
                .token(token)
                .memberEmail(memberEmail)
                .build());
        

        final DeniedToken deniedToken = deniedTokenRepository.findById(token).orElseThrow();

        // then
        assertThat(deniedToken.getToken()).isEqualTo(token);
        assertThat(deniedToken.getMemberEmail()).isEqualTo(memberEmail);
    }
}