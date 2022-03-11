package shlee.exam.idus.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shlee.exam.idus.domain.member.dto.domain.MemberAccount;
import shlee.exam.idus.domain.member.dto.domain.MemberDetailInfo;
import shlee.exam.idus.domain.member.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByEmail(String email);
    Optional<MemberAccount> findMemberAccountByEmail(String email);
    Optional<MemberDetailInfo> findMemberDetailInfoByEmail(String email);
    Optional<Member> findByEmail(String email);
}
