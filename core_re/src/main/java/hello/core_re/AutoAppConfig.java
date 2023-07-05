package hello.core_re;

import hello.core_re.member.MemberRepository;
import hello.core_re.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import static org.springframework.context.annotation.ComponentScan.*;

/**
 * 컴포넌트 스캔을 사용하면 @Configuration 이 붙은 설정 정보도 자동으로 등록되기 때문에,
 * AppConfig, TestConfig 등 앞서 만들어두었던 설정 정보도 함께 등록되고, 실행되어 버린다.
 * 그래서 excludeFilters 를 이용해서 설정정보는 컴포넌트 스캔 대상에서 제외했다.
 * 보통 설정 정보를 컴포넌트 스캔 대상에서 제외하지는 않지만, 기존 예제 코드를 최대한 남기고 유지하기 위해서
 * 이 방법을 선택했다.
 * 권장하는 방법
 * 개인적으로 즐겨 사용하는 방법은 패키지 위치를 지정하지 않고,
 * 설정 정보 클래스의 위치를 프로젝트 최상단에 두는 것이다.
 * 최근 스프링 부트도 이 방법을 기본으로 제공한다.
 * 참고로 스프링 부트를 사용하면 스프링 부트의 대표 시작 정보인
 * @SpringBootApplication 를 이 프로젝트 시작 루트 위치에 두는 것이 관례이다.
 * (그리고 이 설정안에 바로 @ComponentScan 이 들어있다!)
 */
@Configuration
@ComponentScan(
//        basePackages = "hello.core_re",
        excludeFilters = @Filter(type = FilterType.ANNOTATION, classes =
                Configuration.class))
public class AutoAppConfig {

    // 수동 빈 vs 자동 빈 -> 빈 이름이 충돌할 경우 수동 빈이 우선권을 지닌다. (수동 빈이 자동 빈을 오버라이딩 해버린다.)
    @Bean(name = "memoryMemberRepository")
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }


}
