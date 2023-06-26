package study.querydsl2.entity;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl2.dto.MemberDto;
import study.querydsl2.dto.QMemberDto;
import study.querydsl2.dto.UserDto;

import java.util.List;

import static com.querydsl.jpa.JPAExpressions.*;
import static org.assertj.core.api.Assertions.*;
import static study.querydsl2.entity.QMember.member;
import static study.querydsl2.entity.QTeam.team;

@SpringBootTest
@Transactional
public class QuerydslBasicTest {

    @Autowired
    EntityManager em;

    JPAQueryFactory queryFactory;

    @BeforeEach
    public void beforeEach() {
        queryFactory = new JPAQueryFactory(em);

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
    }

    @Test
    void startJPQL() throws Exception {
        // given
        String qlString = "select m from Member m " +
                "where m.username = :username";
        Member findMember = em.createQuery(qlString, Member.class)
                .setParameter("username", "member1")
                .getSingleResult();


        // when


        // then
        assertThat(findMember.getUsername()).isEqualTo("member1");
    }

    @Test
    void startQuerydsl1() throws Exception {
        // given
        //         QMember m = new QMember("m");
        //         QMember m = member;
        // 같은 테이블을 조인해야 하는 경우에만 alias를 설정한다.
        //         QMember m1 = new QMember("m1");

        // when
        Member findMember = queryFactory
                .select(member)
                .from(member)
                .where(member.username.eq("member1"))
                .fetchOne();


        // then
        assertThat(findMember.getUsername()).isEqualTo("member1");
    }

    @Test
    void search() throws Exception {
        // given
        Member findMember = queryFactory
                .selectFrom(QMember.member)
                .where(QMember.member.username.eq("member1")
                        .and(QMember.member.age.eq(10)))
                .fetchOne();


        // when


        // then
        assertThat(findMember.getUsername()).isEqualTo("member1");
        assertThat(findMember.getAge()).isEqualTo(10);
    }

    @Test
    void searchAndParam() throws Exception {
        // given
        Member findMember = queryFactory
                .selectFrom(QMember.member)
                .where(
                        QMember.member.username.eq("member1"),
                        QMember.member.age.eq(10)
                )
                .fetchOne();

        // when

        // then
        assertThat(findMember.getUsername()).isEqualTo("member1");
    }

    @Test
    void resultFetch() throws Exception {
        // given
        //            List<Member> fetch = queryFactory
        //                    .selectFrom(member)
        //                    .fetch();
        //            Member fetchOne = queryFactory
        //                    .selectFrom(QMember.member)
        //                    .fetchOne();
        //
        //            Member fetchFirst = queryFactory
        //                    .selectFrom(QMember.member)
        ////                    .limit(1).fetchOne();
        //                    .fetchFirst();

        //            QueryResults<Member> results = queryFactory
        //                    .selectFrom(member)
        //                    .fetchResults();

        long total = queryFactory
                .selectFrom(member)
                .fetchCount();


        // when


        // then
    }

    @Test
    void sort() throws Exception {
        // given
        em.persist(new Member(null, 100));
        em.persist(new Member("member5", 100));
        em.persist(new Member("member6", 100));

        List<Member> result = queryFactory
                .selectFrom(member)
                .where(member.age.eq(100))
                .orderBy(member.age.desc(), member.username.asc().nullsLast())
                .fetch();

        // when
        Member member5 = result.get(0);
        Member member6 = result.get(1);
        Member memberNull = result.get(2);


        // then
        assertThat(member5.getUsername()).isEqualTo("member5");
        assertThat(member6.getUsername()).isEqualTo("member6");
        assertThat(memberNull.getUsername()).isNull();
    }

    @Test
    void paging1() throws Exception {
        // given
        List<Member> result = queryFactory
                .selectFrom(member)
                .orderBy(member.username.desc())
                // offset은 0번부터
                .offset(1)
                .limit(2)
                .fetch();

        //              for (Member member1 : result) {
        //                  System.out.println("member1 = " + member1);
        //              }


        // when


        // then
        assertThat(result.size()).isEqualTo(2);

    }

    @Test
    void paging2() throws Exception {
        // given
        QueryResults<Member> queryResults = queryFactory
                .selectFrom(member)
                .orderBy(member.username.desc())
                .offset(1)
                .limit(2)
                .fetchResults();

        // when


        // then
        assertThat(queryResults.getTotal()).isEqualTo(4);
        assertThat(queryResults.getLimit()).isEqualTo(2);
        assertThat(queryResults.getOffset()).isEqualTo(1);
        assertThat(queryResults.getResults().size()).isEqualTo(2);
    }

