package dev.phasterinc.tripdat.config;

/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

import dev.phasterinc.tripdat.LoggingAccessDeniedHandler;
import dev.phasterinc.tripdat.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Name: SecurityConfig
 * Purpose: Sets configuration settings for Security features of the application by
 * overriding methods from the base class WebSecurityConfigurerAdapter.
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private LoggingAccessDeniedHandler accessDeniedHandler;

    @Autowired
    private CustomUserDetailsService tripdatUserService;

    /**
     * Name: configureGlobalSecurity
     * Purpose: Sets the authentication provider.
     * <p>
     *
     * @param auth AuthenticationManagerBuilder allows for in memory authentication,
     *             and JDBC authentication.
     * @throws Exception thrown if there is an issue setting the authenticationProvider.
     */
    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    /**
     * Name: configure
     * Purpose: Configure the HttpSecurity
     * Synopsis: Denotes what requests are authorized, what permissions are needed for
     * requesting certain pages, what do for login, logout, and what to display after
     * successful logout.
     * <p>
     *
     * @param http the HttpSecurity to modify
     * @throws Exception if an error occurs
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(
                        "/registration",
                        "/",
                        "/js/**",
                        "/css/**",
                        "/images/**",
                        "/webjars/**").permitAll()
                .antMatchers("/user/**",
                        "/trip/**").hasRole("USER")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler);
    }

    /**
     * Name: authenticationProvider
     * Purpose: To retrieve User details from a UserDetails Service.
     * Synopsis: Creates new DaoAuthenticationProvider, sets the UserDetails Service to
     * be called upon & sets the password encoder to be used.
     * <p>
     *
     * @return DaoAuthenticationProvider
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(tripdatUserService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }


    /**
     * Name: passwordEncoder
     * Purpose: To encode a user's password before storing it in the database.
     * Synopsis: Uses the BCrypt strong hashing function.
     * <p>
     *
     * @return BCryptPasswordEncoder
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
