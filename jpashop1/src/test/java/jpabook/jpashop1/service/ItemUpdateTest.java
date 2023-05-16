package jpabook.jpashop1.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop1.domain.item.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ItemUpdateTest {

    @Autowired
    EntityManager em;

    @Test
    void updateTest() throws Exception {
        // given
        Book book = em.find(Book.class, 1L);

        // when
        book.setName("asfafsdf");

        // 변경감지 == dirty checking

        // then


     }
}
