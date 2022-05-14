package com.github.smkozh.restaurantvoting.web.vote;

import com.github.smkozh.restaurantvoting.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.github.smkozh.restaurantvoting.web.restaurant.RestaurantTestData.restaurant2;
import static com.github.smkozh.restaurantvoting.web.user.UserTestData.ADMIN_MAIL;
import static com.github.smkozh.restaurantvoting.web.vote.VoteTestData.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminVoteControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminVoteController.REST_URL + "/";

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAllForRestaurant() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + restaurant2.id()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_WITH_USER_AND_RESTAURANT_MATCHER.contentJson(vote1, vote2, vote4));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAllForRestaurantAndDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "by-date/" + restaurant2.id())
                .param("date", "2020-02-15"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_WITH_USER_AND_RESTAURANT_MATCHER.contentJson(vote1, vote2));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getCountForRestaurant() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "count/" + restaurant2.id()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("3"));
    }
}