package jpabook.jpashop1.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop1.domain.Member;
import jpabook.jpashop1.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    @Rollback(false)
    void 회원가입() throws Exception {
        // given
        Member member = new Member();
        member.setName("kim222");

        // when
        Long saveId = memberService.join(member);

        // then
        Assertions.assertEquals(member, memberRepository.findById(saveId).get());
     }

     @Test
     void 중복_회원_관리() throws Exception {
         // given
         Member member1 = new Member();
         member1.setName("kim");

         Member member2 = new Member();
         member2.setName("kim");

         // when
         memberService.join(member1);
//         memberService.join(member2); // 예외가 발생해야 한다.

         Assertions.assertThrows(IllegalStateException.class, ()->{
             memberService.join(member2);
         });


         // then
//         fail("예외가 발생해야 한다.");

      }



}