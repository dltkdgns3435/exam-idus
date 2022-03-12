package shlee.exam.idus.domain.order.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import shlee.exam.idus.domain.order.dto.domain.MemberOrderDetail;
import shlee.exam.idus.domain.order.dto.domain.OrderDetail;
import shlee.exam.idus.domain.order.dto.request.MemberOrderQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static shlee.exam.idus.domain.member.entity.QMember.member;
import static shlee.exam.idus.domain.order.entity.QOrder.order;

@RequiredArgsConstructor
public class OrderQuerydslRepositoryImpl implements OrderQuerydslRepository {
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

    @Override
    public List<MemberOrderDetail> findMemberOrderDetailListBy(MemberOrderQuery query) {
        JPAQuery<MemberOrderDetail> jpaQuery = jpaQueryFactory
                .select(Projections.constructor(MemberOrderDetail.class,
                                member.name,
                                member.nickname,
                                member.email,
                                member.phone,
                                order.orderNo,
                                order.productName,
                                order.orderAt
                        )
                )
                .from(member)
                .leftJoin(member.orders, order)
                .where(order.orderAt.eq(JPAExpressions
                                        .select(order.orderAt.max())
                                        .from(order)
                                        .where(order.member.eq(member)
                                        )
                                )
                                .or(member.orders.isEmpty())
                );


        if (query.getMemberName() != null){
            jpaQuery.where(member.name.eq(query.getMemberName()));
        }

        if (query.getMemberEmail() != null){
            jpaQuery.where(member.email.eq(query.getMemberEmail()));
        }

        if(query.getSize() != 0){//페이징 설정시에만 페이징 처리 그 외에는 전부 조회
            jpaQuery.limit(query.getSize()).offset(query.getPage());
        }


            return jpaQuery.fetch();
    }


    private <T> BooleanExpression condition(T value, Function<T, BooleanExpression> function) {
        return Optional.ofNullable(value).map(function).orElse(null);
    }
}
