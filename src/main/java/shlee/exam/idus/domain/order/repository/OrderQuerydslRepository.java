package shlee.exam.idus.domain.order.repository;

import shlee.exam.idus.domain.order.dto.domain.OrderDetail;

import java.util.List;

public interface OrderQuerydslRepository {
    List<OrderDetail> findOrderDetailByMemberEmail(String memberEmail);
}
