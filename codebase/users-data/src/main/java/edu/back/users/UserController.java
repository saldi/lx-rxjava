package edu.back.users;

import java.util.List;
import java.util.Random;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@Log4j2
public class UserController {

    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<User> justList(@RequestHeader("TraceId") String traceId) {
        logTraceId(traceId);
        return userRepository.findAll();
    }

    private void logTraceId(String traceId) {
        log.info("TraceId {}", traceId);
    }

    @GetMapping(params = "_sleep=random")
    public List<User> list(@RequestHeader("TraceId") String traceId) throws InterruptedException {
        int randomSleep = new Random().nextInt(2000);
        log.info("Request with sleep {} with TraceId {}", randomSleep, traceId);
        Thread.sleep(randomSleep);
        return userRepository.findAll();
    }

    @GetMapping(params = "_sleep")
    public List<User> list(@RequestParam("_sleep") Long sleep,
            @RequestHeader("TraceId") String traceId) throws InterruptedException {
        log.info("Request with sleep {} with TraceId {}", sleep, traceId);
        Thread.sleep(sleep);
        return userRepository.findAll();
    }

}
