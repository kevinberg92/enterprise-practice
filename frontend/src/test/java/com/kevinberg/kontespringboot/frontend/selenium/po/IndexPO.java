package com.kevinberg.kontespringboot.frontend.selenium.po;


import com.kevinberg.kontespringboot.frontend.selenium.PageObject;
import com.kevinberg.kontespringboot.frontend.selenium.po.ui.MatchPO;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertTrue;

//index = homepage = "/"
public class IndexPO extends LayoutPO {

    //when to distinguish between this? and super(other) ???
    public IndexPO(WebDriver driver, String host, int port) {
        super(driver,host,port);
    }

    public IndexPO(PageObject other) {
        super(other);
    }

    public void toStartingPage() {
        getDriver().get(host + ":" + port);
    }

    @Override
    public boolean isOnPage() {
        return getDriver().getTitle().contains("Quiz Game");
    }

    public MatchPO startNewMatch() {

        clickAndWait("newMatchBtnId");
        MatchPO po = new MatchPO(this);
        assertTrue(po.isOnPage());

        return po;
    }
}
