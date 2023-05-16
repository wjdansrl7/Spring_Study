package jpabook.jpashop1.service;

import jpabook.jpashop1.domain.Item;
import jpabook.jpashop1.domain.item.Book;
import jpabook.jpashop1.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@Transactional
class ItemServiceTest {

    @Autowired ItemRepository itemRepository;
    @Autowired ItemService itemService;

    @Test
    void 상품주문() throws Exception {
        // given
        Item book = new Book();
        book.setId(1L);
        book.setName("JPA 프로그래밍");

        // when
        itemService.saveItem(book);

        // then


     }

}