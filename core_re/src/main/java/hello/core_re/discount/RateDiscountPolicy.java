package hello.core_re.discount;

import hello.core_re.member.Grade;
import hello.core_re.member.Member;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RateDiscountPolicy implements DiscountPolicy{

    private int discountPercent = 10;

    @Override
    public int discount(Member member, int price) {
        if (member.getGrade() == Grade.VIP) {
            return price * discountPercent / 100;
        } else {
            return 0;
        }
    }
}
