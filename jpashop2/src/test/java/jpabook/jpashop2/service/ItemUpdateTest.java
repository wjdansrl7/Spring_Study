package jpabook.jpashop2.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop2.domain.item.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ItemUpdateTest {

    @Autowired
    EntityManager em;

    @Test
    void updateTest() throws Exception {
        // given
        Book book = em.find(Book.class, 1L);

        // TX
        book.setName("ajdladfjsk");

        // 변경감지 -> dirty checking
        // TX commit



        // when


        // then


     }



}
