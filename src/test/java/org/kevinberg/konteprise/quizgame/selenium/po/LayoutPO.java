package org.kevinberg.konteprise.quizgame.selenium.po;

import org.kevinberg.konteprise.quizgame.selenium.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertTrue;

//all other PO's extends this now instead of PageObject....I guess.
public abstract class LayoutPO extends PageObject {

    //when to distinguish between this? and super(other) ??? OR BOTH?????
    public LayoutPO(WebDriver driver, String host, int port) {
        super(driver,host,port);
    }

    public LayoutPO(PageObject other) {
        super(other);
    }

    public SignupPO toSignup() {

        clickAndWait("linkToSignupId");

        SignupPO po = new SignupPO(this);
        assertTrue(po.isOnPage());

        return po;

    }

    public IndexPO doLogout() {
        //IDs of links and buttons in .xhtml files
        clickAndWait("logoutId");

        IndexPO po = new IndexPO(this);
        assertTrue(po.isOnPage());

        return po;
    }

    public boolean isLoggedIn() {
        //if logoutId > 0, then the button is visible. ie u are signed in
        return getDriver().findElements(By.id("logoutId")).size() > 0 &&
                getDriver().findElements(By.id("linkToSignupId")).isEmpty();
    }
}
