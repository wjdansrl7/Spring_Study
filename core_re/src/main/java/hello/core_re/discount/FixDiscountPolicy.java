package hello.core_re.discount;

import hello.core_re.discount.DiscountPolicy;
import hello.core_re.member.Grade;
import hello.core_re.member.Member;

public class FixDiscountPolicy implements DiscountPolicy {

    private int discountFixAmount = 1000;

    @Override
    public int discount(Member member, int price) {
        if (member.getGrade() == Grade.VIP) {
            return discountFixAmount;
        } else {
            return 0;
        }
    }
}
