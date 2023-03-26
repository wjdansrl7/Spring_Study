package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

//    나는 Order table에 있는 member에 의해서 매핑된 것.
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

}
