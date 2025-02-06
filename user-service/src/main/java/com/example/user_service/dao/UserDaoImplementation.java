package com.example.user_service.dao;

import com.example.user_service.model.Job;
import com.example.user_service.model.User;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImplementation implements UserDao {
    @Autowired
    private EntityManager entityManager;


    @Override
    public User getUserById(Long id) {
        Session session = entityManager.unwrap(Session.class);
        return session.get(User.class, id);
    }

    @Override
    public List<User> getUsersByIds(List<Long> userIds) {
        Session session = entityManager.unwrap(Session.class);
        return session.createQuery("from User where id in (:ids)", User.class)
                .setParameter("ids", userIds)
                .getResultList();
    }

    @Override
    public User createUser(User user) {
        Session session = entityManager.unwrap(Session.class);
        session.saveOrUpdate(user);
        return user;
    }

    @Override
    public User updateUser(Long id, User user) {
        Session session = entityManager.unwrap(Session.class);
        User userToUpdate = session.get(User.class, id);
        if (userToUpdate == null) {
            return null;
        }
        else {
            userToUpdate.setName(user.getName());
            userToUpdate.setEducation(user.getEducation());
            userToUpdate.setEmail(user.getEmail());
            userToUpdate.setExperience(user.getExperience());
            userToUpdate.setCurrentCompanyId(user.getCurrentCompanyId());

            // userToUpdate.setJobIds(user.getJobIds());
            session.saveOrUpdate(userToUpdate);
            return userToUpdate;
        }
    }

    @Override
    public boolean deleteUser(Long id) {
        Session session = entityManager.unwrap(Session.class);
        User user = session.get(User.class, id);
        if (user == null) {
            return false;
        }

        session.delete(user);
        return true;
    }

}
