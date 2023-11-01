package hello.login.web.session;

import hello.login.domain.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletResponse;

import static org.assertj.core.api.Assertions.*;

public class SessionManagerTest {

    SessionManager sessionManager = new SessionManager();

    @Test
    void sessionTest() throws Exception {
        // given

        MockHttpServletResponse response = new MockHttpServletResponse();

        // 세션 생성 -> 서버입장에선 웹브라우저로 쿠키가 나감.
        Member member = new Member();
        sessionManager.createSession(member, response);

        // 요청에 응답 쿠키 저장 -> 웹브라우저가 쿠키를 전달
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setCookies(response.getCookies());

        // 세션 조회
        Object result = sessionManager.getSession(request);
        assertThat(result).isEqualTo(member);

        // 세션 만료
        sessionManager.expire(request);
        Object expired = sessionManager.getSession(request);
        assertThat(expired).isNull();
        // when


        // then


     }
}
