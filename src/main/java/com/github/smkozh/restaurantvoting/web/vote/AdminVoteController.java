package com.github.smkozh.restaurantvoting.web.vote;

import com.github.smkozh.restaurantvoting.repository.vote.CrudVoteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = AdminVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
//@CacheConfig
public class AdminVoteController {

    static final String REST_URL = "api/admin/votes";

    @Autowired
    private CrudVoteRepository repository;


}
