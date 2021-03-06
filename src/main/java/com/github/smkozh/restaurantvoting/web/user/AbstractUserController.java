package com.github.smkozh.restaurantvoting.web.user;

import com.github.smkozh.restaurantvoting.model.User;
import com.github.smkozh.restaurantvoting.repository.UserRepository;
import com.github.smkozh.restaurantvoting.util.UserUtil;
import com.github.smkozh.restaurantvoting.util.validation.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

@Slf4j
public abstract class AbstractUserController {

    @Autowired
    protected UserRepository repository;

    @Autowired
    private UniqueMailValidator emailValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(emailValidator);
    }

    public User get(int id) {
        log.info("get {}", id);
        return ValidationUtil.checkNotFoundWithId(repository.findById(id).orElse(null), id);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        ValidationUtil.checkModification(repository.delete(id), id);
    }

    protected User prepareAndSave(User user) {
        return repository.save(UserUtil.prepareToSave(user));
    }
}
