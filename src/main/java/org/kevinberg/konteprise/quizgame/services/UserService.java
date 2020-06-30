package org.kevinberg.konteprise.quizgame.services;


import org.kevinberg.konteprise.quizgame.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Collections;

//use transcational when committing something to DB
//and when using entitymanager @autowired
@Transactional
@Service
public class UserService {

    //services dont define a constructor

    @Autowired
    private EntityManager em;

    @Autowired
    private PasswordEncoder pwEncoder;


    //parameters not in camelcase (lowerCamelCase)
    public boolean createUserWithPassword(String username, String password) {

        String hashedPw = pwEncoder.encode(password);

        // !=null , same as not unique aka 1
        if(em.find(User.class, username) != null) {
            return false;
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRoles(Collections.singleton("USER"));
        user.setEnabled(true);

        em.persist(user);

        return true;


    }


}
