package dev.phasterinc.tripdat.config;
/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

import dev.phasterinc.tripdat.util.ViewNames;
import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

/**
 * Name: WebConfig
 * Purpose: Configurations for the Web Application
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    // == bean methods ==

    /**
     * Name: localeResolver
     * Purpose: Resolve the locale
     * Synopsis: Allows for various locale resolution strategies.
     * <p>
     *
     * @return SessionLocaleResolver uses a locale attribute in the User's session in case
     * of custom setting, with a fallback to the specified default locale (english) or the
     * request's accept-header locale.
     */
    @Bean
    public LocaleResolver localeResolver() {
        return new SessionLocaleResolver();
    }

    /**
     * Name: addViewController
     * Purpose: Configure simple automated controllers for pre-configured with the
     * response status code and/or view to render.
     * Synopsis: Sets the default path of "/" to return the View of HOME.
     * <p>
     *
     * @param registry ViewControllerRegistry Configures simple automated controllers where
     *                 there is no need to create a custom controller for it.
     *                 Adds the default home page to the registry.
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName(ViewNames.HOME);
    }

    /**
     * Name: messageSource
     * Purpose: Strategy Interface for resolving messages.
     * Synopsis: Interface for resolving messages with the support for the parameterization
     *      and internationalization of such messages.
     * <p>
     *
     * @return MessageSource where the messages are coming from.
     */
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource
                = new ReloadableResourceBundleMessageSource();

        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;

    }

    /**
     * Name: getValidator
     * Purpose: Setup for {@code javax.validation}
     * Synopsis: Bootstraps a {@code javax.validation.ValidationFactory}
     * and exposes it through the Spring {@code Validator} interface.
     * <p>
     *
     * @return LocalValidatorFactoryBean
     */
    @Override
    @Bean
    public LocalValidatorFactoryBean getValidator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setValidationMessageSource(messageSource());
        return validator;
    }

    /**
     * Name: addResourceHandler
     * Purpose: Add handlers to serve static resources such as images, js, css, jars.
     * <p>
     *
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");

    }

    /**
     * Name: layoutDialect
     * Purpose: Enable Thymeleaf dialect for building layouts and reusable templates.
     * Synopsis: A Thymeleaf dialect that improves code reuse.
     * <p>
     *
     * @return LayoutDialect
     */
    @Bean
    public LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }

    /**
     * Name: springSecurityDialect
     * Purpose: Enable Thymeleaf dialect for the use of Spring Security methods in
     * the front end.
     * <p>
     *
     * @return SpringSecurityDialect that enables the use of Spring Security methods in
     * Thymeleaf pages.
     */
    @Bean
    public SpringSecurityDialect springSecurityDialect() {
        return new SpringSecurityDialect();
    }

    /**
     * Name: Java8TimeDialect
     * Purpose: Thymeleaf dialect to format and create Java 8 time objects
     * <p>
     *
     * @return Java8TimeDialect dialect for formatting and creating Java 8 time objects
     * in templates.
     */
    @Bean
    public Java8TimeDialect java8TimeDialect() {
        return new Java8TimeDialect();
    }

    /**
     * Name: modelMapper
     * Purpose: Performs object mapping
     * <p>
     *
     * @return ModelMapper Enables the configuration of one mapping to another & other
     * object mapping.
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }




}
