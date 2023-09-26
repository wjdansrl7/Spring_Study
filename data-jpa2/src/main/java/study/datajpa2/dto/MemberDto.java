package study.datajpa2.dto;

import lombok.Data;
import study.datajpa2.entity.Member;

@Data
public class MemberDto {
    private Long id;
    private String username;

    public MemberDto(Member m) {
        this.id = m.getId();
        this.username = m.getUsername();
    }

    public MemberDto(Long id, String username) {
        this.id = id;
        this.username = username;
    }
}
