package shlee.exam.idus.domain.order.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OrderTest {

    @Test
    @DisplayName("주문번호 생성 테스트")
    void generateIdTest(){
        //given, when
        Order order = Order.builder()
                .productName("에어팟프로")
                .build();
        
        //then, print
        System.out.println("order.getId() = " + order.getOrderNo());
        assertThat(order.getOrderNo().length()).isEqualTo(12);
    }

}