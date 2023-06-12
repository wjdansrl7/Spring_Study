package study.datajpa2.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa2.dto.MemberDto;
import study.datajpa2.entity.Member;
import study.datajpa2.entity.Team;

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
        Member member = new Member("username");
        Member savedMember = memberRepository.save(member);

        // when
        Member findMember = memberRepository.findById(savedMember.getId()).get();

        // then
        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member);

        System.out.println("memberRepository = " + memberRepository.getClass());
     }

    @Test
    void basicCRUD() throws Exception {
        // given
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberRepository.save(member1);
        memberRepository.save(member2);

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

    @Test
    void findByUsernameAndAgeGreaterThan() throws Exception {
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
     void testNamedQuery() throws Exception {
         // given
         Member m1 = new Member("AAA", 10);
         Member m2 = new Member("BBB", 20);

         memberRepository.save(m1);
         memberRepository.save(m2);

         Team team = new Team("teamA");
         teamRepository.save(team);

         m1.setTeam(team);

         // when
//         List<Member> result = memberRepository.findByUsername("AAA");
//         Member findMember = result.get(0);
         List<Member> members = memberRepository.findByUsername("AAA");
//         Member findMember = result.get(0);
         for (Member member : members) {
             System.out.println("member = " + member);
             System.out.println("member.getUsername() = " + member.getUsername());
             System.out.println("member.getTeam().getClass() = " + member.getTeam().getClass());
             System.out.println("member.getTeam().getName( = " + member.getTeam().getName());

         }
//         }


         // then
//         assertThat(findMember).isEqualTo(m1);

      }

      @Test
      void testQuery() throws Exception {
          // given
          Member m1 = new Member("AAA", 10);
          Member m2 = new Member("BBB", 20);

          memberRepository.save(m1);
          memberRepository.save(m2);

          // when
          List<Member> result = memberRepository.findUser("AAA", 10);


          // then
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
            Member m1 = new Member("AAA", 10);
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
         void findMemberTest() throws Exception {
             // given
             Member m1  = new Member("AAA", 10);
             Member m2 = new Member("BBB", 20);

             memberRepository.save(m1);
             memberRepository.save(m2);

             // when
             List<Member> findMember = memberRepository.findMembers("BBB");

             for (Member member : findMember) {
                 System.out.println("member = " + member);
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
              List<Member> result = memberRepository.findNames(Arrays.asList("AAA", "BBB"));
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
//               List<Member> result = memberRepository.findListByUsername("dfaskfjalf"); // []
//               Member member = memberRepository.findMemberByUsername("jfalskfjl"); // null
               Optional<Member> member = memberRepository.findOptionalMemberByUsername("dljaskfjl"); // Optional.empty
               System.out.println("member = " + member);

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
                // totalPage = totalCount / size ...
                // 마지막 페이지
                // 최초 페이지 ...

                // then
                List<Member> content = page.getContent();
                long totalElements = page.getTotalElements();

                for (Member member : content) {
                    System.out.println("member = " + member);
                }

                System.out.println("totalElements = " + totalElements);

                List<MemberDto> content1 = toMap.getContent();
                long totalElements1 = toMap.getTotalElements();

                for (MemberDto memberDto : content1) {
                    System.out.println("memberDto = " + memberDto);
                }

                System.out.println("totalElements1 = " + totalElements1);

                assertThat(content.size()).isEqualTo(3);
                assertThat(page.getTotalElements()).isEqualTo(5);
                assertThat(page.getNumber()).isEqualTo(0);
                assertThat(page.isFirst()).isTrue();
                assertThat(page.hasNext()).isTrue();
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
//                em.flush();
//                em.clear();

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
                 List<Member> members = memberRepository.findEntityGraphByUsername("member1");

                 for (Member member : members) {
                     System.out.println("member = " + member.getUsername());
                     System.out.println("member.getTeam().getClass() = " + member.getTeam().getClass());
                     System.out.println("member.getTeam().getName() = " + member.getTeam().getName());
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
                  // 조회만 목적으로 사용한다고 hibernatea에게 알려줘서 snapshot같은 것을 만들어두지 않는다.

                  // when
                  Member findMember = memberRepository.findReadOnlyByUsername("member1");
                  findMember.setUsername("member2");

                  em.flush(); // update 쿼리 실행 x

//                  System.out.println("findMember.getUsername( = " + findMember.getUsername());


                  // then

               }

               @Test
               void queryHint1() throws Exception {
                   // given
                   Member member1 = memberRepository.save(new Member("member1", 10));

                   em.flush();
                   em.clear();



                   // when
                   PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));

                   // when

                   Page<Member> page = memberRepository.findReadByUsername("member1", pageRequest);

                   List<Member> content = page.getContent();
                   long totalElements = page.getTotalElements();

                   for (Member member : content) {
                       System.out.println("member = " + member);
                   }

                   System.out.println("totalElements = " + totalElements);


                   assertThat(content.size()).isEqualTo(1);
                   assertThat(page.getTotalElements()).isEqualTo(1);
                   assertThat(page.getNumber()).isEqualTo(0);
                   assertThat(page.isFirst()).isTrue();
                   assertThat(page.hasNext()).isFalse();


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




}