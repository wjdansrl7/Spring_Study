package jpql;

import javax.persistence.*;
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

            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

            em.flush();
            em.clear();

//            TypedQuery<Member> query1 = em.createQuery("select m from Member m", Member.class);
//            TypedQuery<String> query2 = em.createQuery("select m.username from Member m", String.class);

            //반환타입이 불분명할 때
//            Query query3 = em.createQuery("select m.username, m.age from Member m");

            // 만약 쿼리를 +로 받게되면 sql injection 공격을 받게 될 수 도 있다.
//            Member result = em.createQuery("select m from Member m where m.username = :username", Member.class)
//                    .setParameter("username", "member1")
//                    .getSingleResult();
//            System.out.println("singleResult = " + result.getUsername());


            // projection에 걸린 엔티티들은 모두 영속성 컨텍스트에서 관리한다.
//            List<Member> result = em.createQuery("select m from Member m", Member.class)
//                    .getResultList();

            // Join query를 쓸 때, sql도 알아볼 수 있게 쓰는게 좋다.
//            List<Member> result = em.createQuery("select t from Member m join m.team t", Team.class)
//                    .getResultList();

//            Member findMember = result.get(0);
//            findMember.setAge(20);

//            TODO: 프로젝션 - 여러 값 조회
            // 타입을 명시하지 못하므로 Object 타입으로 나오게 된다.
//            1)
//            List resultList = em.createQuery("select m.username, m.age from Member m")
//                    .getResultList();
//            Object o = resultList.get(0);
//            Object[] result = (Object[]) o;

//            2)
//            List<Object[]> resultList = em.createQuery("select m.username, m.age from Member m")
//                    .getResultList();
//            Object[] result = resultList.get(0);
//            System.out.println("result = " + result[0]);
//            System.out.println("result = " + result[1]);

//            3) 파일명 적어주는 것은 QueryDSL로 극복 가능
            List<MemberDTO> result = em.createQuery("select new jpql.MemberDTO(m.username, m.age) from Member m", MemberDTO.class)
                    .getResultList();

            MemberDTO memberDTO = result.get(0);
            System.out.println("memberDTO.getUsername() = " + memberDTO.getUsername());
            System.out.println("memberDTO.getAge() = " + memberDTO.getAge());


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        emf.close();
    }

//    private static void logic(Member m1, Member m2) {
//        == 으로 비교하지마라.
//        System.out.println("m1 == m2: " + (m1.getClass() == m2.getClass()));
//        System.out.println("m1 == m2 " + (m1 instanceof Member));
//        System.out.println("m1 == m2 " + (m2 instanceof Member));

//    }
}
