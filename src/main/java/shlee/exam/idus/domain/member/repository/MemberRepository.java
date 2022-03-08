package shlee.exam.idus.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shlee.exam.idus.domain.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByEmail(String email);
}
