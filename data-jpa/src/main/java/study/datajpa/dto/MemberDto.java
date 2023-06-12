package study.datajpa.dto;

import lombok.Data;
import study.datajpa.entity.Member;

@Data
public class MemberDto {

    private Long id;
    private String username;
    private String teamName;

    public MemberDto(Long id, String username, String teamName) {
        this.id = id;
        this.username = username;
        this.teamName = teamName;
    }

    // entity -> dto로 바라보는건 X
    // dto -> entity는 괜춘 field에 넣는건 안됨,
    public MemberDto(Member member) {
        this.id = member.getId();
        this.username = member.getUsername();
    }
}
