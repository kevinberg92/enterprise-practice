package org.kevinberg.konteprise.quizgame.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.function.Supplier;

@Service
public class DefaultDataInitializerService {


    @Autowired
    private CategoryService categoryEjb;

    @Autowired
    private QuizService quizEjb;

    @Autowired
    private UserService userService;

    //@Startup, specified in class name not method
    @PostConstruct
    //@TransactionAttribute(NOT_SUPPORTED) //annotation is used on a method that needs to be executed after
    // dependency injection is done to perform any initialization
    public void init() {

        attempt(() -> userService.createUserWithPassword("Foo", "Bar"));

        //Use Long when the value can be null or not set, and long as it is a primitive cant be null, for failcheck etc.
        Long categoryIdZero = categoryEjb.createCategory("Anatomy");
        Long categoryIdOne = categoryEjb.createCategory("Music");

        Long sHemo = categoryEjb.createSubCategory(categoryIdZero, "Hemorrhoids");
        Long sMusc = categoryEjb.createSubCategory(categoryIdZero, "Muscles");
        Long sPoo = categoryEjb.createSubCategory(categoryIdZero, "Poop");
        long sRap = categoryEjb.createSubCategory(categoryIdOne, "Rap");
        long sClass = categoryEjb.createSubCategory(categoryIdOne, "Classical");

        quizEjb.createQuiz(sHemo, "How many types of hemorrhoids are there?",
                "0",
                "1",
                "2",
                "3",
                3);

        quizEjb.createQuiz(sClass, "who is called the Magnus Carlsen of classical music? /s",
                "Choping",
                "Mozart",
                "Bethoven",
                "Debussy",
                1);

    }

    //method that fails if its allready there? i.e not new instances of dummy-data
    private  <T> T attempt(Supplier<T> lambda){
        try{
            return lambda.get();
        }catch (Exception e){
            return null;
        }
    }
}
