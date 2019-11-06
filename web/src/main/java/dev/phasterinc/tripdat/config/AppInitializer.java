package dev.phasterinc.tripdat.config;

/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Name: AppInitializer
 * Purpose: To register a {@code DispatcherServlet} and use Java-based Spring configuration.
 */
public class AppInitializer extends
        AbstractAnnotationConfigDispatcherServletInitializer {

    /**
     * Name: getRootConfigClass
     * Purpose: To retrieve the "root" application context configuration.
     * <p>
     *
     * @return the configuration of the root application context, or {@code null}
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{WebConfig.class};
    }

    /**
     * Name: getServletConfigClasses
     * Purpose: To retrieve the configuration of the Servlet application context
     * <p>
     *
     * @return the configuration for the Servlet application context, or {@code null} if
     * all configuration is specified through root config classes.
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebConfig.class};
    }

    /**
     * Name: getServletMappings
     * Purpose: Specify the servlet mapping(s) for the {@code DispatcherServlet}
     * <p>
     *
     * @return String, the mappings for the Servlet.
     */
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
