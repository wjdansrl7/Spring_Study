package study.datajpa.repository;


import org.springframework.beans.factory.annotation.Value;

public interface UsernameOnly {

    // open projection
    // entity를 다 가지고 와서 처리
    @Value("#{target.username + ' ' + target.age}")
    String getUsername();

    // close projection
    // Interface 기반의 projection
    // String getUsername();
}
