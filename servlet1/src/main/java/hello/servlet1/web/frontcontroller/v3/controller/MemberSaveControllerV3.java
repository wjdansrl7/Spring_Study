package hello.servlet1.web.frontcontroller.v3.controller;

import hello.servlet1.domain.member.Member;
import hello.servlet1.domain.member.MemberRepository;
import hello.servlet1.web.frontcontroller.ModelView;
import hello.servlet1.web.frontcontroller.v3.ControllerV3;

import java.util.Map;

public class MemberSaveControllerV3 implements ControllerV3 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public ModelView process(Map<String, String> paramMap) {

        String username = paramMap.get("username");

        return null;
    }
}
