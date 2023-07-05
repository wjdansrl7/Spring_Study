package hello.core_re.beanfind;

import com.sun.source.tree.AssertTree;
import hello.core_re.AppConfig;
import hello.core_re.member.MemberService;
import hello.core_re.member.MemberServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


public class ApplicationContextInfoTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("모든 빈 출력하기")
    void findAllBean() throws Exception {
        // given
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            Object bean = ac.getBean(beanDefinitionName);
            System.out.println("beanDefinitionName = " + beanDefinitionName);
            System.out.println("bean = " + bean);
        }

        // when


        // then
     }

     // ac.getBeanDefinitionNames(): 스프링에 등록된 모든 빈 이름을 조회한다.
     @Test
     @DisplayName("애플리케이션 빈 출력하기")
     void findApplicationBean() throws Exception {
         // given
         String[] beanDefinitionNames = ac.getBeanDefinitionNames();
         for (String beanDefinitionName : beanDefinitionNames) {
             BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);

             //Role ROLE_APPLICATION: 직접 등록한 애플리케이션 빈
             //Role ROLE_INFRASTRUCTURE: 스프링이 내부에서 사용하는 빈
             if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
                 Object bean = ac.getBean(beanDefinitionName);
                 System.out.println("beanDefinition = " + beanDefinition);
                 System.out.println("bean = " + bean);
             }

         }

         // when


         // then


      }

      @Test
      @DisplayName("빈 이름으로 조회")
      void findBeanByName() throws Exception {
          // given
          MemberService memberService = ac.getBean("memberService", MemberService.class);
          assertThat(memberService).isInstanceOf(MemberServiceImpl.class);


          // when


          // then


       }

       @Test
       @DisplayName("이름 없이 타입만으로 조회")
       void findBeanByType() throws Exception {
           // given
           MemberService memberService = ac.getBean(MemberService.class);
           assertThat(memberService).isInstanceOf(MemberServiceImpl.class);


           // when


           // then


        }

        @Test
        @DisplayName("구체 타입으로 조회")
        void findBeanByName2() throws Exception {
            // given
            MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
            assertThat(memberService).isInstanceOf(MemberServiceImpl.class);

            // when


            // then


         }

         @Test
         @DisplayName("빈 이름으로 조회X")
         void findBeanByNameX() throws Exception {
             // given
             assertThrows(NoSuchBeanDefinitionException.class, () ->
                     ac.getBean("xxxx", MemberService.class));
             // when


             // then


          }
}
