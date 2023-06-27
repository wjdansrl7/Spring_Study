package study.querydsl2.repository;

import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl2.dto.MemberSearchCondition;
import study.querydsl2.dto.MemberTeamDto;
import study.querydsl2.entity.Member;
import study.querydsl2.entity.Team;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    MemberRepository memberRepository;

    @Test
    void basicTest() throws Exception {
        // given
        Member member = new Member("member1", 10);
        memberRepository.save(member);

        Member findMember = memberRepository.findById(member.getId()).get();
        assertThat(findMember).isEqualTo(member);


        List<Member> result1 = memberRepository.findAll();
        assertThat(result1).extracting("username").containsExactly("member1");

        List<Member> result2 = memberRepository.findByUsername("member1");
        assertThat(result2).extracting("username").containsExactly("member1");

        // when


        // then
     }

     @Test
     void searchTest() throws Exception {
         // given
         Team teamA = new Team("teamA");
         Team teamB = new Team("teamB");
         em.persist(teamA);
         em.persist(teamB);

         Member member1 = new Member("member1", 10, teamA);
         Member member2 = new Member("member2", 20, teamA);
         Member member3 = new Member("member3", 30, teamB);
         Member member4 = new Member("member4", 40, teamB);

         em.persist(member1);
         em.persist(member2);
         em.persist(member3);
         em.persist(member4);

         MemberSearchCondition condition = new MemberSearchCondition();
         condition.setAgeGoe(35);
         condition.setAgeLoe(40);
         condition.setTeamName("teamB");

         List<MemberTeamDto> result = memberRepository.search(condition);
         assertThat(result).extracting("username").containsExactly("member4");


         // when


         // then
      }



}