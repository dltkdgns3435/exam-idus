package shlee.exam.idus.domain.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import shlee.exam.idus.domain.order.dto.domain.MemberOrderDetail;
import shlee.exam.idus.domain.order.dto.domain.OrderDetail;
import shlee.exam.idus.domain.order.dto.request.MemberOrderQuery;
import shlee.exam.idus.domain.order.service.OrderService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/order/{productName}")
    public ResponseEntity<String> createOrder(@PathVariable String productName) {
        String memberEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return new ResponseEntity<>(orderService.createOrder(productName, memberEmail), HttpStatus.OK);
    }

    @GetMapping("/order/{memberEmail}")
    public ResponseEntity<List<OrderDetail>> readOrderDetail(@PathVariable String memberEmail) {
        return new ResponseEntity<>(orderService.readOrderDetail(memberEmail), HttpStatus.OK);
    }

    @GetMapping("/order")
    public ResponseEntity<List<MemberOrderDetail>> findMemberOrderDetailListBy(
            @RequestParam(required = false) String memberName,
            @RequestParam(required = false) String memberEmail,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "0") int size) {
        MemberOrderQuery query = MemberOrderQuery.builder()
                .memberName(memberName)
                .memberEmail(memberEmail)
                .page(page)
                .size(size)
                .build();


        return new ResponseEntity<>(orderService.readMemberOrderDetailList(query), HttpStatus.OK);
    }


}
