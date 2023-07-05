package hello.core_re.beanfind;

import hello.core_re.discount.DiscountPolicy;
import hello.core_re.discount.RateDiscountPolicy;
import hello.core_re.member.MemberRepository;
import hello.core_re.member.MemoryMemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class ApplicationContextSameBeanFindTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SameBeanConfig.class);

    @Test
    @DisplayName("타입으로 조회시 같은 타입이 둘 이상 있으면, 중복 오류가 발생한다.")
    void findBeanByTypeDuplicate() throws Exception {
         // given
//        MemberRepository bean = ac.getBean(MemberRepository.class);
        assertThrows(NoUniqueBeanDefinitionException.class,
                () -> ac.getBean(MemberRepository.class));

        // when


        // then
     }

     @Test
     @DisplayName("타입으로 조회시 같은 타입이 둘 이상 있으면, 빈 이름을 지정하면 된다.")
     void findBeanByName() throws Exception {
         // given
         MemberRepository memberRepository = ac.getBean("memberRepository1", MemberRepository.class);
         assertThat(memberRepository).isInstanceOf(MemberRepository.class);

         // when


         // then
      }

      @Test
      @DisplayName("특정 타입을 모두 조회하기")
      void findAllBeanByType() throws Exception {
          // given
          Map<String, MemberRepository> beansOfType = ac.getBeansOfType(MemberRepository.class);

          for (String key : beansOfType.keySet()) {
              System.out.println("key = " + key);
              System.out.println("beansOfType.get(key) = " + beansOfType.get(key));
          }

          System.out.println("beansOfType = " + beansOfType);
          assertThat(beansOfType.size()).isEqualTo(2);
          // when


          // then


      }

    @Configuration
    static class SameBeanConfig {

        @Bean
        public MemberRepository memberRepository1() {
            return new MemoryMemberRepository();
        }

        @Bean
        public MemberRepository memberRepository2() {
            return new MemoryMemberRepository();
        }
    }
}
