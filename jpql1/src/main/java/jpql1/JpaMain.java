package jpql1;


import javax.persistence.*;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Team teamA = new Team();
            teamA.setName("팀A");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("팀B");
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("회원1");
            member1.setTeam(teamA);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("회원2");
            member2.setTeam(teamA);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("회원3");
            member3.setTeam(teamB);
            em.persist(member3);

            em.flush();
            em.clear();

//            String query = "select m from Member m join fetch m.team";
//            String query = "select m from Member m join fetch m.team";
//            String query = "select t from Team t";


//            List<Team> result = em.createQuery(query, Team.class)
//                    .setFirstResult(0)
//                    .setMaxResults(2)
//                    .getResultList();

//            System.out.println("result.size() = " + result.size());


            // Collection은 DB 테이블에 출력되는 수만큼 반환한다.
//            for (Team team : result) {
//                System.out.println("member = " + member.getUsername() + ", " + member.getTeam().getName());
//                System.out.println("team = " + team.getName() + "|" + team.getMembers().size());
//                for (Member member : team.getMembers()) {
//                    System.out.println("member = " + member);
//                }
                // 회원1, 팀A(SQL)
                // 회원2, 팀A(1차캐시)
                // 회원3, 팀B(SQL)

                // 회원 100명 -> N + 1
//            }

//            String query = "select t.members from Team t";
//            Collection result = em.createQuery(query, Collection.class)
//                    .getResultList();
//
//            for (Object s : result) {
//                System.out.println("s = " + s);
//            }

//            String query = "select t.members.size from Team t";
//            String query = "select m.username from Team t join t.members m";


//            Integer result = em.createQuery(query, Integer.class)
//                    .getSingleResult();

//            Collection result = em.createQuery(query, Collection.class)
//                    .getResultList();

//            System.out.println("result = " + result);


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

//            TypedQuery<Member> query1 = em.createQuery("select m from Member m", Member.class);
//            TypedQuery<String> query2 = em.createQuery("select m.username from Member m", String.class);
//
//            System.out.println("query2 = " + query2);
//
//            Member result = em.createQuery("select m from Member m where m.username = :username", Member.class)
//                    .setParameter("username", "memberA")
//                    .getSingleResult();
//
//            System.out.println("result.getUsername() = " + result.getUsername());
//
//            // 파일 명 적어주는 것은 QueryDSL로 극복 가능
//            List<MemberDTO> result1 = em.createQuery("select new jpql1.MemberDTO(m.username, m.age) from Member m", MemberDTO.class)
//                    .getResultList();
//
//            MemberDTO memberDTO = result1.get(0);
//            System.out.println("memberDTO.getUsername() = " + memberDTO.getUsername());
//            System.out.println("memberDTO.getAge( = " + memberDTO.getAge());

//            String query = "select m from Member m where m = :member";
//            List resultList = em.createQuery(query)
//                    .setParameter("member", member1)
//                    .getResultList();

//            String query2 = "select m from Member m where m.id = :memberId";

//            List memberId = em.createQuery(query2)
//                    .setParameter("memberId", member1.getId())
//                    .getResultList();

//            System.out.println("resultList = " + resultList);
//            System.out.println("memberId = " + memberId);

//            Team team = em.find(Team.class, 1L);

//            String query = "select m from Member m where m.team = :teamId";
//            List resultList = em.createQuery(query)
//                    .setParameter("teamId", team)
//                    .getResultList();

//            System.out.println("resultList = " + resultList);

            // NamedQuery 사용
//            List<Member> resultList = em.createNamedQuery("Member.findByUsername", Member.class)
//                    .setParameter("username", "회원1")
//                    .getResultList();

//            for (Member member : resultList) {
//                System.out.println("member = " + member);
//            }

            // 벌크 연산 -> Modifiying Queries : Spring data JPA가 이걸로 도와줌.
            int resultCount = em.createQuery("update Member m set m.age = 20")
                    .executeUpdate();

            System.out.println("resultCount = " + resultCount);

            em.clear();

            Member findMember = em.find(Member.class, member1.getId());
            System.out.println("findMember.getAge() = " + findMember.getAge());


//            System.out.println("member1 = " + member1.getAge());
//            System.out.println("member2 = " + member2.getAge());
//            System.out.println("member3 = " + member3.getAge());


            tx.commit();

        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();

        } finally {
            em.close();
        }
    }
}
