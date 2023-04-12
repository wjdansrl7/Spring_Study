package jpql1;

import javassist.bytecode.ExceptionTable;

import javax.persistence.*;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member1 = new Member();
            member1.setUsername("memberA");
            member1.setAge(20);
            em.persist(member1);

            em.flush();
            em.clear();

            // projection에 걸린 엔티티들은 모두 영속성 컨텍스트에서 관리한다.
//            List<Member> result = em.createQuery("select m from Member m", Member.class)
//                    .getResultList();

//            List<Team> result = em.createQuery("select t from Member m join m.team t", Team.class)
//                    .getResultList();


//            Member findMember = result.get(0);
//            findMember.setAge(27);

//            for (Team team : result) {
//                System.out.println("team = " + team.getName());
//            }

            // 프로젝션 - 여러 값 조회
            // 타입을 명시하지 않으므로 Object 타입으로 나오게 된다.
//            List resultList = em.createQuery("select m.username, m.age from Member m")
//                    .getResultList();
//            Object o = resultList.get(0);
//            Object[] result = (Object[]) o;
//
//            System.out.println("result[0] = " + result[0]);
//            System.out.println("result[1] = " + result[1]);
//
//            List<Object[]> resultList1 = em.createQuery("select m.username, m.age from Member m")
//                    .getResultList();
//
//            Object[] result1 = resultList1.get(0);
//            System.out.println("result1[0] = " + result1[0]);
//            System.out.println("result1[1] = " + result1[1]);

            TypedQuery<Member> query1 = em.createQuery("select m from Member m", Member.class);
            TypedQuery<String> query2 = em.createQuery("select m.username from Member m", String.class);

            System.out.println("query2 = " + query2);

            Member result = em.createQuery("select m from Member m where m.username = :username", Member.class)
                    .setParameter("username", "memberA")
                    .getSingleResult();

            System.out.println("result.getUsername() = " + result.getUsername());

            // 파일 명 적어주는 것은 QueryDSL로 극복 가능
            List<MemberDTO> result1 = em.createQuery("select new jpql1.MemberDTO(m.username, m.age) from Member m", MemberDTO.class)
                    .getResultList();

            MemberDTO memberDTO = result1.get(0);
            System.out.println("memberDTO.getUsername() = " + memberDTO.getUsername());
            System.out.println("memberDTO.getAge( = " + memberDTO.getAge());




            tx.commit();

        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();

        } finally {
            em.close();
        }
    }
}
