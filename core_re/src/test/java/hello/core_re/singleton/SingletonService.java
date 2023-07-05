package hello.core_re.singleton;

public class SingletonService {

    //1. static 영역에 객체를 딱 1개만 생성해준다.
    private static final SingletonService instance = new SingletonService();

    //2. public으로 열어서 객체 인스턴스가 필요하면  이 static 메서드를 통해서만 조회하도록 허용한다.
    public static SingletonService getInstance() {
        return instance;
    }

    //3. 생성자를 private으로 선언해서 외부에서 new 키워드를 사용한 객체 생성을 못하게 막는다.
    private SingletonService() {
    }

    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }

    // 싱글톤 패턴의 문제점
    /**
     * 싱글톤 패턴을 구현하는 코드 자체가 많이 들어간다.
     * 의존관계상 클라이언트가 구체 클래스에 의존한다. -> DIP 위반
     * 클라이언트가 구체 클래스에 의존해서 OCP 원칙을 위반할 가능성이 높다.
     * 테스트하기 어렵다.
     * 내부 속성을 변경하거나 초기화 하기 어렵다.
     * private 생성자로 자식 클래스를 만들기 어렵다.
     * 결론적으로 유연성이 떨어진다.
     * 안티패턴으로 불리기도 한다.
     */

    // 싱글톤 방식의 주의점
    /**
     * 싱글톤 패턴이든, 스프링 같은 싱글톤 컨테이너를 사용하든, 객체 인스턴스를 하나만 생성해서 공유하는 싱글톤 방식은
     * 여러 클라이언트가 하느이 같은 객체 인스턴스를 공유하기 때문에 싱글톤 객체는 상태를 유지(stateful)하게 설계하면 안된다.
     * 무상태(stateless)로 설계해야 한다.
     * 특정 클라이언트에 의존적인 필드가 있으면 안된다.
     * 특정 클라이언트가 값을 변경할 수 이쓴ㄴ 필드가 있으면 안된다.
     * 가급적 읽기만 가능해야 한다.
     * 필드 대신에 자바에서 공유되지 않는, 지역변수, 파라미터, ThreadLocal 등을 사용해야 한다.
     * 스프링 빈의 필드에 공유 값을 설정하면 정말 큰 장애를 발생할 수 있다.
     */

}
