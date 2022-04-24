package com.github.smkozh.restaurantvoting;

import com.github.smkozh.restaurantvoting.model.Role;
import com.github.smkozh.restaurantvoting.model.User;
import com.github.smkozh.restaurantvoting.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Set;

@SpringBootApplication
@AllArgsConstructor
@Slf4j
public class RestaurantVotingApplication implements ApplicationRunner {
    private final UserRepository userRepository;
//    private static final Logger log = LoggerFactory.getLogger(RestaurantVotingApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(RestaurantVotingApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        log.info("main run");
//        userRepository.save(new User("user@gmail.com", "User_First", "User_Last", "password", Set.of(Role.ROLE_USER)));
//        userRepository.save(new User("admin@javaops.ru", "Admin_First", "Admin_Last", "admin", Set.of(Role.ROLE_USER, Role.ROLE_ADMIN)));
        System.out.println(userRepository.findAll());
        System.out.println("---------------------------");
        System.out.println(userRepository.findByLastNameContainingIgnoreCase("last"));
    }
}
