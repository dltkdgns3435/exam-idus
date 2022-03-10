package shlee.exam.idus.domain.order.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import shlee.exam.idus.domain.member.dto.request.PostMemberDto;
import shlee.exam.idus.domain.member.entity.Member;
import shlee.exam.idus.domain.member.repository.MemberRepository;
import shlee.exam.idus.domain.member.service.MemberService;
import shlee.exam.idus.domain.order.dto.domain.OrderDetail;
import shlee.exam.idus.domain.order.entity.Order;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OrderRepositoryTest {
    @Autowired
    private MemberService memberService;
    @Autowired
    private OrderRepository orderRepository;

    private final String email = "test@id.us.kr";
    private final String name = "이상훈";
    private final String nickname = "karry";
    private final String password = "z4319495A!";
    private final String phone = "01068283435";
    private final String sex = "M";

    @BeforeAll
    void setUpData() {
        PostMemberDto postMemberDto = PostMemberDto.builder()
                .name(name)
                .nickname(nickname)
                .password(password)
                .phone(phone)
                .email(email)
                .sex(sex)
                .build();
        Member member = memberService.joinMember(postMemberDto);
        List<Order> orders = new ArrayList<>();

        Order order_1 = Order.builder()
                .productName("아이패드")
                .build();

        Order order_2 = Order.builder()
                .productName("에어팟")
                .build();

        Order order_3 = Order.builder()
                .productName("맥북")
                .build();

        order_1.registerMember(member);
        order_2.registerMember(member);
        order_3.registerMember(member);

        orders.add(order_1);
        orders.add(order_2);
        orders.add(order_3);
        
        orderRepository.saveAll(orders);
    }
    
    @Test
    @DisplayName("단일 회원의 주문 목록 조회 성공")
    void findOrderDetailByMemberEmail(){
        //given, when
        List<OrderDetail> orderDetails = orderRepository.findOrderDetailByMemberEmail(email);

        //then
        assertThat(orderDetails.size()).isEqualTo(3);

        //print
        orderDetails.forEach(i -> System.out.println("i = " + i.toString()));
    }
}