package com.example.chat.config;

import jakarta.persistence.Persistence;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import static com.example.chat.config.DataLoader.em;
import static com.example.chat.config.DataLoader.emf;

@WebListener
public class MyListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        emf = Persistence.createEntityManagerFactory("default");
        em = emf.createEntityManager();
        ServletContextListener.super.contextInitialized(sce);
    }
}
