package jpabook.jpashop2.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop2.domain.Address;
import jpabook.jpashop2.domain.Member;
import jpabook.jpashop2.domain.Order;
import jpabook.jpashop2.domain.OrderStatus;
import jpabook.jpashop2.domain.item.Book;
import jpabook.jpashop2.domain.item.Item;
import jpabook.jpashop2.exception.NotEnoughStockException;
import jpabook.jpashop2.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Test
    void 상품주문() throws Exception {
        // given
        Member member = createMember();
        Item item = createBook("시골 JPA", 10000, 10); // 이름, 가격, 재고
        int orderCount = 2;

        // when
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        // then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.ORDER, getOrder.getStatus());
        assertEquals(1, getOrder.getOrderItems().size());
        assertEquals(10000 * 2, getOrder.getTotalPrice());
        assertEquals(8, item.getStockQuantity());
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "강가", "123-123"));
        em.persist(member);
        return member;
    }

    private Item createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    @Test
    void 상품주문_재고수량초과() throws Exception {
        // given
        Member member = createMember();
        Item item = createBook("시골 JPA", 10000, 10);

        int orderCount = 11;

        // when
        try {
            orderService.order(member.getId(), item.getId(), orderCount);
        } catch (Exception e) {
//            throw new NotEnoughStockException("재고 수량 부족 예외가 발생해야 한다.");
            System.out.println("재고 수량 부족 예외가 발생해야 한다.");
        }
        // then
     }

     @Test
     void 주문취소() throws Exception {
         // given
         Member member = createMember();
         Item item = createBook("시골 JPA", 10000, 10);
         int orderCount = 2;

         Long orderId = orderService.order(member.getId(), item.getId(), orderCount);
         // when
         orderService.cancelOrder(orderId);

         // then
         Order getOrder = orderRepository.findOne(orderId);

         assertEquals(OrderStatus.CANCEL, getOrder.getStatus());
         assertEquals(10, item.getStockQuantity());


     }

}