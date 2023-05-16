package jpql;

import javax.persistence.*;
import java.util.Collection;
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

//            Member member = new Member();
//            member.setUsername("member1");
//            member.setAge(10);
//            em.persist(member);
//
//            em.flush();
//            em.clear();
//
////            TypedQuery<Member> query1 = em.createQuery("select m from Member m", Member.class);
////            TypedQuery<String> query2 = em.createQuery("select m.username from Member m", String.class);
//
//            //반환타입이 불분명할 때
////            Query query3 = em.createQuery("select m.username, m.age from Member m");
//
//            // 만약 쿼리를 +로 받게되면 sql injection 공격을 받게 될 수 도 있다.
////            Member result = em.createQuery("select m from Member m where m.username = :username", Member.class)
////                    .setParameter("username", "member1")
////                    .getSingleResult();
////            System.out.println("singleResult = " + result.getUsername());
//
//
//            // projection에 걸린 엔티티들은 모두 영속성 컨텍스트에서 관리한다.
////            List<Member> result = em.createQuery("select m from Member m", Member.class)
////                    .getResultList();
//
//            // Join query를 쓸 때, sql도 알아볼 수 있게 쓰는게 좋다.
////            List<Member> result = em.createQuery("select t from Member m join m.team t", Team.class)
////                    .getResultList();
//
////            Member findMember = result.get(0);
////            findMember.setAge(20);
//
////            TODO: 프로젝션 - 여러 값 조회
//            // 타입을 명시하지 못하므로 Object 타입으로 나오게 된다.
////            1)
////            List resultList = em.createQuery("select m.username, m.age from Member m")
////                    .getResultList();
////            Object o = resultList.get(0);
////            Object[] result = (Object[]) o;
//
////            2)
////            List<Object[]> resultList = em.createQuery("select m.username, m.age from Member m")
////                    .getResultList();
////            Object[] result = resultList.get(0);
////            System.out.println("result = " + result[0]);
////            System.out.println("result = " + result[1]);
//
////            3) 파일명 적어주는 것은 QueryDSL로 극복 가능
//            List<MemberDTO> result = em.createQuery("select new jpql.MemberDTO(m.username, m.age) from Member m", MemberDTO.class)
//                    .getResultList();
//
//            MemberDTO memberDTO = result.get(0);
//            System.out.println("memberDTO.getUsername() = " + memberDTO.getUsername());
//            System.out.println("memberDTO.getAge() = " + memberDTO.getAge());

//            for (int i = 0; i < 100; i++) {
//                Member member = new Member();
//                member.setUsername("member" + i);
//                member.setAge(i);
//                em.persist(member);
//            }

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

//            String query = "select m.username, 'HELLO', TRUE From Member m " +
//                    "where m.type = :userType";
//                    "where m.type = jpql.MemberType.USER";
//            List<Object[]> result = em.createQuery(query)
//                    .setParameter("userType", MemberType.ADMIN)
//                    .getResultList();

//            for (Object[] objects : result) {
//                System.out.println("objects[0] = " + objects[0]);
//                System.out.println("objects[1] = " + objects[1]);
//                System.out.println("objects[2] = " + objects[2]);
//            }



            // 세타 조인
//            String query = "select m from Member m, Team t where m.username = t.name";

//            String query1 = "select m from Member m left join m.team t on t.name = 'TeamA'";

//            String query2 = "select m from Member m left join Team t on m.username = t.name";
//            List<Member> result = em.createQuery(query2, Member.class)
//                    .getResultList();

//            System.out.println("result.size() = " + result.size());

//            for (Member member1 : result) {
//                System.out.println("member1 = " + member1);
//            }



//            List<Member> result = em.createQuery("select m from Member m order by m.age desc", Member.class)
//                    .setFirstResult(1)
//                    .setMaxResults(10)
//                    .getResultList();

//            System.out.println("result.size() = " + result.size());

//            for (Member member1 : result) {
//                System.out.println("member1 = " + member1);
//            }
            // 조건 - CASE 식
//            String query =
//                    "select " +
//                            "case when m.age <= 10 then '학생요금'" +
//                            "     when m.age >= 60 then '경로요금'" +
//                            "     else '일반요금' " +
//                            "end " +
//                            "from Member m";
//            List<String> result = em.createQuery(query, String.class)
//                    .getResultList();

//            for (String s : result) {
//                System.out.println("s = " + s);
//            }

            // COALESCE: 하나씩 조회해서 null이 아니면 반환
            // 사용자 이름이 없으면 이름 없는 회원을 반환
//            String query1 = "select coalesce(m.username, '이름 없는 회원')" +
//                    " from Member m";

            // NULLIF: 두 값이 같으면 null 반환, 다르면 첫번째 값 반환
            // 사용자 이름이 '관리자'면 null을 반환하고, 나머지는 본인의 이름을 반환
