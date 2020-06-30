package org.kevinberg.konteprise.quizgame.service;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

public class ServiceTestBase {

    @Autowired
    private ResetService resetService;


    @BeforeEach
    public void cleanDatabase(){
        resetService.resetDatabase();
    }
}

