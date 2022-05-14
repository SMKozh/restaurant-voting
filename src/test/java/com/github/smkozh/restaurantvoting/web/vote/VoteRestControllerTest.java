package com.github.smkozh.restaurantvoting.web.vote;

import com.github.smkozh.restaurantvoting.model.Vote;
import com.github.smkozh.restaurantvoting.repository.vote.CrudVoteRepository;
import com.github.smkozh.restaurantvoting.util.JsonUtil;
import com.github.smkozh.restaurantvoting.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static com.github.smkozh.restaurantvoting.web.restaurant.RestaurantTestData.restaurant1;
import static com.github.smkozh.restaurantvoting.web.restaurant.RestaurantTestData.restaurant2;
import static com.github.smkozh.restaurantvoting.web.user.UserTestData.GUEST_MAIL;
import static com.github.smkozh.restaurantvoting.web.user.UserTestData.USER_MAIL;
import static com.github.smkozh.restaurantvoting.web.vote.VoteTestData.VOTE_MATCHER;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VoteRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = VoteRestController.REST_URL + "/";

    @Autowired
    private CrudVoteRepository crudVoteRepository;

    @Test
    @WithUserDetails(value = GUEST_MAIL)
    void createWithLocation() throws Exception {
        Vote newVote = VoteTestData.getNew();

        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL + restaurant1.id())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newVote)));

        Vote created = VOTE_MATCHER.readFromJson(action);
        int newId = created.id();
        newVote.setId(newId);
        VOTE_MATCHER.assertMatch(created, newVote);
        VOTE_MATCHER.assertMatch(crudVoteRepository.findById(newId).orElse(null), newVote);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateAfterDeadline() throws Exception {
        AbstractVoteController.setDeadLine(LocalTime.now().minus(5, ChronoUnit.MINUTES));
        Vote newVote = VoteTestData.getNew();

        perform(MockMvcRequestBuilders.post(REST_URL + restaurant2.id())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newVote)))
                .andExpect(status().isUnprocessableEntity());

        AbstractVoteController.setDeadLine(LocalTime.of(11, 0));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateBeforeDeadline() throws Exception {
        AbstractVoteController.setDeadLine(LocalTime.now().plus(5, ChronoUnit.MINUTES));
        Vote newVote = VoteTestData.getNew();

        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL + restaurant2.id())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newVote)));

        Vote created = VOTE_MATCHER.readFromJson(action);
        int newId = created.id();
        newVote.setId(newId);
        VOTE_MATCHER.assertMatch(created, newVote);
        VOTE_MATCHER.assertMatch(crudVoteRepository.findById(newId).orElse(null), newVote);
        AbstractVoteController.setDeadLine(LocalTime.of(11, 0));
    }
}