package shlee.exam.idus.domain.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import shlee.exam.idus.domain.order.dto.domain.MemberOrderDetail;
import shlee.exam.idus.domain.order.dto.domain.OrderDetail;
import shlee.exam.idus.domain.order.dto.request.MemberOrderQuery;
import shlee.exam.idus.domain.order.service.OrderService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/order/{memberEmail}")
    public ResponseEntity<List<OrderDetail>> readOrderDetail(@PathVariable String memberEmail){
        return new ResponseEntity<>(orderService.readOrderDetail(memberEmail), HttpStatus.OK);
    }

    @GetMapping("/order")
    public ResponseEntity<List<MemberOrderDetail>> findMemberOrderDetailListBy(@RequestBody MemberOrderQuery query){
        return new ResponseEntity<>(orderService.readMemberOrderDetailList(query), HttpStatus.OK);
    }


}
