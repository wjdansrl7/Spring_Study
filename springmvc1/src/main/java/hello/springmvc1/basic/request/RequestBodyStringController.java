package hello.springmvc1.basic.request;

import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyStringController {

    @PostMapping("/request-body-string-v1")
    public void requestBodyString(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Stream은 바이트 코드로 들어오기 때문에 반드시 어떤 문자열로 인코딩 받을 건지 지정해줘야한다.
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}", messageBody);

        response.getWriter().write("ok");

    }

    @PostMapping("/request-body-string-v2")
    public void requestBodyStringV2(InputStream inputStream, Writer responseWriter) throws IOException {
        // Stream은 바이트 코드로 들어오기 때문에 반드시 어떤 문자열로 인코딩 받을 건지 지정해줘야한다.
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}", messageBody);

        responseWriter.write("ok");

    }

//    @PostMapping("/request-body-string-v3")
//    public HttpEntity requestBodyStringV3(HttpEntity<String> httpEntity) throws IOException {
//        // spring: http body에 있는 부분을 String으로 처리해서 넘겨줄게
//        String messageBody = httpEntity.getBody();
//
//        log.info("messageBody={}", messageBody);
//
//        return new HttpEntity("ok");
//    }

    @PostMapping("/request-body-string-v3")
    public HttpEntity requestBodyStringV3(RequestEntity<String> httpEntity) throws IOException {
        // spring: http body에 있는 부분을 String으로 처리해서 넘겨줄게
        String messageBody = httpEntity.getBody();

        log.info("messageBody={}", messageBody);

        return new ResponseEntity("ok", HttpStatus.CREATED);
    }

    @ResponseBody
    @PostMapping("/request-body-string-v4")
    public String requestBodyStringV4(@RequestBody String messageBody) {
        // spring: http body에 있는 부분을 String으로 처리해서 넘겨줄게

        log.info("messageBody={}", messageBody);

        return "ok";
    }


}
