package hello.core1.member;

public class MemberServiceImpl implements MemberService {

//    interface에만 의존하도록 설계
    private final MemberRepository memberRepository;

//    생성자를 통해 주입받도록 하였다.
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
