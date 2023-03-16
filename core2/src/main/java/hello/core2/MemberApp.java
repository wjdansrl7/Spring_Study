package hello.core2;

import hello.core2.member.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {
    public static void main(String[] args) {
//        AppConfig appConfig = new AppConfig();
//        MemberService memberService = appConfig.memberService();
//        MemberService memberService = new MemberServiceImpl();

        // 스프링 컨테이너 : ApplicationContext
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        // 나는 "membmerService"라는 이름의 메서드를 찾을 거고, Type은 MemberService야.
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);

        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        Member findMember = memberService.findMember(1L);


        System.out.println("new member = " + member.getName());
        System.out.println("find Member = " + findMember.getName());
    }
}
