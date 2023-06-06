package jpabook.jpashop1.service;

import jpabook.jpashop1.domain.Member;
import jpabook.jpashop1.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

//    @Autowired
//    MemberRepository memberRepository;

    private final MemberRepository memberRepository;

//    @Autowired
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    /**
     * 회원가입
     */
    @Transactional // 변경
    public Long join(Member member) {

        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        // Exception
        // 해당 비즈니스 로직은 multi-thread환경과 같은 경우 동시에 같은 이름을 가진 사람이 가입할 경우
        // 문제가 생길 수도 있으므로 이럴 경우에는 데이터베이스에 unique 제약조건을 권장.
        List<Member> findMember = memberRepository.findByName(member.getName());

        if (!findMember.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findById(memberId).get();
    }

    @Transactional // 변경
    public void update(Long id, String name) {
        // 변경 감지를 사용하기 위해서 영속성 컨텍스트를 이용한다.
        Member member = memberRepository.findById(id).get();
        member.setName(name);
    }
}
