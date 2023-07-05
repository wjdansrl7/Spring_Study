package hello.core_re.discount;

import hello.core_re.member.Member;

public interface DiscountPolicy {

    int discount(Member member, int price);
}
