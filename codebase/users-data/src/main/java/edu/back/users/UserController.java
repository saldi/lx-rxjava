package edu.back.users;

import java.util.List;
import java.util.Random;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/{id}")
    public User byId(HttpEntity<String> entity, @PathVariable("id") Long id) {
        logTraceId(entity);
        return userRepository.findById(id).get();
    }

    @GetMapping(value="/{id}", params = "_sleep")
    public User byIdWithSleep(HttpEntity<String> entity,
            @RequestParam("_sleep") Long sleep,
            @PathVariable("id") Long id) throws InterruptedException {
        log.info("Request with sleep {} with TraceId {}", sleep,
                entity.getHeaders().get("TraceId"));
        Thread.sleep(sleep);
        return userRepository.findById(id).get();
    }

    @GetMapping
    public List<User> justList(HttpEntity<String> entity) {
        logTraceId(entity);
        return userRepository.findAll();
    }

    private void logTraceId(HttpEntity<String> entity) {
        log.info("TraceId {}", entity.getHeaders().get("TraceId"));
    }

    @GetMapping(params = "_sleep=random")
    public List<User> list(HttpEntity<String> entity) throws InterruptedException {
        int randomSleep = new Random().nextInt(2000);
        log.info("Request with random sleep {} with TraceId {}", randomSleep,
                entity.getHeaders().get("TraceId"));
        Thread.sleep(randomSleep);
        return userRepository.findAll();
    }

    @GetMapping(params = "_sleep")
    public List<User> list(@RequestParam("_sleep") Long sleep,
            HttpEntity<String> entity) throws InterruptedException {
        log.info("Request with sleep {} with TraceId {}", sleep,
                entity.getHeaders().get("TraceId"));
        Thread.sleep(sleep);
        return userRepository.findAll();
    }

}
