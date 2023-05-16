package jpabook.jpashop1.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop1.domain.*;
import jpabook.jpashop1.domain.item.Book;
import jpabook.jpashop1.exception.NotEnoughStockException;
import jpabook.jpashop1.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

/*
좋은 테스트는 DB나 Spring dependency 없이 순수하게 단위 테스트로 메서드만
잘 동작하는지가 좋은 테스트이다.
 */

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Test
    void 상품주문() throws Exception {
        // given
        Member member = createMember();
        Item item = createBook("시골 JPA", 10000, 10);

        int orderCount = 2;

        // when
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        // then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.ORDER, getOrder.getStatus());
        assertEquals(1, getOrder.getOrderItems().size());
        assertEquals(10000 * orderCount, getOrder.getTotalPrice());
        assertEquals(8, item.getStockQuantity());


    }



    //    예외 테스트는 매우 중요하다.
    @Test
    void 상품주문_재고수량초과() throws Exception {
        // given
        Member member = createMember();
        Item item = createBook("도시 JPA", 10000, 5);
        int orderCount = 10;

        // when
//        orderService.order(member.getId(), item.getId(), orderCount);


        // then
        assertThrows(NotEnoughStockException.class, () ->
        {
            orderService.order(member.getId(), item.getId(), orderCount);
        });
     }

     @Test
     void 주문취소() throws Exception {
         // given
         Member member = createMember();
         Item item = createBook("위례 JPA", 15000, 10);
         int orderCount = 2;

         Long orderId = orderService.order(member.getId(), item.getId(), orderCount);
         // when
         orderService.cancelOrder(orderId);


         // then
         Order getOrder = orderRepository.findOne(orderId);

         assertEquals(10, item.getStockQuantity());
         assertEquals(OrderStatus.CANCEL, getOrder.getStatus());
     }


    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "강가", "123-123"));
        em.persist(member);
        return member;
    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);

        return book;
    }


}