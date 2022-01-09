//package com.mdp.autocops.config;
//import javax.servlet.http.HttpSessionEvent;
//import javax.servlet.http.HttpSessionListener;
//
//public class SessionListener implements HttpSessionListener {
//
//    @Override
//    public void sessionCreated(HttpSessionEvent se) {
//        System.out.println("=== Session Created ===");
//        se.getSession().setMaxInactiveInterval(30*60);
//    }
//
//    @Override
//    public void sessionDestroyed(HttpSessionEvent se) {
//        System.out.println("=== Session is Destroyed! ===");
//    }
//
//}
