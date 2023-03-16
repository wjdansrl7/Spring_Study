package hello1.hello1spring;

import hello1.hello1spring.repository.JdbcMemberRepository;
import hello1.hello1spring.repository.JdbcTemplateMemberRepository;
import hello1.hello1spring.repository.MemberRepository;
import hello1.hello1spring.repository.MemoryMeberRepository;
import hello1.hello1spring.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    private DataSource dataSource;

    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository()
    {
//        return new MemoryMeberRepository();
//        return new JdbcMemberRepository(dataSource); // 다형성
        return new JdbcTemplateMemberRepository(dataSource);
   }


}
