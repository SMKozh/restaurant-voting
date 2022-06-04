package com.github.smkozh.restaurantvoting.web.vote;

import com.github.smkozh.restaurantvoting.model.Vote;
import com.github.smkozh.restaurantvoting.service.VoteService;
import com.github.smkozh.restaurantvoting.web.AuthUser;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController {
    static final String REST_URL = "/api/votes";

    @Autowired
    private VoteService service;

    @Transactional
    @PostMapping
    public ResponseEntity<Vote> createWithLocation(@RequestParam int restaurantId, @Parameter(hidden = true) @AuthenticationPrincipal AuthUser authUser) {
        Vote created = service.create(restaurantId, authUser.id());

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestParam int restaurantId, @Parameter(hidden = true) @AuthenticationPrincipal AuthUser authUser) {
        service.update(restaurantId, authUser.id());
    }

    @GetMapping("/by-date")
    public Vote get(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, @Parameter(hidden = true) @AuthenticationPrincipal AuthUser authUser) {
        return service.get(date, authUser.id());
    }
}
