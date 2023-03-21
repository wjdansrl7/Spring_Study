package hello.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// REST API를 만들 때 핵심적인 Controller, HTTP message body에 그대로 데이터를 넣는 행위
@Slf4j
@RestController
public class LogtestController {

//    @Slf4j가 아래 코드를 대신 넣어준다.
//    private final Logger log = LoggerFactory.getLogger(getClass());
//    private final Logger log = LoggerFactory.getLogger(LogtestController.class);

    @RequestMapping("/log-test")
    public String logTest() {
        String name = "Spring";

        // java 언어 특성상 +가 있으면 +연산자가 먼저 실행이 되고 -> log.trace("trace my log = Spring")
        // trace는 log level상 출력이 되지 않지만, +연산자를 사용해서 메모리와 resource를 잡아먹게 되므로 사용하면 안된다.
        // log.trace("tracy my log = " + name);

        log.trace("trace log={}", name);
        log.debug("debug log={}", name);
        log.info("info log={}", name);
        log.warn("warn log={}", name);
        log.error("error log={}", name);

        return "ok";
    }
}