//            String query1 = "select nullif(m.username, '관리자') as username " +
//                    "from Member m";
//            List<String> result2 = em.createQuery(query1, String.class)
//                    .getResultList();

//            for (String s : result2) {
//                System.out.println("s = " + s);
//            }


//            String query = "select concat('a','b') From Member m";
            // hibernate에서 제공
//            String query = "select 'a' || 'b' From Member m";
//            String query = "select substring(m.username, 2, 3) From Member m";
//            String query = "select locate('de', 'abcdefg') From Member m";
            // collection의 크기를 반환해줌.
//            String query = "select size(t.members) From Team t";

            // 사용자 정의 함수 사용
//            String query = "select function('group_concat', m.username) From Member m";
//            String query = "select group_concat(m.username) From Member m";


//            List<Integer> result2 = em.createQuery(query, Integer.class)
//                    .getResultList();

//            for (Integer integer : result2) {
//                System.out.println("integer = " + integer);
//            }

//            List<String> result = em.createQuery(query, String.class)
//                    .getResultList();

//            for (String s : result) {
//                System.out.println("s = " + s);
//            }

//            String query = "select t.members.size from Team t";
            // 컬렉션 값 연관 경로로 묵시적 내부 조인이 발생하고 탐색이 되지 않으므로
//            String query = "select t.members from Team t";
            // 탐색하고 싶다면 FROM 절에서 명시적 조인을 통해 별칭을 얻으면 별칭을 통해 탐색 가능
            // TODO: 다 필요없고, 실무에서는 묵시적 조인을 쓰지 말고, 명시적 조인을 쓰자
            // TODO: 명시적 조인이 query 튜닝하기도 쉽다.
//            String query = "select m.username From Team t join t.members m";

//            Integer result = em.createQuery(query, Integer.class)
//                    .getSingleResult();

//            List<Collection> result = em.createQuery(query, Collection.class)
//                    .getResultList();

//            System.out.println("result = " + result);

//            String query = "select m From Member m join fetch m.team";
            // 정합성 이슈 때문에 페치 조인에서는 별칭을 쓰지 않는다(예를 들어, 해당 팀에 속한 member가 5명인데,
            // 3명만 출력할 경우 이후에 여러 조건들로 출력했을 때 해당 쿼리에 대한 정합성 이슈가 생길 수 있다.
//            String query = "select distinct t From Team t join fetch t.members";
//            String query = "select t from Team t";
//            String query = "select m from Member m where m = : member";
//            String query = "select m from Member m where m.id = :memberId";
//            String query = "select m from Member m where m.team = :team";
//            List<Member> findMember = em.createQuery(query, Member.class)
//                    .setParameter("memberId", member1.getId())
//                    .setParameter("member", member1)
//                    .setParameter("team", teamA)
//                    .getResultList();
//                    .getSingleResult();

//            List<Team> result = em.createQuery(query, Team.class)
//                    .setFirstResult(0)
//                    .setMaxResults(2)
//                    .getResultList();

//            System.out.println("findMember = " + findMember);

//            for (Member member : findMember) {
//                System.out.println("member = " + member);
//            }

//            System.out.println("result.size() = " + result.size());



            // 일대다 관계는 데이터가 뻥튀기가 될 수 있다.
//            for (Team team : result) {
//                System.out.println("team.getName() = " + team.getName() + ", team.getMembers().size() = " + team.getMembers().size());
//                for (Member member : team.getMembers()) {
//                    System.out.println("-> member = " + member);
//                }
//            }


//            for (Member member : result) {
//                System.out.println("member = " + member.getUsername() + ", " + member.getTeam().getName());
                // 회원1, 팀A(SQL)
                // 회원2, 팀A(1차캐시)
                // 회원3, 팀B(SQL)

                // 회원 100명 query-> N + 1 하나의 query를 날렸는데 N개의 결과 query
//            }


            //NamedQuery 사용
//            List<Member> resultList = em.createNamedQuery("Member.findByUsername", Member.class)
//                    .setParameter("username", "회원1")
//                    .getResultList();

//            for (Member member : resultList) {
//                System.out.println("member = " + member);
//            }

            // Flush 자동 호출
            // 벌크 연산 수행 후 영속성 컨텍스트 초기화(데이터 정합성에 안맞을 수도 있으므로)
            int resultCount = em.createQuery("update Member m set m.age = 20")
                    .executeUpdate();

            System.out.println("resultCount = " + resultCount);

            em.clear();

            Member findMember = em.find(Member.class, member1.getId());
            System.out.println("findMember = " + findMember);

            // 영속성 컨텍스트에는 아직 반영이 안됐기 때문에
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

        emf.close();
    }

//    private static void logic(Member m1, Member m2) {
//        == 으로 비교하지마라.
//        System.out.println("m1 == m2: " + (m1.getClass() == m2.getClass()));
//        System.out.println("m1 == m2 " + (m1 instanceof Member));
//        System.out.println("m1 == m2 " + (m2 instanceof Member));

//    }
}
