package shlee.exam.idus.domain.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shlee.exam.idus.domain.order.dto.domain.MemberOrderDetail;
import shlee.exam.idus.domain.order.dto.domain.OrderDetail;
import shlee.exam.idus.domain.order.dto.request.MemberOrderQuery;
import shlee.exam.idus.domain.order.repository.OrderRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    //단일 회원의 주문 목록 조회
    public List<OrderDetail> readOrderDetail(String memberEmail){
        return orderRepository.findOrderDetailByMemberEmail(memberEmail);
    }

    //여러 회원 목록 조회
    public List<MemberOrderDetail> readMemberOrderDetailList(MemberOrderQuery query){
        return orderRepository.findMemberOrderDetailListBy(query);
    }
}
