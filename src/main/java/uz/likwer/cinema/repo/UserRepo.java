package com.example.chat.repo;

import com.example.chat.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.Cookie;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.example.chat.config.DataLoader.emf;

public class UserRepo {

    public static List<User> findAll() {
        return emf.createEntityManager().createQuery("from User", User.class).getResultList();
    }
    public static List<User> findAllExceptCurrentUser(User user) {
        return emf.createEntityManager().createQuery("select u from User u where id!=:id", User.class).setParameter("id",user.getId()).getResultList();
    }

    public static User findById(UUID uuid) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.find(User.class, uuid);
    }

    public static Optional<User> findByUserName(String userName) {
        return emf.createEntityManager().createQuery("select t from User t where username=:name", User.class).setParameter("name", userName).getResultList().stream().findFirst();
    }

    public static void save(User user) {
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public static Cookie getCookieWithUser(User user) {
        Cookie cookie = new Cookie("currentUser", user.getId().toString());
        cookie.setMaxAge(60 * 60 * 24 * 30);
        cookie.setPath("/");
        cookie.setSecure(false);
        return cookie;
    }
}
