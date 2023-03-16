package hello1.hello1spring.service;


// service에서는 비즈니스 로직같은 단어로 함수 이름을 지정해줘야 한다.

import hello1.hello1spring.domain.Member;
import hello1.hello1spring.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberService {
    private final MemberRepository memberRepository;

//    생성자 DI
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;

        // 필드 DI
        // @Autowired private MemberService memberService;

        // Setter DI를 위해 final 제거
        // private MemberRepository memberRepository;
        // @Autowired
//        public void setMemberRepository(MemberRepository memberRepository) {
//          this.memberRepository = memberRepoistory;
//        }
    }

    /**
     * 회원 가입
     */
    public Long join(Member member) {
        // 같은 이름이 있는 중복 회원 x
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }

}
