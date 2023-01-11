package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// interface가 interface를 받을 때에는 extends
// interface는 다중 상속 가능(JpaRepository, MemberRepository)
public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {

    // JPQL select m from Member m where m.name= ? 이렇게 규칙이 있음.
    @Override
    Optional<Member> findByName(String name);
}
