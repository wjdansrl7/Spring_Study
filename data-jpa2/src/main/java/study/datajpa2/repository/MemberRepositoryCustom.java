package study.datajpa2.repository;

import study.datajpa2.entity.Member;

import java.util.List;

// 실무에서는 주로 QueryDSL이나 SpringJdbcTemplate을 함께 사용할 때 사용자 저으이 리포지토리 기능 자주 사용
public interface MemberRepositoryCustom {
    List<Member> findMemberCustom();
}
