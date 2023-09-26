package study.datajpa2.repository;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import study.datajpa2.entity.Member;
import study.datajpa2.entity.Team;

public class MemberSpec {

    // 명세를 정의하려면 Specification 인터페이스를 구현
    // 명세를 정의할 때는 toPredicate(..)메서드만 구현하면 되는데, JPA Criteria의 Root, CriteriaQuery,
    // CriteriaBuilder 클래스를 파라미터 제공
    public static Specification<Member> teamName(final String teamName) {

        return (Specification<Member>) (root, query, builder) -> {
            if (StringUtils.isEmpty(teamName)) {
                return null;
            }

            Join<Member, Team> t = root.join("team", JoinType.INNER);

            return builder.equal(t.get("name"), teamName);
        };
    }

    public static Specification<Member> username(final String username) {
        return (Specification<Member>) (root, query, builder) ->
            builder.equal(root.get("username"), username);

    }
}
