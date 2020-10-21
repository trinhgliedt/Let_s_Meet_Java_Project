package com.events.configurations;

//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.thymeleaf.spring5.SpringTemplateEngine;
//import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
//import org.thymeleaf.spring5.view.ThymeleafViewResolver;
//
//@Configuration
//@EnableWebMvc
//public class ThymeleafConfiguration {
// 
//    @Bean
//    public SpringTemplateEngine templateEngine() {
//        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
//        templateEngine.setTemplateResolver(thymeleafTemplateResolver());
//        return templateEngine;
//    }
// 
//    @Bean
//    public SpringResourceTemplateResolver thymeleafTemplateResolver() {
//        SpringResourceTemplateResolver templateResolver 
//          = new SpringResourceTemplateResolver();
//        templateResolver.setPrefix("/WEB-INF/");
//        return templateResolver;
//    }
//    
//    @Bean
//    public ThymeleafViewResolver thymeleafViewResolver() {
//    ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
//    viewResolver.setTemplateEngine(templateEngine());
//    return viewResolver;
//    }
//    
//    
//}