    @Test
    void aggregation() throws Exception {
        // given
        List<Tuple> result = queryFactory
                .select(
                        member.count(),
                        member.age.sum(),
                        member.age.avg(),
                        member.age.max(),
                        member.age.min()
                )
                .from(member)
                .fetch();

        // when
        Tuple tuple = result.get(0);

        //                for (Tuple tuple1 : result) {
        //                    System.out.println("tuple1 = " + tuple1);
        //                }
        //                tuple1 = [4, 100, 25.0, 40, 10]


        // then
        assertThat(tuple.get(member.count())).isEqualTo(4);
        assertThat(tuple.get(member.age.sum())).isEqualTo(100);
        assertThat(tuple.get(member.age.avg())).isEqualTo(25);
        assertThat(tuple.get(member.age.max())).isEqualTo(40);
        assertThat(tuple.get(member.age.min())).isEqualTo(10);
    }

    /**
     * 팀의 이름과 각 팀의 평균 연령을 구해라.
     */

    @Test
    void group() throws Exception {
        // given
        List<Tuple> result = queryFactory
                .select(team.name, member.age.avg())
                .from(member)
                .join(member.team, team)
                .groupBy(team.name)
                .fetch();

        // when

        Tuple teamA = result.get(0);
        Tuple teamB = result.get(1);

        // then
        assertThat(teamA.get(team.name)).isEqualTo("teamA");
        assertThat(teamA.get(member.age.avg())).isEqualTo(15); // (10 + 20) / 2

        assertThat(teamB.get(team.name)).isEqualTo("teamB");
        assertThat(teamB.get(member.age.avg())).isEqualTo(35); // (30 + 40) / 2
    }

    /**
     * 팀 A에 소속된 모든 회원
     */
    @Test
    void join() throws Exception {
        // given
        List<Member> result = queryFactory
                .selectFrom(member)
                // 조인 대상, 별칭으로 사용할 Q타입
                .join(member.team, team)
                .where(team.name.eq("teamA"))
                .fetch();

        // when


        // then
        assertThat(result)
                .extracting("username")
                .containsExactly("member1", "member2");
    }

    /**
     * 연관관계가 없으면 조인을 못하나?
     * 세타 조인
     * 회원의 이름이 팀 이름과 같은 회원 조회
     * 세타 조인: member의 모든 table과 team의 모든 테이블 정보를 다 조인한다.(물론 DB마다 최적화 해줌)
     * 외부 조인은 불가능 -> 다음에 설명할 조인 on을 사용하면 외부 조인 가능
     */
    @Test
    void theta_join() throws Exception {
        // given
        em.persist(new Member("teamA"));
        em.persist(new Member("teamB"));
        em.persist(new Member("teamC"));

        // when
        List<Member> result = queryFactory
                .select(member)
                .from(member, team)
                .where(member.username.eq(team.name))
                .fetch();


        // then
        assertThat(result)
                .extracting("username")
                .containsExactly("teamA", "teamB");
    }

    /**
     * 예) 회원과 팀을 조인하면서 팀 이름이 teamA인 팀만 조인, 회원은 모두 조인
     * JPQL: select m, t from Member m left join m, Team t on t.name = "teamA"
     * on절을 활용한 조인 대상 필터링을 사용할 때, 내부조인이면 익숙한 where 절로 해결하고, 정말 외부조인이
     * 필요한 경우에만 이 기능을 사용하자.
     */
    @Test
    void join_on_filtering() throws Exception {
        // given
        List<Tuple> result = queryFactory
                .select(member, team)
                .from(member)
                .leftJoin(member.team, team).on(team.name.eq("teamA"))
                //                .join(member.team, team)
                //                .on(team.name.eq("teamA"))
                //                .where(team.name.eq("teamA"))
                .fetch();

        for (Tuple tuple : result) {
            System.out.println("tuple = " + tuple);
        }

        // when


        // then
    }

