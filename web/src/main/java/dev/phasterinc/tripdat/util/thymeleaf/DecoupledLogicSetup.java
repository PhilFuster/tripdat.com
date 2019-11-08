package dev.phasterinc.tripdat.util.thymeleaf;
/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;

import javax.annotation.PostConstruct;

/**
 * Name: DecoupledLogicSetup
 * Purpose: Sets up functionality for decoupled Thymeleaf logic from an html page.
 */
@Slf4j
@Component
public class DecoupledLogicSetup {
    // == fields ==
    private final SpringResourceTemplateResolver templateResolver;

    // == constructors ==

    /**
     * Name: DecoupledLogicSetup
     * Purpose: Constructor for injecting dependencies pertinent to Decoupled template
     * logic setup
     * <p>
     *
     * @param templateResolver SpringResourceTemplateResolver, resolves templates using
     *                         Spring's Resource Resolution mechanism.
     */
    public DecoupledLogicSetup(SpringResourceTemplateResolver templateResolver) {
        this.templateResolver = templateResolver;
    }

    // == init ==

    /**
     * Name: init
     * Purpose: Initialize Decoupled template logic for the applicatoin
     * Synopsis: Uses the templateResolver dependency to set the DecoupleLogic setting.
     * <p>
     */
    @PostConstruct
    public void init() {
        templateResolver.setUseDecoupledLogic(true);
        log.info("Decoupled template logic enabled");
    }
}
