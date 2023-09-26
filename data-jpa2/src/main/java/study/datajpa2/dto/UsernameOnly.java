package study.datajpa2.dto;

import org.springframework.beans.factory.annotation.Value;

public interface UsernameOnly {
    // 조회할 엔티티의 필드를 getter 형식으로 지정하면 해당 필드만 선택해서 조회(Projection)
    // 프로퍼티명
//    @Value("#{target.username + ' ' + target.age}") // open projections : 엔티티 전체를 다 가져와서 처리
    String getUsername(); // closed projections: 원하는 값만
}
