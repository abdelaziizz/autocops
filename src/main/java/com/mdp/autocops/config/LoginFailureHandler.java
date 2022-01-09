//package com.mdp.autocops.config;
//
//import com.mdp.autocops.exception.UnAuthorizedException;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//
//@Log4j2
//@Component
//public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {
//    @Override
//    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
//        log.info("LoginFailureHandler :: onAuthenticationFailure :: start");
//        HttpSession session = request.getSession();
//        if (exception.getClass().equals(UnAuthorizedException.class)) {
//            log.info("there is exception of type UnAuthorizedException.class!");
//            session.setAttribute("auth_error", "auth");
//        }
//
//        log.info("LoginFailureHandler :: onAuthenticationFailure :: end");
//        setDefaultFailureUrl("/login");
//        super.onAuthenticationFailure(request, response, exception);
//    }
//}
