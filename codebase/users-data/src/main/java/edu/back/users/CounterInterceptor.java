package edu.back.users;


import java.util.concurrent.atomic.AtomicInteger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Log4j2
public class CounterInterceptor implements HandlerInterceptor {

    private AtomicInteger counter = new AtomicInteger(0);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler) throws Exception {
        if (request.getRequestURL().toString().endsWith("/reset")) {
            counter.set(0);
            return false;
        }
        log.info("Counter {}", counter.getAndIncrement());
        return true;
    }
}
