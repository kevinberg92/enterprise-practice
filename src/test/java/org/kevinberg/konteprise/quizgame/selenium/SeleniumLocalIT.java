package org.kevinberg.konteprise.quizgame.selenium;


import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kevinberg.konteprise.quizgame.Application;
import org.kevinberg.konteprise.quizgame.selenium.po.IndexPO;
import org.kevinberg.konteprise.quizgame.selenium.po.SignupPO;
import org.kevinberg.konteprise.quizgame.selenium.po.ui.MatchPO;
import org.kevinberg.konteprise.quizgame.selenium.po.ui.ResultPO;
import org.kevinberg.konteprise.quizgame.services.QuizService;
import org.kevinberg.konteprise.quizgame.services.UserService;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.kevinberg.konteprise.quizgame.selenium.PageObject.getUniqueId;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class, webEnvironment = RANDOM_PORT)
public class SeleniumLocalIT {

    private static WebDriver driver;

    @LocalServerPort
    private int port;

    @Autowired
    private QuizService quizService;

    @Autowired
    private UserService userService;


    @BeforeAll
    public static void initClass() {
        driver = SeleniumDriverHandler.getFirefoxDriver();

        assumeTrue(driver != null, "Cannot find/Init Firefox driver");
    }

    @AfterAll
    public static void tearDown() {
        if(driver !=null) {
            driver.close();
        }
    }


    private IndexPO home;

    @BeforeEach
    public void initTest() {
        /*
            we want to have a new session, otherwise the tests
            will share the same Session beans
         */


        driver.manage().deleteAllCookies();

        home = new IndexPO(driver, "localhost", port);

        home.toStartingPage();

        assertTrue(home.isOnPage(), "Failed to start from Home Page");

    }

    //from home page, click the new match button, and verify you can see the category selection
    /*@Test
    public void testNewMatch() {
        MatchPO po = home.startNewMatch();
        assertTrue(po.canSelectCategory());
    }
    */

    //testFirstQuiz(): from home page, click the new match button. On the match page, select a category.
    // Verify that a quiz is displayed, and that the categories are no longer displayed.

    @Test
    public void testFirstQuiz() {

        createNewUser(getUniqueId(), "123");
        MatchPO po = home.startNewMatch();
        String ctgId = po.getCategoryIds().get(0);

        assertTrue(po.canSelectCategory());
        assertFalse(po.isQuestionDisplayed());

        po.chooseCategory(ctgId);

        assertFalse(po.canSelectCategory());
        assertTrue(po.isQuestionDisplayed());
    }

    //testWrongAnswer(): start a new match (click on the new match button and then choose a category). At the first quiz, answer it wrongly.
    // Verify that you can see the "lost" message and not the "win" one.

    @Test
    public void testWrongAnswer() {

        createNewUser(getUniqueId(), "123");
        MatchPO po = home.startNewMatch();
        String ctgId = po.getCategoryIds().get(0);
        po.chooseCategory(ctgId);

        long quizId = po.getQuizId();

        int rightAnswer = quizService.getQuiz(quizId).getCorrectAnswer();
        int wrongAnswer = rightAnswer+1;

        ResultPO resultPO = po.answerQuestion(wrongAnswer);
        assertNotNull(resultPO);

        assertTrue(resultPO.haveLost());
        assertFalse(resultPO.haveWon());

    }

    //testWinAMatch(): start a new match (click on the new match button and then choose a category).
    // Correctly answer the 5 quizzes in the match. At each answer, do verify that the current displayed
    // quiz counter increases by 1 (i.e., from 1 to 5). Once match is over, do verify that you can see the
    // "win" message and not the "lost" one.

    @Test
    public void testWinAMatch() {

        createNewUser(getUniqueId(), "123");
        MatchPO po = home.startNewMatch();
        String ctgId = po.getCategoryIds().get(0);
        po.chooseCategory(ctgId);

        ResultPO resultPO = null;

        for(int i= 0; i <=5; i++) {
            assertTrue(po.isQuestionDisplayed());
            assertEquals(i, po.getQuestionCounter());

            long quizId = po.getQuizId();
            int rightAnswer = quizService.getQuiz(quizId).getCorrectAnswer();

            resultPO = po.answerQuestion(rightAnswer);

            if(i != 5) {
                assertNull(resultPO);
            }
        }

        assertTrue(resultPO.haveWon());
        assertFalse(resultPO.haveLost());
    }

    //why is this method here and not in a service or PO or whatTheFuckingEver

    private IndexPO createNewUser(String username, String password) {

        home.toStartingPage();

        //fucking confusing syntax swaps and poop in my ass
        SignupPO signupPO = home.toSignup();

        //this is because the method public IndexPO createUser()
        IndexPO indexPO = signupPO.createUser(username, password);
        assertNotNull(indexPO);

        return indexPO;
    }


    @Test
    public void testCreateAndLogoutUser() {
        assertFalse(home.isLoggedIn());

        String username = getUniqueId();
        String password = "12345";
        //spaghetti aids web
        home = createNewUser(username,password);

        assertTrue(home.isLoggedIn());
        //eg "Welcome <username> !!!
        assertTrue(home.getDriver().getPageSource().contains(username));

        home.doLogout();

        assertFalse(home.isLoggedIn());
        assertFalse(home.getDriver().getPageSource().contains(username));
    }


}
