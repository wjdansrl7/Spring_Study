package study.datajpa2.entity;

import jakarta.persistence.*;
import lombok.*;


/*
@NoArgsConstructor(access = AccessLevel.PROTECTED) : 기본 생성자 막고 싶은데, JPA 스펙상 PROTECTED로 열어두라 함,
@ToString: 가급적 내부 필드만(연관관계 없는 필드만)
 */
@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "username", "age"})
//@NamedQuery(
//        name = "Member.findByUsername",
//        query = "select m from Member m where m.username = :username")
@NamedEntityGraph(name = "Member.all", attributeNodes = @NamedAttributeNode("team"))
public class Member extends JpaBaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String username;
    private int age;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    public Member(String username) {
        this.username = username;
    }

    public Member(String username, int age) {
        this.username = username;
        this.age = age;
    }

    public Member(String username, int age, Team team) {
        this.username = username;
        this.age = age;
        if (team != null) {
            changeTeam(team);
        }
    }

    //==연관관계 편의 메소드==//
    private void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }

}
