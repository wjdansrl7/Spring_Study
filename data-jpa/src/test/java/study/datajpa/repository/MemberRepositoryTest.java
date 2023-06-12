package study.datajpa.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.hibernate.metamodel.mapping.EntityIdentifierMapping;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TeamRepository teamRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    void testMember() throws Exception {
        // given
        System.out.println("memberRepository.getClass() = " + memberRepository.getClass());
        Member member = new Member("memberA");
        Member savedMember = memberRepository.save(member);


        // when
        Member findMember = memberRepository.findById(member.getId()).get();



        // then
        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member);
     }

    @Test
    void basicCRUD() throws Exception {
        // given
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");

        memberRepository.save(member1);
        memberRepository.save(member2);

        // when
        Member findMember1 = memberRepository.findById(member1.getId()).get();
        Member findMember2 = memberRepository.findById(member2.getId()).get();

        // then
        // 단건 조회 검증
        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);

        findMember1.setUsername("member!!!!!!");



        //리스트 조회 검증
        List<Member> all = memberRepository.findAll();
        assertThat(all.size()).isEqualTo(2);

        // 카운트 검증
        long count = memberRepository.count();
        assertThat(count).isEqualTo(2);

        //삭제 검증
        memberRepository.delete(member1);
        memberRepository.delete(member2);

        long deletedCount = memberRepository.count();
        assertThat(deletedCount).isEqualTo(0);
    }

    @Test
    void findByUsernameAndAgeGreaterThen() throws Exception {
        // given
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("AAA", 20);

        memberRepository.save(m1);
        memberRepository.save(m2);

        // when
        List<Member> result = memberRepository.findByUsernameAndAgeGreaterThan("AAA", 15);


        // then
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getUsername()).isEqualTo("AAA");
        assertThat(result.get(0).getAge()).isEqualTo(20);


    }

    @Test
    void findHelloBy() throws Exception {
        // given
        List<Member> helloBy = memberRepository.findTop3HelloBy();

        // when


        // then


     }

     @Test
     void testNamedQuery() throws Exception {
         // given
         Member m1  = new Member("AAA", 10);
         Member m2 = new Member("BBB", 20);

         memberRepository.save(m1);
         memberRepository.save(m2);

         // when

         List<Member> result = memberRepository.findByUsername("AAA");
         Member findMember = result.get(0);


         // then
         assertThat(findMember).isEqualTo(m1);


     }

     @Test
     void testQuery() throws Exception {
         Member m1  = new Member("AAA", 10);
         Member m2 = new Member("BBB", 20);

         memberRepository.save(m1);
         memberRepository.save(m2);

         List<Member> result = memberRepository.findUser("AAA", 10);

         assertThat(result.get(0)).isEqualTo(m1);

     }

     @Test
     void findUsernameList() throws Exception {
         // given

         Member m1  = new Member("AAA", 10);
         Member m2 = new Member("BBB", 20);

         memberRepository.save(m1);
         memberRepository.save(m2);

         // when
         List<String> usernameList = memberRepository.findByUsernameList();
         for (String s : usernameList) {
             System.out.println("s = " + s);
         }


         // then
      }

    @Test
    void findMemberDto() throws Exception {
        // given

        Member m1  = new Member("AAA", 10);
        memberRepository.save(m1);

        Team team = new Team("teamA");
        teamRepository.save(team);

        m1.setTeam(team);

        // when
        List<MemberDto> memberDto = memberRepository.findMemberDto();

        for (MemberDto dto : memberDto) {
            System.out.println("dto = " + dto);
        }


        // then
    }

    @Test
    void findNames() throws Exception {
        // given

        Member m1  = new Member("AAA", 10);
        Member m2  = new Member("BBB", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);
        
        // when
        List<Member> result = memberRepository.findByNames(Arrays.asList("AAA", "BBB"));
        for (Member member : result) {
            System.out.println("member = " + member);
        }

        // then
    }

    @Test
    void returnType() throws Exception {
        // given

        Member m1  = new Member("AAA", 10);
        Member m2  = new Member("BBB", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        // when
//        List<Member> result = memberRepository.findListByUsername("dfafda");
        Member result = memberRepository.findMemberByUsername("dfafda");
        System.out.println("result = " + result);


        // then
    }

    @Test
    void paging() throws Exception {
        // given
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 10));
        memberRepository.save(new Member("member3", 10));
        memberRepository.save(new Member("member4", 10));
        memberRepository.save(new Member("member5", 10));

        int age = 10;
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));



        // when
        Page<Member> page = memberRepository.findByAge(age, pageRequest);

        Page<MemberDto> toMap = page.map(m -> new MemberDto(m.getId(), m.getUsername(), null));


        // 페이지 계산 공식 적용
        // totalPage = totalCount / size ..
        // 마지막 페이지
        // 최조 페이지 ...


        // then
        List<Member> content = page.getContent();
        long totalElements = page.getTotalElements();

        for (Member member : content) {
            System.out.println("member = " + member);
        }
        System.out.println("totalElements = " + totalElements);

        assertThat(content.size()).isEqualTo(3);
        assertThat(page.getTotalElements()).isEqualTo(5);
        assertThat(page.getNumber()).isEqualTo(0); // 페이지 번호
        assertThat(page.getTotalPages()).isEqualTo(2); //전체 페이지 개수
        assertThat(page.isFirst()).isTrue(); // 첫 번째 페이지인가?
        assertThat(page.hasNext()).isTrue(); // 다음 페이지가 있는가?
    }

    @Test
    void bulkUpdate() throws Exception {
        // given
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 19));
        memberRepository.save(new Member("member3", 20));
        memberRepository.save(new Member("member4", 21));
        memberRepository.save(new Member("member5", 40));

        // when
        int resultCount = memberRepository.bulkAgePlus(20);
//        em.flush();
//        em.clear();

        List<Member> result = memberRepository.findByUsername("member5");
        Member member5 = result.get(0);
        System.out.println("member5 = " + member5);

        // then
        assertThat(resultCount).isEqualTo(3);


    }

    @Test
    void findMemberLazy() throws Exception {
        // given
        // member1 -> teamA
        // member2 -> teamB

        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        teamRepository.save(teamA);
        teamRepository.save(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 10, teamB);
        memberRepository.save(member1);
        memberRepository.save(member2);

        em.flush();
        em.clear();

        // when
        List<Member> members = memberRepository.findAll();
//        List<Member> members = memberRepository.findMemberEntityGraph();

        for (Member member : members) {
            System.out.println("member = " + member.getUsername());
            System.out.println("member.getTeam().getClass( = " + member.getTeam().getClass());
            System.out.println("member.getTeam().getName( = " + member.getTeam().getName());
        }
        // then
     }

     @Test
     void queryHint() throws Exception {
         // given
         Member member1 = memberRepository.save(new Member("member1", 10));
         em.flush();
         em.clear();

         // when
         // 조회만 목적으로 사용한다고 hibernate에게 알려줘서 snapshot같은 것을 만들어두지 않는다.
         Member findMember = memberRepository.findReadOnlyByUsername("member1");
         findMember.setUsername("member2");

         em.flush();

         // then

      }

    @Test
    void lock() throws Exception {
        // given
        Member member1 = memberRepository.save(new Member("member1", 10));
        em.flush();
        em.clear();

        // when
        List<Member> findMember = memberRepository.findLockByUsername("member1");

        // then
    }

    @Test
    void callCustom() throws Exception {
        // given
        List<Member> memberCustom = memberRepository.findMemberCustom();


        // when


        // then


     }



}