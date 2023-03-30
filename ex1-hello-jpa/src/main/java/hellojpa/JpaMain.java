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

            Member member1 = em.find(Member.class, 150L);
            member1.setName("AAAAAA");

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
            System.out.println("============================");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
