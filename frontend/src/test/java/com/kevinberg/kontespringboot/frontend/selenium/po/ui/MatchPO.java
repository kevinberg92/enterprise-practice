package com.kevinberg.kontespringboot.frontend.selenium.po.ui;


import com.kevinberg.kontespringboot.frontend.selenium.PageObject;
import com.kevinberg.kontespringboot.frontend.selenium.po.LayoutPO;
import org.openqa.selenium.By;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

public class MatchPO extends LayoutPO {

    public MatchPO(PageObject other) {
        super(other);
    }

    @Override
    public boolean isOnPage() {
        return getDriver().getTitle().contains("Match");
        //return getDriver().getTitle().trim().equalsIgnoreCase("Match");
    }


      public boolean canSelectCategory() {
        return getCategoryIds().size() > 0;
    }


    public List<String> getCategoryIds() {
        return getDriver().findElements(By.xpath("//input[@data-ctgid]"))
                .stream()
                .map(e -> e.getAttribute("data-ctgid"))
                .collect(Collectors.toList());
    }

    public void chooseCategory(String id) {
        clickAndWait("newCtgBtnId" + id);
    }

    public boolean isQuestionDisplayed() {
        return getDriver().findElements(By.id("questionId")).size() > 0;
    }

    public int getQuestionCounter() {
        return getInteger("questionCounterId");
    }

    public long getQuizId() {
        String id = getDriver().findElement(By.xpath("//*[@data-quizid]")).getAttribute("data-quizid");
        return Long.parseLong(id);
    }

    public ResultPO answerQuestion(int index) {
        clickAndWait("answerId" + index);

        if(isOnPage()) {
            return null;
        }

        ResultPO po = new ResultPO(this);
        assertTrue(po.isOnPage());

        return po;
    }


}
