//package com.mdp.autocops.config;
//import com.mdp.autocops.exception.UnAuthorizedException;
//import lombok.extern.log4j.Log4j2;
//import org.apache.http.HttpStatus;
//import org.apache.http.client.HttpResponseException;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//
//@Component
//@Log4j2
//public class CustomAuthenticationProvider implements AuthenticationProvider {
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        String username = authentication.getName();
//        String password = authentication.getCredentials().toString();
//        try {
////            AuthorizationResponse authorize = AuthzClient.create()
////                    .authorization(username, password)
////                    .authorize(new AuthorizationRequest());
////            log.info("User Authorized successfully! expires in: {}",authorize.getExpiresIn());
//            System.out.println("Try Block");
//        } catch (RuntimeException e) {
//            if (e.getCause() != null) {
//                Throwable cause = e.getCause();
//                if (cause instanceof HttpResponseException &&
//                        ((HttpResponseException) cause).getStatusCode() == HttpStatus.SC_UNAUTHORIZED) {
//                    throw new UnAuthorizedException("UNAUTHORIZED");
//                } else {
//                    e.getMessage();
//                    throw e;
//                }
//            }
//            throw e;
//        }
//        return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return authentication.equals(UsernamePasswordAuthenticationToken.class);
//    }
//
//}
