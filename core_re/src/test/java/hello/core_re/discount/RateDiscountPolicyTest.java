package hello.core_re.discount;

import hello.core_re.member.Grade;
import hello.core_re.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.swing.plaf.basic.BasicCheckBoxMenuItemUI;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RateDiscountPolicyTest {

    RateDiscountPolicy discountPolicy = new RateDiscountPolicy();

    @Test
    @DisplayName("VIP는 10% 할인이 적용되어야 한다.")
    void vip_o() throws Exception {
        // given
        Member member = new Member(1L, "memberVIP", Grade.VIP);

        // when
        int discount = discountPolicy.discount(member, 10000);



        // then
        assertThat(discount).isEqualTo(1000);


     }

     @Test
     void vip_x() throws Exception {
         // given
         Member member = new Member(2L, "memberBasic", Grade.BASIC);

         // when
         int discount = discountPolicy.discount(member, 10000);


         // then
         assertThat(discount).isEqualTo(0);


      }
}