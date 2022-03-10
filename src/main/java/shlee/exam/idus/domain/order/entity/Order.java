package shlee.exam.idus.domain.order.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity(name = "order")
public class Order {
    @Id
    @Column(length = 12, unique = true, nullable = false)
    private String id;

    @Column(length = 100, nullable = false)
    private String productName;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime orderAt;

    @Builder
    public Order(String productName, LocalDateTime orderAt) {
        //year ~ ms 단위 16진수 전환 및 길이 12미만의 부분 UUID 추가
        this.id = Long.toHexString(Long.parseLong(LocalDateTime.now().toString().replaceAll("[^0-9]", "").substring(2, 14)));
        this.id += UUID.randomUUID().toString().substring(0, 12 - this.id.length());
        this.id = this.id.toUpperCase(Locale.ROOT);
        this.productName = productName;
        this.orderAt = orderAt;
    }
}
