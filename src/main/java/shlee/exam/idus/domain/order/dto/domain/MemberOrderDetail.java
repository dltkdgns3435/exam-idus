package shlee.exam.idus.domain.order.dto.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@AllArgsConstructor
public class MemberOrderDetail {
    private String memberName;
    private String memberNickname;
    private String memberEmail;
    private String memberPhone;
    private String orderNo;
    private String productName;
    private LocalDateTime orderAt;

}
