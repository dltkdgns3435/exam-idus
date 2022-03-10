package shlee.exam.idus.domain.order.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import shlee.exam.idus.domain.member.entity.Member;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity(name = "orders")
@EntityListeners(AuditingEntityListener.class)
@Table(indexes = {
        @Index(name = "idx_order_orderno", columnList = "orderNo")
})
public class Order {
    @Id
    @Column(length = 12, unique = true, nullable = false)
    private String orderNo;

    @Column(length = 100, nullable = false)
    private String productName;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime orderAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public void registerMember(Member member){
        this.member = member;
    }

    @Builder
    public Order(String productName) {
        //year ~ ms 단위 16진수 전환 및 길이 12미만의 부분 UUID 추가
        this.orderNo = Long.toHexString(Long.parseLong(LocalDateTime.now().toString().replaceAll("[^0-9]", "").substring(2, 14)));
        this.orderNo += UUID.randomUUID().toString().substring(0, 12 - this.orderNo.length());
        this.orderNo = this.orderNo.toUpperCase(Locale.ROOT);
        this.productName = productName;
    }
}
