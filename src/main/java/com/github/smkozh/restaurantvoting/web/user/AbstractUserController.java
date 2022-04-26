package com.github.smkozh.restaurantvoting.web.user;

import com.github.smkozh.restaurantvoting.model.User;
import com.github.smkozh.restaurantvoting.repository.UserRepository;
import com.github.smkozh.restaurantvoting.util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;

@Slf4j
public abstract class AbstractUserController {

    @Autowired
    protected UserRepository repository;

    public ResponseEntity<User> get(int id) {
        log.info("get {}", id);
        return ResponseEntity.of(repository.findById(id));
    }

    @CacheEvict(value = "users", allEntries = true)
    public void delete(int id) {
        log.info("delete {}", id);
        repository.deleteById(id);
    }

    protected User prepareAndSave(User user) {
        return repository.save(UserUtil.prepareToSave(user));
    }
}
