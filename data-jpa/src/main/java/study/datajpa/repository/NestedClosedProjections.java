package study.datajpa.repository;

public interface NestedClosedProjections {

    // 중첩 구조 처리
    // root는 정확히 가져오지만,
    String getUsername();

    // 이후에는 연관된 엔티티를 모두 가져온다.
    // join이 들어가는 순간 에매해진다.
    TeamInfo getTeam();

    interface TeamInfo {
        String getName();
    }

}
