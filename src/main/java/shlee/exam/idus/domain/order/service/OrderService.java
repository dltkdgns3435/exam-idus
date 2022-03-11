package shlee.exam.idus.domain.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shlee.exam.idus.domain.member.entity.Member;
import shlee.exam.idus.domain.member.repository.MemberRepository;
import shlee.exam.idus.domain.order.dto.domain.MemberOrderDetail;
import shlee.exam.idus.domain.order.dto.domain.OrderDetail;
import shlee.exam.idus.domain.order.dto.request.MemberOrderQuery;
import shlee.exam.idus.domain.order.entity.Order;
import shlee.exam.idus.domain.order.repository.OrderRepository;
import shlee.exam.idus.global.exception.exceptions.MemberNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public String createOrder(String productName, String memberEmail){
        Order order = Order.builder().productName(productName).build();
        Member member = memberRepository.findByEmail(memberEmail)
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 사용자 입니다."));

        order.registerMember(member);
        return order.getOrderNo();
    }

    //단일 회원의 주문 목록 조회
    @Transactional(readOnly = true)
    public List<OrderDetail> readOrderDetail(String memberEmail){
        return orderRepository.findOrderDetailByMemberEmail(memberEmail);
    }

    //여러 회원 목록 조회
    @Transactional(readOnly = true)
    public List<MemberOrderDetail> readMemberOrderDetailList(MemberOrderQuery query){
        return orderRepository.findMemberOrderDetailListBy(query);
    }
}
