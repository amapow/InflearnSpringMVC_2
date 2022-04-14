package hello.springmvc.basic.request;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResponseErrorHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
public class RequestParamController {

    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request,
                               HttpServletResponse response,
                               HttpMethod method) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
        log.info("username={}, age={}, method={}", username, age, method);

        response.getWriter().write("ok ; " + username + age + "  " + method);
    }

    @ResponseBody
    @RequestMapping("/request-param-v2")
    public String requestParamV2(
            @RequestParam("username") String memberName,
            @RequestParam("age") int memberAge,
            HttpMethod method) {
        log.info("username={}, age={}, method={}", memberName, memberAge, method);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParamV3(
            @RequestParam String username,
            @RequestParam int age,
            HttpMethod method) {
        log.info("username={}, age={}, method={}", username, age, method);
        return "ok";
    }

    /**
     * @RequestParam이 없어도 변수를 받아올 수 있으나 명확히 이해하기 힘들어 넣는 것을 추천
     */

    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String requestParamV4(String username, int age, HttpMethod method, HttpServletRequest request) {
        log.info("username={}, age={}, method={}, URIinfo={}", username, age, method, request.getRequestURI());
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-required")
    public String requestParamRequired(
            @RequestParam String username,
            @RequestParam(required = false) Integer age, HttpMethod method, HttpServletRequest request) {       //int는 자바의 기본타입으로 null을 넣을 수 없음, 따라서 null을 핸들링
        //해야하는 경우 객체인 Integer를 사용하자
        log.info("username={}, age={}, method={}, URIinfo={}", username, age, method, request.getRequestURI());
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-default")
    public String requestParamDefault(
            @RequestParam(defaultValue = "guest") String username, //defaultvalue는 빈문자도 처리해줌
            @RequestParam(required = false, defaultValue = "-1") int age, HttpMethod method, HttpServletRequest request) {
        log.info("username={}, age={}, method={}, URIinfo={}", username, age, method, request.getRequestURI());
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParammap(
            @RequestParam Map<String, Object> paramMap, HttpMethod method, HttpServletRequest request) {
        log.info("username={}, age={}, method={}, URIinfo={}", paramMap.get("username"), paramMap.get("age"),
                paramMap, request.getRequestURI());
        return "ok";
    }

//    @ResponseBody
//    @RequestMapping("/model-attribute-v1")
//    public String modelAttributeV1(HttpServletRequest request) {
//        HelloData helloData = new HelloData();
//        helloData.setUsername(request.getParameter("username"));
//        helloData.setAge(Integer.parseInt(request.getParameter("age")));
//        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
//        return "ok";
//    }

    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1(@ModelAttribute HelloData helloData) {
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return "ok";
    }

    /**
     * @ModelAttribute 생략가능
     * String, int, Integer 같은 단순 타입은 @RequestParam이, 그 외 나머지는 @ModelAttribute가 바인딩한다. (Argument resolver로 지정해둔 타입 제외)
     */
    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    public String modelAttributeV2(HelloData helloData) {
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return "ok";
    }
}
