package study.datajpa2.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa2.entity.Member;
import study.datajpa2.entity.Team;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class QueryByExampleTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    @Test
    void basic() throws Exception {
        // given
        Team teamA = new Team("teamA");
        em.persist(teamA);

        em.persist(new Member("m1", 0, teamA));
        em.persist(new Member("m2", 0, teamA));
        em.flush();

        // when
        // Probe 생성
        // Probe: 필드에 데이터가 있는 실제 도메인 객체
        // ExampleMatcher: 특정 필드를 일치시키는 상세한 정보 제공, 재사용 가능
        // Example: Probe와 ExampleMatcher로 구성, 쿼리를 생성하는데 사용
        Member member = new Member("m1");
        Team team = new Team("teamA");
        member.setTeam(team);

        // ExampleMatcher 생성, age 프로퍼티는 무시
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnorePaths("age");

        Example<Member> example = Example.of(member, matcher);

        List<Member> result = memberRepository.findAll(example);

        // then
        assertThat(result.size()).isEqualTo(1);


     }

}