    /**
     * 연관관계가 없는 엔티티 외부 조인
     * 회원의 이름이 팀 이름과 같은 대상 외부 조인
     * 연관관계 상관없이 막 조인할 것이기 때문
     * on절: join하는 대상을 필터
     */
    @Test
    void join_on_no_relation() throws Exception {
        // given
        em.persist(new Member("teamA"));
        em.persist(new Member("teamB"));
        em.persist(new Member("teamC"));

        List<Tuple> result = queryFactory
                .select(member, team)
                .from(member)
                .leftJoin(team).on(member.username.eq(team.name))
                .fetch();

        for (Tuple tuple : result) {
            System.out.println("tuple = " + tuple);
        }


        // when


        // then
    }

    // entityManagerfactory를 만들어줌
    @PersistenceUnit
    EntityManagerFactory emf;

    @Test
    void fetchJoinNo() throws Exception {
        // given
        em.flush();
        em.clear();

        // when
        Member findMember = queryFactory
                .selectFrom(QMember.member)
                .where(QMember.member.username.eq("member1"))
                .fetchOne();

        boolean loaded = emf.getPersistenceUnitUtil().isLoaded(findMember.getTeam());

        // then
        assertThat(loaded).as("페치 조인 미적용").isFalse();
    }

    @Test
    void fetchJoinUse() throws Exception {
        // given
        em.flush();
        em.clear();


        // when

        Member findMember = queryFactory
                .selectFrom(QMember.member)
                .join(QMember.member.team, team).fetchJoin()
                .where(QMember.member.username.eq("member1"))
                .fetchOne();

        boolean loaded = emf.getPersistenceUnitUtil().isLoaded(findMember.getTeam());

        // then
        assertThat(loaded).as("페치 조인 적용").isTrue();
    }

    /**
     * 서브쿼리: 서브안에 쿼리
     * 나이가 가장 많은 회원 조회
     */
    @Test
    void subQuery() throws Exception {
        QMember memberSub = new QMember("memberSub");
        // given
        List<Member> result = queryFactory
                .selectFrom(member)
                .where(member.age.eq(
                        select(memberSub.age.max())
                                .from(memberSub)
                ))
                .fetch();

        // when


        // then
        assertThat(result)
                .extracting("age")
                .containsExactly(40);
    }

    /**
     * 나이가 평균 이상인 회원 조회
     */

    @Test
    void subQueryGoe() throws Exception {
        QMember memberSub = new QMember("memberSub");
        // given
        List<Member> result = queryFactory
                .selectFrom(member)
                .where(member.age.goe(
                        select(memberSub.age.avg())
                                .from(memberSub)
                ))
                .fetch();

        //        for (Member member1 : result) {
        //            System.out.println("member1 = " + member1);
        //        }


        // when


        // then
        assertThat(result)
                .extracting("age")
                .containsExactly(30, 40);
    }

    @Test
    void subQueryIn() throws Exception {
        // given
        QMember memberSub = new QMember("memberSub");

        // when
        List<Member> result = queryFactory
                .selectFrom(member)
                .where(member.age.in(
                        select(memberSub.age)
                                .from(memberSub)
                                .where(memberSub.age.gt(10))
                ))
                .fetch();

        //         for (Member member1 : result) {
        //             System.out.println("member1 = " + member1);
        //         }

        // then
        assertThat(result)
                .extracting("age")
                .containsExactly(20, 30, 40);
    }

    @Test
    void selectSubQuery() throws Exception {
        QMember memberSub = new QMember("memberSub");
        // given
        List<Tuple> result = queryFactory
                .select(member.username, select(memberSub.age.avg())
                        .from(memberSub))
                .from(member)
                .fetch();

        for (Tuple tuple : result) {
            System.out.println("tuple = " + tuple);
        }

        // when


        // then
    }

    @Test
    void basicCase() throws Exception {
        // given
        List<String> result = queryFactory
                .select(member.age
                        .when(10).then("열살")
                        .when(20).then("스무살")
                        .otherwise("기타"))
                .from(member)
                .fetch();

        for (String s : result) {
            System.out.println("s = " + s);
        }

        // when


        // then
    }

    // 권장사항: DB의 raw data를 filtering, group 등 최소한만, 직접 튜플들을 가져와서 애플리케이션이나
    // 프레젠테이션 로직에서 사용하는 것을 권장
    @Test
    void complexCase() throws Exception {
        // given
        List<String> result = queryFactory
                .select(new CaseBuilder()
                        .when(member.age.between(0, 20)).then("0~20살")
                        .when(member.age.between(21, 30)).then("21살~30살")
                        .otherwise("기타"))
                .from(member)
                .fetch();

        for (String s : result) {
            System.out.println("s = " + s);
        }


        // when


        // then
    }

