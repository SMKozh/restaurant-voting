package com.github.smkozh.restaurantvoting.web.vote;

import com.github.smkozh.restaurantvoting.model.Vote;
import com.github.smkozh.restaurantvoting.repository.VoteRepository;
import com.github.smkozh.restaurantvoting.service.VoteService;
import com.github.smkozh.restaurantvoting.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.LocalTime;

import static com.github.smkozh.restaurantvoting.web.restaurant.RestaurantTestData.restaurant1;
import static com.github.smkozh.restaurantvoting.web.restaurant.RestaurantTestData.restaurant3;
import static com.github.smkozh.restaurantvoting.web.user.UserTestData.*;
import static com.github.smkozh.restaurantvoting.web.vote.VoteTestData.VOTE_MATCHER;
import static com.github.smkozh.restaurantvoting.web.vote.VoteTestData.vote3;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VoteControllerTest extends AbstractControllerTest {

    private static final String REST_URL = VoteController.REST_URL + "/";

    private final LocalTime DEADLINE = LocalTime.of(11, 0);

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private VoteService service;

    @Test
    @WithUserDetails(value = GUEST_MAIL)
    void createWithLocation() throws Exception {
        Vote newVote = VoteTestData.getNew();

        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .param("restaurantId", String.valueOf(restaurant1.id())));

        Vote created = VOTE_MATCHER.readFromJson(action);
        int newId = created.id();
        newVote.setId(newId);
        VOTE_MATCHER.assertMatch(created, newVote);
        VOTE_MATCHER.assertMatch(voteRepository.findById(newId).orElse(null), newVote);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "by-date")
                .param("date", LocalDate.now().toString()))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(vote3));

    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateAfterDeadline() throws Exception {
        VoteService.setDeadLine(LocalTime.MIN);
        Vote updatedVote = VoteTestData.getUpdated();

        perform(MockMvcRequestBuilders.put(REST_URL)
                .param("voteId", String.valueOf(updatedVote.id()))
                .param("restaurantId", String.valueOf(restaurant3.id())))
                .andExpect(status().isUnprocessableEntity());

        VoteService.setDeadLine(DEADLINE);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateBeforeDeadline() throws Exception {
        VoteService.setDeadLine(LocalTime.MAX);
        Vote updatedVote = VoteTestData.getUpdated();

        perform(MockMvcRequestBuilders.put(REST_URL)
                .param("voteId", String.valueOf(updatedVote.id()))
                .param("restaurantId", String.valueOf(restaurant3.id())));

        VOTE_MATCHER.assertMatch(service.get(updatedVote.getDate(), user.id()), updatedVote);
        VoteService.setDeadLine(DEADLINE);
    }
}