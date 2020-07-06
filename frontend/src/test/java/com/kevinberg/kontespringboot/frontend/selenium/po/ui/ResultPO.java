package com.kevinberg.kontespringboot.frontend.selenium.po.ui;


import com.kevinberg.kontespringboot.frontend.selenium.PageObject;
import com.kevinberg.kontespringboot.frontend.selenium.po.LayoutPO;
import org.openqa.selenium.By;

public class ResultPO extends LayoutPO {

    //when to distinguish between this? and super(other) ???
    public ResultPO(PageObject other) {
        super(other);
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