    @Test
    void orderByAndCaseExample() throws Exception {
        NumberExpression<Integer> rankPath = new CaseBuilder()
                .when(member.age.between(0, 20)).then(2)
                .when(member.age.between(21, 30)).then(1)
                .otherwise(3);

        // given
        List<Tuple> result = queryFactory
                .select(member.username, member.age, rankPath)
                .from(member)
                .orderBy(rankPath.desc())
                .fetch();

        for (Tuple tuple : result) {
            String username = tuple.get(member.username);
            Integer age = tuple.get(member.age);
            Integer rank = tuple.get(rankPath);
            System.out.println("username = " + username + " age = "
                    + age + " rank = " + rank);
        }


        // when


        // then


    }

    @Test
    void constant() throws Exception {
        // given
        List<Tuple> result = queryFactory
                .select(member.username, Expressions.constant("A"))
                .from(member)
                .fetch();

        for (Tuple tuple : result) {
            System.out.println("tuple = " + tuple);
        }


        // when


        // then
    }

    // 문자가 아닌 타입들은 stringValue()로 변환해서 사용할 수 있다, enumType타입에서 주로 사용
    @Test
    void concat() throws Exception {
        // given
        List<String> result = queryFactory
                .select(member.username.concat("_").concat(member.age.stringValue()))
                .from(member)
                .where(member.username.eq("member1"))
                .fetch();

        for (String s : result) {
            System.out.println("s = " + s);
        }
        // when


        // then


    }

    // projection: select 절 구문에 쭉 나열하는 것을 projection이라고 한다.
    @Test
    void simpleProjection() throws Exception {
        // given
        List<String> result = queryFactory
                .select(member.username)
                .from(member)
                .fetch();

        for (String s : result) {
            System.out.println("s = " + s);
        }

        // when


        // then
    }

    // Tuple의 pakage는 querydsl -> repository 계층안에서 쓰는 것은 괜찮지만, 벗어나서 service 계층이나
    // controller까지 사용하는 것은 바람직하지 않다고 생각(좋은 설계가 아니다.)
    // 하부 구현 기술 즉, 우리가 jpa나 querydsl을 쓴다는 것을 앞단이 알면 좋지 않잖냐, 핵심 비즈니스 로직이나, 서비스에서
    // jdbc 같은 것을 쓸 때에는 resultset이나 그런 것을 repository나 DAO 계층 안에서만 쓰게 하고 나머지 계층에서는
    // 그런 것들에 대한 의존성이 없도록 설계

    // tuple은 querydsl안에서 종속적인 관계이므로 바깥에 나갈때는 DTO로 나가는 것을 권장

    @Test
    void tupleProjection() throws Exception {
        // given
        List<Tuple> result = queryFactory
                .select(member.username, member.age)
                .from(member)
                .fetch();

        for (Tuple tuple : result) {
            String username = tuple.get(member.username);
            Integer age = tuple.get(member.age);
            System.out.println("username = " + username);
            System.out.println("age = " + age);
        }

        // when


        // then
    }

    @Test
    void findDtoByJPQL() throws Exception {
        // given
        // new Operation을 활용하는 방법
        List<MemberDto> result = em.createQuery("select new study.querydsl2.dto.MemberDto(m.username, m.age) from Member m", MemberDto.class)
                .getResultList();

        for (MemberDto memberDto : result) {
            System.out.println("memberDto = " + memberDto);
        }

        // when


        // then
    }

    @Test
    void findDtoBySetter() throws Exception {
        // given
        // setter를 통해서 값을 집어넣는다.
        List<MemberDto> result = queryFactory
                .select(Projections.bean(MemberDto.class,
                        member.username,
                        member.age))
                .from(member)
                .fetch();

        for (MemberDto memberDto : result) {
            System.out.println("memberDto = " + memberDto);
        }

        // when


        // then
    }

    @Test
    void findDtoByField() throws Exception {
        // given
        List<MemberDto> result = queryFactory
                .select(Projections.fields(MemberDto.class,
                        member.username,
                        member.age))
                .from(member)
                .fetch();

        for (MemberDto memberDto : result) {
            System.out.println("memberDto = " + memberDto);
        }

        // when


        // then
    }

    @Test
    void findDtoByConstructor() throws Exception {
        // given
        // 타입은 맞춰야 한다.
        List<MemberDto> result = queryFactory
                .select(Projections.constructor(MemberDto.class,
                        member.username,
                        member.age))
                .from(member)
                .fetch();

        for (MemberDto memberDto : result) {
            System.out.println("memberDto = " + memberDto);
        }

        // when


        // then
    }

