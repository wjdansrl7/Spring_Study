package hello.core_re.singleton;

import hello.core_re.AppConfig;
import hello.core_re.member.MemberRepository;
import hello.core_re.member.MemberService;
import hello.core_re.member.MemberServiceImpl;
import hello.core_re.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;

public class ConfigurationSingletonTest {

    @Test
    void configurationTest() throws Exception {
        // given
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);

        // 모두 같은 인스턴스를 참조하고 있다.
        System.out.println("memberService -> memberRepository = " + memberService.getMemberRepository());
        System.out.println("orderService -> memberRepository = " + orderService.getMemberRepository());
        System.out.println("memberRepository = " + memberRepository);

        // 모두 같은 인스턴스를 참조하고 있다.
        assertThat(memberService.getMemberRepository()).isSameAs(memberRepository);
        assertThat(orderService.getMemberRepository()).isSameAs(memberRepository);


        // when


        // then
     }

    /**
     * @Configuration과 바이트코드 조작의 마법
     * 스프링은 클래스의 바이트코드를 조작하는 라이브러리를 사용한다.
     * 모든 비밀은 @Configuration을 적용한 AppConfig에 있다.
     * 내가 만든 클래스가 아니라 스프링이 CGLIB라는 바이트코드 조작 라이브러리를 사용해서 AppConfig 클래스를 상속받은
     * 임의의 다른 클래스를 만들고, 그 다른 클래스를 스프링 빈으로 등록한 것 -> 임의의 다른 클래스가 바로 싱클돈이 보장되도록 해준다.
     */
     @Test
     void configurationDeep() throws Exception {
         // given
         ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

         //AppConfig도 스프링 빈으로 등록된다.
         AppConfig bean = ac.getBean(AppConfig.class);

         System.out.println("bean.getClass() = " + bean.getClass());

         // when


         // then


      }
}
