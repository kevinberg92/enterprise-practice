package com.kevinberg.kontespringboot.backend.service;


/*
you only do these two in SERVICES, and not in TESTS....
@Service
@Transactional
 */
//transactional always autowires entitymanager

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import com.kevinberg.kontespringboot.backend.entity.MatchStats;
import com.kevinberg.kontespringboot.backend.services.MatchStatsService;
import com.kevinberg.kontespringboot.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.Assert.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class MatchStatsServiceTest extends ServiceTestBase {

    @Autowired
    private MatchStatsService matchStatsService;

    @Autowired
    private UserService userService;

    @Test
    public void testDefaultStats() {

        //does this method also work? without creating a string? hm. I WOULD NOT KNOW BECUASE NOTHING I DO WORKS :):l):):)
        userService.createUserWithPassword("kevin", "berg");

        MatchStats matchStats = matchStatsService.getMatchStats("kevin");

        assertEquals(0, (int) matchStats.getVictories());
        assertEquals(0, (int) matchStats.getDefeats());
    }


    @Test
    public void testStats() {
        String username = "kjetil";
        String password = "flatland";

        userService.createUserWithPassword(username, password);

        matchStatsService.reportVictory(username);
        matchStatsService.reportDefeat(username);

        MatchStats matchStats = matchStatsService.getMatchStats(username);

        assertEquals(1, matchStats.getVictories());
        assertEquals(1, matchStats.getDefeats());
    }

}