    @Test
    void findUserDtoByField() throws Exception {
        // given
        List<UserDto> result = queryFactory
                .select(Projections.fields(UserDto.class,
                        // DTO에 필드 이름과 일치하지 않는다면 as를 통해 이름을 맞춰준다.
                        member.username.as("name"),
                        member.age))
                .from(member)
                .fetch();

        for (UserDto userDto : result) {
            System.out.println("userDto = " + userDto);
        }


        // when


        // then
    }

    // 프로퍼티나, 필드 접근 생성 방식에서 이름이 다를 때 해결방안
    // ExpressionUtils.as(source, alias): 필드나 서브 쿼리에 별칭 사용
    // username.as("memberName"): 필드에 별칭 사용
    @Test
    void findUserDto() throws Exception {
        QMember memberSub = new QMember("memberSub");
        // given
        List<UserDto> result = queryFactory
                .select(Projections.fields(UserDto.class,
                        member.username.as("name"),
                        //                                     ExpressionUtils.as(member.username, "name"),
                        ExpressionUtils.as(select(memberSub.age.max())
                                .from(memberSub), "age")
                ))
                .from(member)
                .fetch();

        for (UserDto userDto : result) {
            System.out.println("userDto = " + userDto);
        }

        // when
        // then
    }

    @Test
    void findUserDtoByConstructor() throws Exception {
        // given
        List<UserDto> result = queryFactory
                .select(Projections.constructor(UserDto.class,
                        member.username,
                        member.age))
                .from(member)
                .fetch();

        for (UserDto userDto : result) {
            System.out.println("userDto = " + userDto);
        }

        // when


        // then
    }

    // constructor와 queryProjection의 차이점: constructor은 queryFactory의 select 절에
    // 다른 값을 넣더라도 compile 시점에서 오류를 잡아주는게 아니라, runtime 시점에서 오류가 잡힘
    // but, queryProjection은 Q파일에서 생성되서 method로 값을 잡아주므로 compile 시점에서 오류를
    // 잡아낼 수 있다.

    @Test
    void findDtoByQueryProjection() throws Exception {
        // given
        List<MemberDto> result = queryFactory
                .select(new QMemberDto(member.username, member.age))
                .from(member)
                .fetch();

        for (MemberDto memberDto : result) {
            System.out.println("memberDto = " + memberDto);
        }

        // when


        // then
    }

    @Test
    void dynamic_BooleanBuilder() throws Exception {
        // given
        String usernameParam = "member1";
        Integer ageParam = 10;

        List<Member> result = searchMember1(usernameParam, ageParam);


        // when


        // then
        assertThat(result.size()).isEqualTo(1);


    }

    private List<Member> searchMember1(String usernameCond, Integer ageCond) {

        // 초기값 설정 가능(앞에 null이 오면 안된다는 조건을 붙이고)
        //        BooleanBuilder builder = new BooleanBuilder(member.username.eq(usernameCond));

        BooleanBuilder builder = new BooleanBuilder();

        if (usernameCond != null) {
            builder.and(member.username.eq(usernameCond));
        }

        if (ageCond != null) {
            builder.and(member.age.eq(ageCond));
        }

        return queryFactory
                .selectFrom(member)
                .where(builder)
                .fetch();
    }


    @Test
    void dynamicQuery_WhereParam() throws Exception {
        // given
        String usernameParam = "member1";
        Integer ageParam = 10;

        List<Member> result = searchMember2(usernameParam, ageParam);

        // when


        // then
        assertThat(result.size()).isEqualTo(1);


    }

    private List<Member> searchMember2(String usernameCond, Integer ageCond) {
        return queryFactory
                .selectFrom(member)
//                .where(usernameEq(usernameCond), ageEq(ageCond))
                .where(allEq(usernameCond, ageCond))
                .fetch();
    }

    private BooleanExpression usernameEq(String usernameCond) {
        return usernameCond != null ? member.username.eq(usernameCond) : null;

    }

    private BooleanExpression ageEq(Integer ageCond) {
        return ageCond != null ? member.age.eq(ageCond) : null;
    }

    private BooleanExpression allEq(String usernameCond, Integer ageCond) {
        return usernameEq(usernameCond).and(ageEq(ageCond));
    }
}
