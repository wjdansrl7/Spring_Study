package hello.core2;

import hello.core2.discount.DiscountPolicy;
import hello.core2.member.MemberRepository;
import hello.core2.member.MemoryMemberRepository;
import hello.core2.order.OrderService;
import hello.core2.order.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
//        만약 지정하지 않으면 '@ComponentScan'이 붙은 설정 정보 클래스의 패키지가 시작 위치가 된다.
//        basePackages = "hello.core2.member",
//        basePackageClasses = AutoAppConfig.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {

//    수동 빈 등록 vs 자동 빈 등록: 수동 빈이 우선권을 가지므로 overriding된다.
//    @Bean(name = "memoryMemberRepository")
//    MemberRepository memberRepository() {
//        return new MemoryMemberRepository();
//    }
}
