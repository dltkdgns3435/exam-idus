package shlee.exam.idus.domain.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shlee.exam.idus.domain.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, String>, OrderQuerydslRepository {

}
