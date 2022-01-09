//package com.mdp.autocops.config;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Component
//@Log4j2
//public class LoginSuccessHandler implements AuthenticationSuccessHandler {
//
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        log.info("LoginSuccessHandler :: onAuthenticationSuccess :: start");
//        response.sendRedirect(request.getContextPath() + "/dashboard");
//        log.info("LoginSuccessHandler :: onAuthenticationSuccess :: end");
//    }
//}
