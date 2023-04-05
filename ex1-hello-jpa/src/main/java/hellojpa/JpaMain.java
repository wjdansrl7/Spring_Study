package hellojpa;

import org.hibernate.Hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

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

//            Member member1 = new Member();
//            member.setId("ID_A");
//            member1.setUsername("A");

//            Member member2 = new Member();
//            member2.setUsername("B");

//            Member member3 = new Member();
//            member3.setUsername("C");

//          예외적으로 IDENTITY전략에서만 persist하면 바로 실행된다.
//          IDENTITY전략은 SQL문이 실행되어야만 그때 Pk를 생성하므로
//          1차 캐시에서는 식별자로 @ID를 쓰고 있는데, 따라서 바로 생성되어야 한다.
//          영속성 컨텍스트를 하려면 무조건 Pk를 가져와야 한다.
//          어? SEQUENCE 전략이네? DB에서 값을 얻어와야 겠다.!!!

//          따라서 SEQUENCE 전략은 값을 쭉 모아놨다가 한번에 commit 해줘도 되고,
//          IDENTITY 전략은 우선 select로 pk값을 얻어와야 하므로 바로 SQL문을 날려줘야 한다.
//            System.out.println("==================");

//          DB SEQ = 1 | 1
//          DB SEQ = 51 | 2
//          DB SEQ = 51 | 3

//            em.persist(member1);
//            em.persist(member2);
//            em.persist(member3);

//            System.out.println("member1.getId() = " + member1.getId());
//            System.out.println("member2.getId() = " + member2.getId());
//            System.out.println("member3.getId() = " + member3.getId());
//            System.out.println("==================");

//            Team team = new Team();
//            team.setName("TeamA");
//            team.getMembers().add(member);
//            em.persist(team);

//            Member member = new Member();
//            member.setUsername("member1");
//            member.setTeamId(team.getId());

//            연관관계 편의 메서드 -> 둘 중 하나만 작성해준다.
//            member.changeTeam(team);
//            team.addMember(member);

//            em.persist(member);

//            team.getMembers().add(member);

//            em.flush();
//            em.clear();

//            Team findTeam = em.find(Team.class, team.getId());
//            List<Member> members = findTeam.getMembers();
//
//            System.out.println("=================");
//            for (Member m : members) {
//                System.out.println("m.getUsername() = " + m.getUsername());
//            }
//            System.out.println("==================");

            // 연관관계가 없기 때문에
//            Member findMember = em.find(Member.class, member.getId());
//            List<Member> members = findMember.getTeam().getMembers();
//
//            for (Member m : members) {
//                System.out.println("m.getUsername() = " + m.getUsername());
//            }

//            Long findTeamId = findMember.getTeamId();
//            Team findTeam = findMember.getTeam();
//            Team findTeam = em.find(Team.class, findTeamId);

//            System.out.println("findTeam.getName() = " + findTeam.getName());

            // 팀을 바꾸고 싶을때 가정
