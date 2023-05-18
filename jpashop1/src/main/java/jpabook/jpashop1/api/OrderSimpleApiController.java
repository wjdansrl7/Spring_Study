package jpabook.jpashop1.api;

import jpabook.jpashop1.domain.Member;
import jpabook.jpashop1.domain.Order;
import jpabook.jpashop1.domain.OrderSearch;
import jpabook.jpashop1.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {
    private final OrderRepository orderRepository;

    /**
     * V1. 엔티티 직접 노출
     * Hibernate5Module 모듈 등록, LAZY = null 처리
     * jackson 라이브러리는 Proxy 객체를 어떻게 json으로 처리해야할지 몰라서 예외 처리가 발생한다.
     * 양방향 관계 문제 발생 -> @JsonIgnore
     */
    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAllByString(new OrderSearch());

        for (Order order : all) {
            order.getMember().getName();
            order.getDelivery().getAddress();
        }
        return all;
    }


}
