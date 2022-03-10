package shlee.exam.idus.domain.order.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import shlee.exam.idus.domain.order.dto.domain.OrderDetail;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static shlee.exam.idus.domain.member.entity.QMember.member;
import static shlee.exam.idus.domain.order.entity.QOrder.order;

@RequiredArgsConstructor
public class OrderQuerydslRepositoryImpl implements OrderQuerydslRepository{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<OrderDetail> findOrderDetailByMemberEmail(String memberEmail) {
        return jpaQueryFactory
                .select(Projections.constructor(OrderDetail.class,
                        order.orderNo,
                        order.productName,
                        order.orderAt
                        )
                )
                .from(order)
                .innerJoin(order.member, member)
                .where(condition(memberEmail, member.email::eq))
                .fetch();
    }


    private <T> BooleanExpression condition(T value, Function<T, BooleanExpression> function) {
        return Optional.ofNullable(value).map(function).orElse(null);
    }
}
