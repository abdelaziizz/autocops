//package com.mdp.autocops.config;
//import org.springframework.context.MessageSource;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.support.ReloadableResourceBundleMessageSource;
//import org.springframework.scheduling.annotation.EnableAsync;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.web.servlet.LocaleResolver;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import org.springframework.web.servlet.i18n.SessionLocaleResolver;
//import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
//import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;
//import org.thymeleaf.spring5.SpringTemplateEngine;
//import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
//import org.thymeleaf.templatemode.TemplateMode;
//import org.thymeleaf.templateresolver.ITemplateResolver;
//
//import javax.annotation.PostConstruct;
//import java.util.Locale;
//import java.util.TimeZone;
//
//@Configuration
//@EnableScheduling
//@EnableAsync
//public class WebConfig implements WebMvcConfigurer {
//
//    private static final String VIEWS = "classpath:/templates/";
//    private static final String CHARACTER_ENCODING = "UTF-8";
//    private static final String MESSAGE_SOURCE = "classpath:/messages";
//
//    @Bean(name = "messageSource")
//    public MessageSource messageSource() {
//        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
//        messageSource.setBasename(MESSAGE_SOURCE);
////        messageSource.setCacheSeconds(5); // jus for testing purposes and should remove it in production environment
//        messageSource.setDefaultEncoding(CHARACTER_ENCODING);
//        return messageSource;
//    }
//    @PostConstruct
//    void started() {
//        System.out.println("post construct started...");
//        TimeZone.setDefault(TimeZone.getTimeZone("Africa/Cairo"));
//    }
//    @Bean
//    public SpringTemplateEngine templateEngine() {
//        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
//        templateEngine.setTemplateResolver(templateResolver());
//        templateEngine.addDialect(new SpringSecurityDialect());
//        templateEngine.addDialect(new Java8TimeDialect());
//        return templateEngine;
//    }
//
//    @Bean
//    public LocaleResolver localeResolver(){
//        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
//        localeResolver.setDefaultLocale(new Locale("ar", "EG"));
//        return localeResolver;
//    }
//
//    @Bean
//    public ITemplateResolver templateResolver() {
//        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
//        resolver.setPrefix(VIEWS);
//        resolver.setSuffix(".html");
//        resolver.setTemplateMode(TemplateMode.HTML);
//        resolver.setCharacterEncoding(CHARACTER_ENCODING);
//        resolver.setCacheable(false);
//        return resolver;
//    }
//
//
//
//}
