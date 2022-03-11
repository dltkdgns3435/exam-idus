package shlee.exam.idus.domain.order.repository;

import shlee.exam.idus.domain.order.dto.domain.MemberOrderDetail;
import shlee.exam.idus.domain.order.dto.domain.OrderDetail;
import shlee.exam.idus.domain.order.dto.request.MemberOrderQuery;

import java.util.List;

public interface OrderQuerydslRepository {
    List<OrderDetail> findOrderDetailByMemberEmail(String memberEmail);
    List<MemberOrderDetail> findMemberOrderDetailListBy(MemberOrderQuery query);
}
