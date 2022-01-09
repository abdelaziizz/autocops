//package com.mdp.autocops.config;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.web.session.HttpSessionEventPublisher;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//@Configuration
//@RequiredArgsConstructor
//public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
//    private final LoginSuccessHandler loginSuccessHandler;
//    private final LoginFailureHandler loginFailureHandler;
//    private final CustomAuthenticationProvider customAuthenticationProvider;
//    private static final String[] ANONYMOUS_ENDPOINTS = {"/login"};
//
//    @Override
//    public void configure(WebSecurity web) {
//        web.ignoring().antMatchers("/ui-assets/**");
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .authenticationProvider(customAuthenticationProvider)
//                .eraseCredentials(false);
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        System.out.println("Initializing Security Configuration...");
//        http.authorizeRequests()
//                .antMatchers(ANONYMOUS_ENDPOINTS).anonymous()
//                .anyRequest().permitAll()
////                .and()
////                .headers(headers -> headers
////                        .addHeaderWriter(new StaticHeadersWriter("X-Content-Security-Policy", "script-src 'self'"))
////                        .addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)))
////                .exceptionHandling()
////                .authenticationEntryPoint(new AjaxAwareAuthenticationEntryPoint("/login"))
////                .accessDeniedPage("/login")
////                .and()
////                .formLogin()
////                .loginPage("/login")
////                .permitAll()
//                .and().logout()
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                .invalidateHttpSession(true)
//                .logoutSuccessUrl("/login")
//                .deleteCookies("JSESSIONID")
//                .and().csrf();
//    }
//
//
//    @Bean
//    public HttpSessionEventPublisher httpSessionEventPublisher() {
//        return new HttpSessionEventPublisher();
//    }
//
//
//}
