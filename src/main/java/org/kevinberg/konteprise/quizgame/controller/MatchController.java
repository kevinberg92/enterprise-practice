package org.kevinberg.konteprise.quizgame.controller;

import org.kevinberg.konteprise.quizgame.entity.Category;
import org.kevinberg.konteprise.quizgame.entity.Quiz;
import org.kevinberg.konteprise.quizgame.services.CategoryService;
import org.kevinberg.konteprise.quizgame.services.MatchStatsService;
import org.kevinberg.konteprise.quizgame.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
//this annotation always requires -----||--------
@SessionScoped
public class MatchController implements Serializable {


    @Autowired
    private CategoryService categoryService;

    @Autowired
    private QuizService quizService;

    @Autowired
    private MatchStatsService statsService;

    //can wire controllers aswell as services.
    @Autowired
    private UserInfoController userInfoController;


    private final int NUMBER_QUIZZES = 5;


    private Long selectedCategoryId;
    private int counter;
    private List<Quiz> questions;
    private boolean matchOn = false;



    public int getNumberOfQuizzes() {
        return NUMBER_QUIZZES;
    }

    public boolean isMatchOn() {
        return matchOn;
    }

    public String newMatch() {

        //reports a deafeat if u try to 'new match' without completing quiz
        String username = userInfoController.getUserName();
        //if not specified if statement parameters is always '== true'
        if(matchOn) {
            statsService.reportDefeat(username);
        }

        matchOn = true;
        selectedCategoryId = null;

        return "/ui/match.jsf?faces-redirect=true";
    }


    public void selectCategory(long id) {
        selectedCategoryId = id;
        counter = 0;
        questions = quizService.getRandomQuizzes(NUMBER_QUIZZES, selectedCategoryId);
    }

    public Quiz getCurrentQuiz() {
        return questions.get(counter);
    }



    public String answerQuiz(int index) {

        String username = userInfoController.getUserName();

        Quiz quiz = getCurrentQuiz();
        if(index == quiz.getCorrectAnswer()) {
            counter++;
            if(counter == NUMBER_QUIZZES) {
                matchOn = false;
                statsService.reportVictory(username);
                return "result.jsf?victory=true&faces-redirect=true";
            }
        }
        else {
            matchOn = false;
            statsService.reportDefeat(username);
            return "result.jsf?defeat=true&faces-redirect=true";
        }
        return null;
    }

    public int increaseCounter() {
        return counter++;
        //return counter + 1;
    }

    public List<Category> getCategories() {
        return categoryService.getAllCategories(false);
    }

    public boolean isCategorySelected() {
        return selectedCategoryId !=null;
    }


    public Long getSelectedCategoryId() {
        return selectedCategoryId;
    }

    public void setSelectedCategoryId(Long selectedCategoryId) {
        this.selectedCategoryId = selectedCategoryId;
    }

}
