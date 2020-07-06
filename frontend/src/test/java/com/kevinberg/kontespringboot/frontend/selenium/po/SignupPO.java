package com.kevinberg.kontespringboot.frontend.selenium.po;


import com.kevinberg.kontespringboot.frontend.selenium.PageObject;

public class SignupPO extends LayoutPO {

    public SignupPO(PageObject other) {
        super(other);
    }

    @Override
    public boolean isOnPage() {
        return getDriver().getTitle().contains("Sign Up");
    }


    public IndexPO createUser(String username, String password) {
        //why is this returning indexPO when everything is in singup
        //because the redirect is po after success?
        setText("username", username);
        setText("password", password);
        clickAndWait("submit");

        IndexPO po = new IndexPO(this);
        if(po.isOnPage()) {
            return po;
        }
        return null;
        // =) =)= )= )= )= )= )=) = ) =) =)
    }

}
