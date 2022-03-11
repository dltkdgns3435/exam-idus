package shlee.exam.idus.domain.order.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class MemberOrderQuery {
    private String memberName;
    private String memberEmail;
    private int page;
    private int size;
}
