package edu.back.users;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class UsersDataApplication implements WebMvcConfigurer {

    @Autowired
    private CounterInterceptor counterInterceptor;

    public static void main(String[] args) {
        SpringApplication.run(UsersDataApplication.class, args);
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(counterInterceptor);
    }
}
