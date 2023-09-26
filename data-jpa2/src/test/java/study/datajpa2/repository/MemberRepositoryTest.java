package study.datajpa2.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa2.dto.UsernameOnly;
import study.datajpa2.dto.UsernameOnlyDto;
import study.datajpa2.entity.Member;
import study.datajpa2.entity.Team;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TeamRepository teamRepository;

    @Test
    void testMember() throws Exception {
        System.out.println("memberRepository.getClass() = " + memberRepository.getClass());
        // given
        Member member = new Member("memberA");
        Member savedMember = memberRepository.save(member);

        Member findMember = memberRepository.findById(savedMember.getId()).get();

        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getUsername()).isEqualTo("memberA");
        Assertions.assertThat(findMember).isEqualTo(member);

        // when


        // then
    }

    @Test
    void basicCRUD() throws Exception {
        // given
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");

        // when
        memberRepository.save(member1);
        memberRepository.save(member2);

        // then
        // 단건 조회 검증
        Member findMember1 = memberRepository.findById(member1.getId()).get();
        Member findMember2 = memberRepository.findById(member2.getId()).get();
        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);

        // 리스트 조회 검증
        List<Member> all = memberRepository.findAll();
        assertThat(all.size()).isEqualTo(2);

        // 카운트 검증
        long count = memberRepository.count();
        assertThat(count).isEqualTo(2);

        // 삭제 검증
        memberRepository.delete(member1);
        memberRepository.delete(member2);

        long deletedCount = memberRepository.count();
        assertThat(deletedCount).isEqualTo(0);
    }

    // 페이징 조건과 정렬 조건 설정
    @Test
    void page() throws Exception {
        // given
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 10));
        memberRepository.save(new Member("member3", 10));
        memberRepository.save(new Member("member4", 10));
        memberRepository.save(new Member("member5", 10));

        // when
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));
        PageRequest pageRequest1 = PageRequest.ofSize(5);
        Page<Member> page = memberRepository.findByAge(10, pageRequest);
        Page<Member> page2 = memberRepository.findMemberAllCountBy(pageRequest1);
        List<Member> top3By = memberRepository.findTop3By();

        // then
        List<Member> content = page.getContent(); // 조회된 데이터
        assertThat(content.size()).isEqualTo(3); // 조회된 데이터 수
        assertThat(page.getTotalElements()).isEqualTo(5); // 전체 데이터 수
        assertThat(page.getNumber()).isEqualTo(0); // 페이지 번호
        assertThat(page.getTotalPages()).isEqualTo(2); // 전체 페이지 번호
        assertThat(page.isFirst()).isTrue(); // 첫번째 항목인가?
        assertThat(page.hasNext()).isTrue(); // 다음 페이지가 있는가?

        assertThat(page2.getSize()).isEqualTo(5);
        for (Member member : top3By) {

            System.out.println("member = " + member);
        }
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
        memberRepository.save(new Member("member1", 10, teamA));
        memberRepository.save(new Member("member2", 20, teamB));

        em.flush();
        em.clear();

        // when
        List<Member> members = memberRepository.findAll();

        // then
        for (Member member : members) {
            // 지연 로딩 여부 확인

            // Hibernate 기능으로 확인
            // Hibernate.isInitialized(member.getTeam());

            // JPA 표준 방법으로 확인
//            PersistenceUnitUtil util = em.getEntityManagerFactory().getPersistenceUnitUtil();
//            util.isLoaded(member.getTeam());
            member.getTeam().getName();
        }
    }

    @Test
    void NamedEntityGraphTest() throws Exception {
        // given
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 19));
        memberRepository.save(new Member("member3", 20));
        memberRepository.save(new Member("member4", 21));
        memberRepository.save(new Member("member5", 40));

        memberRepository.findMemberEntityGraph();


        // when

        // then
     }

     @Test
     void queryHint() throws Exception {
         // given
         memberRepository.save(new Member("member1", 10));
         em.flush();
         em.clear();

         // when
         Member member = memberRepository.findReadOnlyByUsername("member1");
         member.setUsername("member2"); // Update Query 실행 X

         em.flush();
         // then
      }

      @Test
      void jpaEventBaseEntity() throws Exception {
          // given
          Member member = new Member("member1");
          memberRepository.save(member);

          Thread.sleep(100);
          member.setUsername("member2");

          em.flush(); //@PreUpdate
          em.clear();

          // when
          Member findMember = memberRepository.findById(member.getId()).get();

          // then
          System.out.println("findMember.createdDate = " + findMember.getCreatedDate());
          System.out.println("findMember.updatedDate = " + findMember.getUpdatedDate());
       }

       @Test
       void specBasic() throws Exception {
           // given
           Team teamA = new Team("teamA");
           em.persist(teamA);

           Member m1 = new Member("m1", 0, teamA);
           Member m2 = new Member("m2", 0, teamA);
           em.persist(m1);
           em.persist(m2);
           em.flush();
           em.clear();

           // when
           Specification<Member> spec =
                   MemberSpec.username("m1").and(MemberSpec.teamName("teamA"));
           List<Member> result = memberRepository.findAll(spec);


           // then
           Assertions.assertThat(result.size()).isEqualTo(1);


        }

        @Test
        void projections() throws Exception {
            // given
            Team teamA = new Team("teamA");
            em.persist(teamA);

            Member m1 = new Member("m1", 0, teamA);
            Member m2 = new Member("m2", 0, teamA);
            em.persist(m1);
            em.persist(m2);

            em.flush();
            em.clear();

            // when
            List<NestedClosedProjections> result = memberRepository.findProjectionByUsername("m1", NestedClosedProjections.class);

            for (NestedClosedProjections nestedClosedProjections : result) {
                String username = nestedClosedProjections.getUsername();
                System.out.println("username = " + username);
                String teamName = nestedClosedProjections.getTeam().getName();
                System.out.println("teamName = " + teamName);
            }


            // then
         }

         @Test
         void nativeQuery() throws Exception {
             // given
             Team teamA = new Team("teamA");
             em.persist(teamA);

             Member m1 = new Member("m1", 0, teamA);
             Member m2 = new Member("m2", 0, teamA);
             em.persist(m1);
             em.persist(m2);

             em.flush();
             em.clear();

             // when
//             Member result = memberRepository.findByNativeQuery("m1");
             Page<MemberProjection> result = memberRepository.findByNativeProjection(PageRequest.of(1, 10));
             List<MemberProjection> content = result.getContent();
             for (MemberProjection memberProjection : content) {
                 System.out.println("memberProjection.getUsername() = " + memberProjection.getUsername());
                 System.out.println("memberProjection.getTeamName( = " + memberProjection.getTeamName());
             }
//             System.out.println("result = " + result);

             // then
          }

}