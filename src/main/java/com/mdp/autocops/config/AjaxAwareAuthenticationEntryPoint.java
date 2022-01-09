//package com.mdp.autocops.config;
//
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//public class AjaxAwareAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {
//
//    public AjaxAwareAuthenticationEntryPoint(String loginFormUrl) {
//        super(loginFormUrl);
//    }
//    @Override
//    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
//        String ajaxHeader = request.getHeader("X-Requested-With");
//        if ("XMLHttpRequest".equals(ajaxHeader)) {
//            System.out.println("response error");
//            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Ajax Request Denied (Session Expired)");
//            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//            response.getWriter().write("Ajax Request Denied (Session Expired)");
//            response.flushBuffer();
//        } else {
//            super.commence(request, response, authException);
//        }
//    }
//
//
//}
