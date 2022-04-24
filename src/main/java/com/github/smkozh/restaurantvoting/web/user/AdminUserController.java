package com.github.smkozh.restaurantvoting.web.user;

import com.github.smkozh.restaurantvoting.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = AdminUserController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class AdminUserController extends AbstractUserController {
    static final String REST_URL = "api/admin/users";

    @GetMapping
    public List<User> getAll() {
        log.info("get all");
        return repository.findAll();
    }
}
