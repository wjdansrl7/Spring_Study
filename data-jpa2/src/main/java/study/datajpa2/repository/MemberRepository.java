package study.datajpa2.repository;

import jakarta.persistence.QueryHint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import study.datajpa2.dto.UsernameOnly;
import study.datajpa2.entity.Member;
import study.datajpa2.dto.MemberDto;
import study.datajpa2.entity.Team;

import java.util.List;

// 구현체는 spring data jpa가 만들어준다.
// JPARepository<엔티티, 엔티티에 식별되는 Key Type>
// JPARepository 인터페이스: 공통 CRUD 제공
// 제네릭은 <엔티티 타입, 식별자 타입>
public interface MemberRepository extends JpaRepository<Member, Long>, JpaSpecificationExecutor<Member> {
    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    @Query("select m.username from Member m")
    List<String> findUsernameList();

//    @Query("select new study.datajpa2.dto.MemberDto(m.id, m.username, t.name) " +
//            "from Member m join m.team t")
//    List<MemberDto> findMemberDto();

    // 파라미터 바인딩 지원: 이름 지원
    @Query("select m from Member m where m.username =:name")
    List<Member> findMembers(@Param("name") String username);

    // 컬렉션 파라미터 바인딩: Collection 타입으로 in절 지원
    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") List<String> names);

//    반환 타입
//    List<Member> findByUsername(String name); // 컬렉션
//    Member findByUsername(String name); // 단건
//    Optional<Member> findByUsername(String name); // 단건 Optional

    Page<Member> findByAge(int age, Pageable pageable);

    @Query(value = "select m from Member m",
            countQuery = "select count(m.username) from Member m")
    Page<Member> findMemberAllCountBy(Pageable pageable);

    List<Member> findTop3By();

    @Modifying
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);

    @Query("select m from Member m left join fetch m.team")
    List<Member> findMemberFetchJoin();

    // EntityGraph : 페치 조인(FETCH JOIN)의 간편 버전, LEFT OUTER JOIN 사용
    // 연관된 엔티티들을 SQL 한번에 조회하는 방법
    // 공통 메서드 오버라이드
    @Override
    @EntityGraph(attributePaths = {"team"})
    List<Member> findAll();

    // JPQL + 엔티티 그래프
//    @EntityGraph(attributePaths = {"team"})
//    @Query("select m from Member m")
//    List<Member> findMemberEntityGraph();

    // 메서드 이름으로 쿼리에서 특히 편리하다.
//    @EntityGraph(attributePaths = {"team"})
//    List<Member> findByUsername(String username);

    @EntityGraph("Member.all")
    @Query("select m from Member m")
    List<Member> findMemberEntityGraph();

    // JPA Hint: JPA 쿼리 힌트(SQL 힌트가 아니라 JPA 구현체에게 제공하는 힌트)
    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
    Member findReadOnlyByUsername(String username);

    // forCounting: 반환 타입으로 Page 인터페이스를 적용하면 추가로 호출하는 페이징을 위한 count 쿼리도 쿼리 힌트 적용(기본값: true)
    @QueryHints(value = {@QueryHint(name = "org.hibernate.readOnly",
            value = "true")},
            forCounting = true)
    Page<Member> findByUsername(String name, Pageable pageable);


//    List<UsernameOnly> findProjectionByUsername(String username);

    <T> List<T> findProjectionsByUsername(@Param("username") String username);

}
