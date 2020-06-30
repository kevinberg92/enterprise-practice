package org.kevinberg.konteprise.quizgame.service;


/*
you only do these two in SERVICES, and not in TESTS....
@Service
@Transactional
 */
//transactional always autowires entitymanager

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kevinberg.konteprise.quizgame.controller.UserInfoController;
import org.kevinberg.konteprise.quizgame.entity.MatchStats;
import org.kevinberg.konteprise.quizgame.services.MatchStatsService;
import org.kevinberg.konteprise.quizgame.services.UserService;
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
    private UserInfoController userInfoController;

    @Autowired
    private UserService userService;

    @Test
    public void testDefaultStats() {

        //does this method also work? without creating a string? hm. I WOULD NOT KNOW BECUASE NOTHING I DO WORKS :):l):):)
        userService.createUserWithPassword("kevin", "berg");

        MatchStats matchStats = matchStatsService.getMatchStats("kevin");

        assertEquals(0, matchStats.getVictories());
        assertEquals(0, matchStats.getDefeats());
    }


    @Test
    public void testStats() {
        String username = "kjetil";
        String password = "flatland";

        userService.createUserWithPassword(username, password);

        MatchStats matchStats = matchStatsService.getMatchStats(username);

        matchStatsService.reportVictory(username);

        assertEquals(1, matchStats.getVictories());
        assertEquals(0, matchStats.getDefeats());

        matchStatsService.reportDefeat(username);

        assertEquals(1, matchStats.getDefeats());
    }

}