//            Team newTeam = em.find(Team.class, 100L);
//            findMember.setTeam(newTeam);            // 생성
//            // Member member = new Member();
//            // member.setId(3L);
//            // member.setName("HelloC");
//
//            // 조회
////            Member findMember = em.find(Member.class, 1L);
////            System.out.println("findMember.id = " + findMember.getId());
////            System.out.println("findMember.name = " + findMember.getName());
//
//            // 삭제
////           em.remove(findMember);
//
//            // 수정, em.persist 햊쥐 않아도됨.
////            findMember.setName("HelloJpa");
//
//            // JPQL: 객체를 대상으로, 해당 SQL 방언에 맞춰서
////            List<Member> result = em.createQuery("select m from Member as m", Member.class)
////                    .getResultList();
////
////            for (Member member : result) {
////                System.out.println("member.getName() = " + member.getName());
////            }
//
//            // 비영속
////            Member member = new Member();
////            member.setId(101L);
////            member.setName("HelloJPA");
//
//            // 영속
////            System.out.println("===BEFORE ===");
////            em.persist(member);
////            System.out.println("=== AFTER ===");
//
//            // entityManager를 다시 실행했기 때문에 DB에서 SELECT문으로 가져와서 1차 캐시에 저장하므로
//            // 그 이후에 조회는 SELECT문이 실행되지 않는다.
////            Member findMember1 = em.find(Member.class, 101L);
////            Member findMember2 = em.find(Member.class, 101L);
//
//            // 영속 엔티티의 동일성 보장
////            System.out.println("result = " + (findMember1 == findMember2));
//
//            // 1차 캐시에 있었기 때문에 SELECT문이 따로 출력되지 않는다.
////            System.out.println("findMember.getId() = " + findMember.getId());
////            System.out.println("findMember.getName( = " + findMember.getName());
////            Member member1 = new Member(150L, "A");
////            Member member2 = new Member(160L, "B");
//
////            Member member1 = em.find(Member.class, 150L);
////            member1.setName("AAAAAA");
//
//            // 영속성 컨텍스트를 통째로 날려버린다.
////            em.clear();
//
////            Member member2 = em.find(Member.class, 150L);
//
//
//
////            해당 entity를 준영속으로 바꿔준다.
////            em.detach(member);
//
////            Member member = new Member(200L, "member200");
////            em.persist(member);
//
////            em.flush();
//
////            em.persist(member1);
////            em.persist(member2);
//
////            Member member = new Member();
////            member.setId(2L);
////            member.setName("memberA");
//
////            em.persist(member);
//
////            System.out.println("============================");
//
////            Member member1 = new Member();
////            member.setId("ID_A");
////            member1.setUsername("A");
//
////            Member member2 = new Member();
////            member2.setUsername("B");
//
////            Member member3 = new Member();
////            member3.setUsername("C");
//
////          예외적으로 IDENTITY전략에서만 persist하면 바로 실행된다.
////          IDENTITY전략은 SQL문이 실행되어야만 그때 Pk를 생성하므로
////          1차 캐시에서는 식별자로 @ID를 쓰고 있는데, 따라서 바로 생성되어야 한다.
////          영속성 컨텍스트를 하려면 무조건 Pk를 가져와야 한다.
////          어? SEQUENCE 전략이네? DB에서 값을 얻어와야 겠다.!!!
//
////          따라서 SEQUENCE 전략은 값을 쭉 모아놨다가 한번에 commit 해줘도 되고,
////          IDENTITY 전략은 우선 select로 pk값을 얻어와야 하므로 바로 SQL문을 날려줘야 한다.
////            System.out.println("==================");
//
////          DB SEQ = 1 | 1
////          DB SEQ = 51 | 2
////          DB SEQ = 51 | 3
//
////            em.persist(member1);
////            em.persist(member2);
////            em.persist(member3);
//
////            System.out.println("member1.getId() = " + member1.getId());
////            System.out.println("member2.getId() = " + member2.getId());
////            System.out.println("member3.getId() = " + member3.getId());
////            System.out.println("==================");
//
////            Team team = new Team();
////            team.setName("TeamA");
////            team.getMembers().add(member);
////            em.persist(team);
//
////            Member member = new Member();
////            member.setUsername("member1");
////            member.setTeamId(team.getId());
//
////            연관관계 편의 메서드 -> 둘 중 하나만 작성해준다.
////            member.changeTeam(team);
////            team.addMember(member);
//
////            em.persist(member);
//
////            team.getMembers().add(member);
//
////            em.flush();
////            em.clear();
//
////            Team findTeam = em.find(Team.class, team.getId());
////            List<Member> members = findTeam.getMembers();
////
////            System.out.println("=================");
////            for (Member m : members) {
////                System.out.println("m.getUsername() = " + m.getUsername());
////            }
////            System.out.println("==================");
//
//            // 연관관계가 없기 때문에
////            Member findMember = em.find(Member.class, member.getId());
////            List<Member> members = findMember.getTeam().getMembers();
////
////            for (Member m : members) {
////                System.out.println("m.getUsername() = " + m.getUsername());
////            }
//
////            Long findTeamId = findMember.getTeamId();
////            Team findTeam = findMember.getTeam();
////            Team findTeam = em.find(Team.class, findTeamId);
//
////            System.out.println("findTeam.getName() = " + findTeam.getName());
//
//            // 팀을 바꾸고 싶을때 가정
////            Team newTeam = em.find(Team.class, 100L);
////            findMember.setTeam(newTeam);
//
//            Member member = new Member();
//            member.setUsername("member1");
//
//            em.persist(member);
//
//            Team team = new Team();
//            team.setName("teamA");
//            team.getMembers().add(member);
//
//            em.persist(team);
//
//
//            tx.commit();

//            Member member = new Member();
//            member.setUsername("member1");
//
//            em.persist(member);
//
//            Team team = new Team();
//            team.setName("teamA");
//            team.getMembers().add(member);

//            em.persist(team);

//            Movie movie = new Movie();
//            movie.setDirector("aaaa");
//            movie.setActor("bbbb");
//            movie.setName("미녀와 야수");
//            movie.setPrice(10000);
//
//            em.persist(movie);
//
//            em.flush();
//            em.clear();

//            Movie findMovie = em.find(Movie.class, movie.getId());
//            System.out.println("findMovie = " + findMovie);
//            Item item = em.find(Item.class, movie.getId());
//            System.out.println("item = " + item);

//            Team team = new Team();
//            team.setName("TeamA");
//            em.persist(team);
//
//            Team teamB = new Team();
//            team.setName("TeamB");
//            em.persist(teamB);
//
//            Member member1 = new Member();
//            member1.setUsername("member1");
//            member1.setTeam(team);
//            em.persist(member1);
//
//
//            Member member2 = new Member();
//            member2.setUsername("member2");
//            member2.setTeam(teamB);
//            em.persist(member2);
//
//            em.flush();
//            em.clear();



//            em.find()는 pk를 찍어서 JPA가 내부적으로 최적화 해줄 수 있음.
//            Member m = em.find(Member.class, member1.getId());

