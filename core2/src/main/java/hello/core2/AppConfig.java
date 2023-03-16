package hello.core2;

import hello.core2.discount.DiscountPolicy;
import hello.core2.discount.FixDiscountPolicy;
import hello.core2.discount.RateDiscountPolicy;
import hello.core2.member.MemberRepository;
import hello.core2.member.MemberService;
import hello.core2.member.MemberServiceImpl;
import hello.core2.member.MemoryMemberRepository;
import hello.core2.order.OrderService;
import hello.core2.order.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// @cConfiguration을 주석 처리하면 CGLIB 기술 없이 순수한 AppConfig로 빈이 등록되며, 따라서 Singleton을 보장
// 못하게 된다.
@Configuration // 설정 정보
public class AppConfig {

    // memberService -> memberRepository
    // orderService -> memberRepository

    // @Configuration이 주석처리시, Singleton을 보장받지 못하므로
    // call AppConfig.memberService
    // call AppConfig.memberRepository
    // call AppConfig.orderService
    // call AppConfig.memberRepository
    // call AppConfig.memberRepository

    // -> 실제
    // call AppConfig.memberService
    // call AppConfig.memberRepository
    // call AppConfig.orderService

    // 자동 주입을 통해서 Singleton을 지키게 한 경우
//    @Autowired MemberRepository memberRepository;

    @Bean // 각 메서드에 Bean -> 스프링 컨테이너에 등록
    public MemberService memberService() {
        // 생성자 주입
        System.out.println("call AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public MemoryMemberRepository memberRepository() {
        System.out.println("call AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService() {
        System.out.println("call AppConfig.orderService");
        return new OrderServiceImpl(memberRepository(), discountPolicy());
//        return null;
    }

    @Bean
    public DiscountPolicy discountPolicy() {
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }

}
