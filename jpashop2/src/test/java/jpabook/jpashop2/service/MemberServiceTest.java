package jpabook.jpashop2.service;

import jpabook.jpashop2.domain.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    void 회원가입() throws Exception {
        // given
        Member member = new Member();
        member.setName("kim");

        // when
        Long saveId = memberService.join(member);

        // then
        Assertions.assertEquals(member, memberRepository.findOne(saveId));
     }

     @Test
     void 중복_회원_예외() throws Exception {
         // given
         Member member1 = new Member();
         member1.setName("kim");

         Member member2 = new Member();
         member2.setName("kim");

         // when
         try{
             memberService.join(member1);
             memberService.join(member2);
         } catch (Exception e) {
             System.out.println("예외가 발생해야 한다.");

         }

         // then


      }

}