//            JPQL은 이거 그대로 sql로 번역이 된다. 즉시 로딩은 sql문을 실행하면서 값을 다 가져와야 하므로
//            join을 통해 team도 가져오게 된다. 따라서 sql문이 두 개가 실행이 된다.
//            List<Member> members = em.createQuery("select m from Member m join fetch m.team", Member.class)
//                    .getResultList();

//            System.out.println("m.getTeam().getClass() = " + m.getTeam().getClass());

//            System.out.println("=========================");
//            System.out.println("teamName = " + m.getTeam().getName());
//            m.getTeam().getName(); // 초기화
//            System.out.println("teamName = " + m.getTeam().getName());
//            System.out.println("=========================");


//            Member refMember = em.getReference(Member.class, member1.getId());
//            Member m2 = em.getReference(Member.class, member2.getId());
//            logic(m1, m2);

//            System.out.println("refMember = " + refMember.getClass());
//            refMember.getUsername();  // 강제 초기화

//            Hibernate.initialize(refMember); // 프록시 강제 초기화


//            proxy 인스턴스의 초기화 여부 확인
//            System.out.println("isLoaded = " + emf.getPersistenceUnitUtil().isLoaded(refMember));


//            em.detach(refMember);


//            System.out.println("refMember.getUsername() = " + refMember.getUsername());


//            Member findMember = em.find(Member.class, member1.getId());

//            System.out.println("findMember = " + findMember.getClass());

            // JPA는 한 트랜잭션 내에 같은 것을 보장해줘야 하므로, repetableRead 보장
//            System.out.println("a == a: " + (refMember == findMember));





            //
//            Member findMember = em.find(Member.class, member.getId());
//            Member findMember = em.getReference(Member.class, member.getId());
//            System.out.println("before findMember = " + findMember.getClass());
//            System.out.println("findMember.id = " + findMember.getId());
//            proxy 객체가 어? 나 Member 객체가 없는데 하면서 영속성 컨텍스트에 요청하면 영속성 컨텍스트가 DB에서 조회하고
//            실제 Entity를 생성해서 target에 해당하는 메서드를 호출해준다.
//            System.out.println("findMember.username = " + findMember.getUsername());
//            System.out.println("findMember.username = " + findMember.getUsername());
//            System.out.println("after findMember = " + findMember.getClass());

//            Child child1 = new Child();
//            Child child2 = new Child();

//            Parent parent = new Parent();
//            parent.addChild(child1);
//            parent.addChild(child2);

//            em.persist(parent);

//            em.flush();
//            em.clear();

//            Parent findParent = em.find(Parent.class, parent.getId());
//            em.remove(findParent);

//            Address address = new Address("city", "street", "10000");

//            Member member1 = new Member();
//            member1.setUsername("member1");
//            member1.setHomeAddress(address);
//            em.persist(member1);

//            Address newAddress = new Address("newCity", address.getStreet(), address.getZipcode());
//            member1.setHomeAddress(newAddress);

//            member.setHomeAddress(new Address("city", "street", "10"));
//            member.setWorkPeriod(new Period());

//            em.persist(member);

            Member member = new Member();
            member.setUsername("member1");
            member.setHomeAddress(new Address("homeCity", "street", "1000"));

            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("피자");
            member.getFavoriteFoods().add("탕수육");

            member.getAddressHistory().add(new AddressEntity("old1", "street", "1000"));
            member.getAddressHistory().add(new AddressEntity("old2", "street", "1000"));

            em.persist(member);

            em.flush();
            em.clear();


//            Member 테이블만 조회된 것으로 보아 값 타입 컬렉션들은 모두 지연 로딩이다.
            System.out.println("============= START ==================");

//            TODO: 조회
            Member findMember = em.find(Member.class, member.getId());

//            List<Address> addressHistory = findMember.getAddressHistory();
//            for (Address address : addressHistory) {
//                System.out.println("address.getCity() = " + address.getCity());
//            }
//
//            Set<String> favoriteFoods = findMember.getFavoriteFoods();
//            for (String favoriteFood : favoriteFoods) {
//                System.out.println("favoriteFood = " + favoriteFood);
//            }

//            TODO: 수정
//            homeCity -> newCity
//            findMember.getHomeAddress().setCity("newCity"); => XXXX

//            값 타입 하나를 바꿔도 아예 새로 갈아 끼워야 한다.
            Address a = findMember.getHomeAddress();
            findMember.setHomeAddress(new Address("newCity", a.getStreet(), a.getZipcode()));

//            치킨 -> 한식
//            FavoriteFoods 자체가 String이기 때문에 update를 할 수가 없다. 따라서 새로 값을 넣어준다.
            findMember.getFavoriteFoods().remove("치킨");
            findMember.getFavoriteFoods().add("한식");

//            주소 변경

//            findMember.getAddressHistory().remove(new Address("old1", "street", "1000"));
//            findMember.getAddressHistory().add(new Address("newCity1", "street", "1000"));









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
