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
import shlee.exam.idus.domain.order.dto.domain.MemberOrderDetail;
import shlee.exam.idus.domain.order.dto.domain.OrderDetail;
import shlee.exam.idus.domain.order.dto.request.MemberOrderQuery;
import shlee.exam.idus.domain.order.entity.Order;

import javax.management.Query;
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

        PostMemberDto postMemberDto2 = PostMemberDto.builder()
                .name("테스트")
                .nickname("test")
                .password("testTEST1!")
                .phone("01022221111")
                .email("test@test.com")
                .sex("F")
                .build();
        Member member2 = memberService.joinMember(postMemberDto2);
        List<Order> orders2 = new ArrayList<>();

        Order order_2_1 = Order.builder()
                .productName("갤럭시탭")
                .build();

        Order order_2_2 = Order.builder()
                .productName("갤럭시버즈")
                .build();

        Order order_2_3 = Order.builder()
                .productName("갤럭시북")
                .build();

        Order order_2_4 = Order.builder()
                .productName("갤럭시워치")
                .build();

        order_2_1.registerMember(member2);
        order_2_2.registerMember(member2);
        order_2_3.registerMember(member2);
        order_2_4.registerMember(member2);

        orders2.add(order_2_1);
        orders2.add(order_2_2);
        orders2.add(order_2_3);
        orders2.add(order_2_4);

        orderRepository.saveAll(orders2);

        PostMemberDto postMemberDto3 = PostMemberDto.builder()
                .name("윈터")
                .nickname("winter")
                .password("winTer!@12")
                .phone("01012341234")
                .email("winter@weather.com")
                .sex("F")
                .build();
        memberService.joinMember(postMemberDto3);

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


    @Test
    @DisplayName("여러 회원 목록 조회 - 검색단어 없음")
    void findMemberOrderDetailListBy(){
        //given, when
        List<MemberOrderDetail> orderDetails = orderRepository.findMemberOrderDetailListBy(MemberOrderQuery.builder().build());

        //then
        assertThat(orderDetails.size()).isEqualTo(3);

        //print
        orderDetails.forEach(i -> System.out.println("i = " + i.toString()));
    }

    @Test
    @DisplayName("여러 회원 목록 조회 - 이름으로 검색")
    void findMemberOrderDetailListByWithMemberName(){
        //given
        MemberOrderQuery query = MemberOrderQuery.builder()
                .memberName(name)
                .build();

        //when
        List<MemberOrderDetail> orderDetails = orderRepository.findMemberOrderDetailListBy(query);

        //then
        assertThat(orderDetails.size()).isEqualTo(1);
        assertThat(orderDetails.get(0).getMemberName()).isEqualTo(name);
        assertThat(orderDetails.get(0).getProductName()).isEqualTo("맥북");

        //print
        orderDetails.forEach(i -> System.out.println("i = " + i.toString()));
    }

    @Test
    @DisplayName("여러 회원 목록 조회 - 이메일로 검색")
    void findMemberOrderDetailListByWithMemberEmail(){
        //given
        MemberOrderQuery query = MemberOrderQuery.builder()
                .memberEmail(email)
                .build();

        //when
        List<MemberOrderDetail> orderDetails = orderRepository.findMemberOrderDetailListBy(query);

        //then
        assertThat(orderDetails.size()).isEqualTo(1);
        assertThat(orderDetails.get(0).getMemberName()).isEqualTo(name);
        assertThat(orderDetails.get(0).getProductName()).isEqualTo("맥북");

        //print
        orderDetails.forEach(i -> System.out.println("i = " + i.toString()));
    }

    @Test
    @DisplayName("여러 회원 목록 조회 - 이름, 이메일로 검색")
    void findMemberOrderDetailListByWithMemberNameAndMemberEmail(){
        //given
        MemberOrderQuery query = MemberOrderQuery.builder()
                .memberName(name)
                .memberEmail(email)
                .build();

        //when
        List<MemberOrderDetail> orderDetails = orderRepository.findMemberOrderDetailListBy(query);

        //then
        assertThat(orderDetails.size()).isEqualTo(1);
        assertThat(orderDetails.get(0).getMemberName()).isEqualTo(name);
        assertThat(orderDetails.get(0).getProductName()).isEqualTo("맥북");

        //print
        orderDetails.forEach(i -> System.out.println("i = " + i.toString()));
    }


    @Test
    @DisplayName("여러 회원 목록 조회 - 이름, 이메일로 검색")
    void findMemberOrderDetailListByWithMemberNameAndMemberEmailNotFound(){
        //given
        MemberOrderQuery query = MemberOrderQuery.builder()
                .memberName("윈터")
                .memberEmail(email)
                .build();

        //when
        List<MemberOrderDetail> orderDetails = orderRepository.findMemberOrderDetailListBy(query);

        //then
        assertThat(orderDetails.size()).isEqualTo(0);
    }

}