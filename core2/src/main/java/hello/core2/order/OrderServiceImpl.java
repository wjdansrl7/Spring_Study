package hello.core2.order;

import hello.core2.annotation.MainDiscountPolicy;
import hello.core2.discount.DiscountPolicy;
import hello.core2.discount.FixDiscountPolicy;
import hello.core2.discount.RateDiscountPolicy;
import hello.core2.member.Member;
import hello.core2.member.MemberRepository;
import hello.core2.member.MemoryMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
//final이 붙은 생성자를 만들어준다.(lombok에서)
//@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    // 생성자가 1개일 경우에는 생략 가능
    // DI내에서 우선순위를 가진다.
    // 생성자를 사용하면 좋은 점: 불변, 누락, final 키워드
    // 임의로 조작 불가능, 생성자내에 코드가 누락될 경우 바로 check 가능
    // 수정자 주입을 포함한 나머지 주입 방식은 모두 생성자 이후에 호출되므로, 필드에 final 키워드를 사용할 수 없다.
    // 오직 생성자 주입 방식만 final 키워드를 사용할 수 있다.
    @Autowired
     public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy ;
    }

    // setter로 주입할 경우는 final 제거
    // private MemberRepository memberRepository;
    // private DiscountPolicy discountPolicy;

    // 필드 주입
//    @Autowired private MemberRepository memberRepository;
//    @Autowired private DiscountPolicy discountPolicy;


//    @Autowired
//    public void setMemberRepository(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }
//    @Autowired
//    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
//        this.discountPolicy = discountPolicy;
//    }

//    private final MemberRepository memberRepository;
//    private final DiscountPolicy discountPolicy;



    // autowired의 기본 동작은 주입할 대상이 없으면 오류가 나지만, required=false를 붙이면
    // 주입할 대상이 없더라 도 동작하게 할 수 있다.
//    @Autowired(required = false)
//    // 선택, 변경: 가능성이 있는 의존관계에 사용
//    // 선택전 의존 관계 설정
//    public void setMemberRepository(MemberRepository memberRepository) {
//        System.out.println("memberRepository = " + memberRepository);
//        this.memberRepository = memberRepository;
//    }
//
//    @Autowired
//    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
//        System.out.println("discountPolicy = " + discountPolicy);
//        this.discountPolicy = discountPolicy;
//    }

    // 일반 메서드 주입
//    스프링 빈으로 동록되어있어야 동작.
//    @Autowired
//    public void init(MemberRepository memberRepository, DiscountPolicy
//            discountPolicy) {
//        this.memberRepository = memberRepository;
//        this.discountPolicy = discountPolicy;
//        }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);

    }

    // 테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
