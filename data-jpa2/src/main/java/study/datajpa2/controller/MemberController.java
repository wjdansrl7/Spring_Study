package study.datajpa2.controller;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import study.datajpa2.dto.MemberDto;
import study.datajpa2.entity.Member;
import study.datajpa2.repository.MemberRepository;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

//    @GetMapping("/members/{id}")
//    public String findMember(@PathVariable("id") Long id) {
//        Member member = memberRepository.findById(id).get();
//        return member.getUsername();
//    }

    // HTTP 파라미터로 넘어온 엔티티의 아이디로 엔티티 객체를 찾아서 바인딩
    @GetMapping("/members/{id}")
    public String findMember(@PathVariable("id") Member member) {
        member.setUsername("member2");
        return member.getUsername();
    }

    @PostConstruct
    public void init() {
        for (int i = 0; i < 100; i++) {
            memberRepository.save(new Member("user" + i, i));
        }
    }

//    @GetMapping("/members")
//    public Page<Member> list(Pageable pageable) {
//        Page<Member> page = memberRepository.findAll(pageable);
//        return page;
//    }

    @GetMapping("/members")
    public Page<MemberDto> list(Pageable pageable) {
//        Page<Member> page = memberRepository.findAll(pageable);
//        Page<MemberDto> pageDto = page.map(MemberDto::new);
//        return pageDto;
        return memberRepository.findAll(pageable).map(MemberDto::new);
    }
}
