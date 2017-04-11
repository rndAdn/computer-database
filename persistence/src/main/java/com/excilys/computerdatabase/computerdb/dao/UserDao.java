package com.excilys.computerdatabase.computerdb.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.computerdb.HiberbateUtils.HibernateUtil;
import com.excilys.computerdatabase.computerdb.model.entities.User;

@Repository
public class UserDao {


    @SuppressWarnings("unchecked")
    public User findByUserName(String username) {

        List<User> users = new ArrayList<User>();

        users = HibernateUtil.getSessionFactory().openSession()
            .createQuery("from User where username=?")
            .setParameter(0, username)
            .list();

        if (users.size() > 0) {
            return users.get(0);
        } else {
            return null;
        }

    }

}