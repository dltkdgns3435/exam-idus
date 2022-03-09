package shlee.exam.idus.domain.member.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import shlee.exam.idus.domain.member.entity.DeniedToken;

@Repository
public interface DeniedTokenRepository extends CrudRepository<DeniedToken, String> {
}
