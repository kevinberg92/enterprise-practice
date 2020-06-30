package org.kevinberg.konteprise.quizgame.selenium.po.ui;

import org.kevinberg.konteprise.quizgame.selenium.PageObject;
import org.kevinberg.konteprise.quizgame.selenium.po.LayoutPO;
import org.openqa.selenium.By;

public class ResultPO extends LayoutPO {

    //when to distinguish between this? and super(other) ???
    public ResultPO(PageObject pooper) {
        super(pooper);
    }

    @Override
    public boolean isOnPage() {
        return getDriver().getTitle().contains("Match Result");
    }

    public boolean haveWon() {
        return getDriver().findElements(By.id("wonId")).size()>0;
    }

    public boolean haveLost() {
        return getDriver().findElements(By.id("lostId")).size()>0;
    }
}
