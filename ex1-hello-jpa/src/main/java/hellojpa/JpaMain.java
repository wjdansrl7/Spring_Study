package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        //Application Loading 시점에 하나만
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        //database connection 하나를 받았다.
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            // 생성
            // Member member = new Member();
            // member.setId(3L);
            // member.setName("HelloC");

            // 조회
//            Member findMember = em.find(Member.class, 1L);
//            System.out.println("findMember.id = " + findMember.getId());
//            System.out.println("findMember.name = " + findMember.getName());

            // 삭제
//           em.remove(findMember);

            // 수정, em.persist 햊쥐 않아도됨.
//            findMember.setName("HelloJpa");

            // JPQL: 객체를 대상으로, 해당 SQL 방언에 맞춰서
//            List<Member> result = em.createQuery("select m from Member as m", Member.class)
//                    .getResultList();
//
//            for (Member member : result) {
//                System.out.println("member.getName() = " + member.getName());
//            }

            // 비영속
//            Member member = new Member();
//            member.setId(101L);
//            member.setName("HelloJPA");

            // 영속
//            System.out.println("===BEFORE ===");
//            em.persist(member);
//            System.out.println("=== AFTER ===");

            // entityManager를 다시 실행했기 때문에 DB에서 SELECT문으로 가져와서 1차 캐시에 저장하므로
            // 그 이후에 조회는 SELECT문이 실행되지 않는다.
//            Member findMember1 = em.find(Member.class, 101L);
//            Member findMember2 = em.find(Member.class, 101L);

            // 영속 엔티티의 동일성 보장
//            System.out.println("result = " + (findMember1 == findMember2));

            // 1차 캐시에 있었기 때문에 SELECT문이 따로 출력되지 않는다.
//            System.out.println("findMember.getId() = " + findMember.getId());
//            System.out.println("findMember.getName( = " + findMember.getName());
//            Member member1 = new Member(150L, "A");
//            Member member2 = new Member(160L, "B");

//            Member member1 = em.find(Member.class, 150L);
//            member1.setName("AAAAAA");

            // 영속성 컨텍스트를 통째로 날려버린다.
//            em.clear();

//            Member member2 = em.find(Member.class, 150L);



//            해당 entity를 준영속으로 바꿔준다.
//            em.detach(member);

//            Member member = new Member(200L, "member200");
//            em.persist(member);

//            em.flush();

//            em.persist(member1);
//            em.persist(member2);

//            Member member = new Member();
//            member.setId(2L);
//            member.setName("memberA");

//            em.persist(member);

//            System.out.println("============================");

            Member member1 = new Member();
//            member.setId("ID_A");
            member1.setUsername("A");

            Member member2 = new Member();
            member2.setUsername("B");

            Member member3 = new Member();
            member3.setUsername("C");

//          예외적으로 IDENTITY전략에서만 persist하면 바로 실행된다.
//          IDENTITY전략은 SQL문이 실행되어야만 그때 Pk를 생성하므로
//          1차 캐시에서는 식별자로 @ID를 쓰고 있는데, 따라서 바로 생성되어야 한다.
//          영속성 컨텍스트를 하려면 무조건 Pk를 가져와야 한다.
//          어? SEQUENCE 전략이네? DB에서 값을 얻어와야 겠다.!!!

//          따라서 SEQUENCE 전략은 값을 쭉 모아놨다가 한번에 commit 해줘도 되고,
//          IDENTITY 전략은 우선 select로 pk값을 얻어와야 하므로 바로 SQL문을 날려줘야 한다.
            System.out.println("==================");

//          DB SEQ = 1 | 1
//          DB SEQ = 51 | 2
//          DB SEQ = 51 | 3

            em.persist(member1);
            em.persist(member2);
            em.persist(member3);

            System.out.println("member1.getId() = " + member1.getId());
            System.out.println("member2.getId() = " + member2.getId());
            System.out.println("member3.getId() = " + member3.getId());
            System.out.println("==================");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
