package dev.phasterinc.tripdat.config;

/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Name: HibernateConfig
 * Purpose: Configuration for the Hibernate ORM framework
 */
@Configuration
@EnableTransactionManagement
@PropertySource({"classpath:persistence-postgresql.properties"})
@ComponentScan({"dev.phasterinc.tripdat.model"})
public class HibernateConfig {
    @Autowired
    private Environment env;

    /**
     * Name: sessionFactory
     * Purpose: Creates a Hibernate SessionFactory.
     * Synopsis: This is the usual way to set up a shared Hibernate SessionFactory in a
     * Spring application context. This enables the SessionFactory to then be passed to
     * data access objects via dependency injection.
     * <p>
     *
     * @return LocalSessionFactoryBean to be passed to data access objects via dependency injection.
     */
    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(new String[]{"dev.phasterinc.tripdat.model"});
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    /**
     * Name: transactionManager
     * Purpose: To implement a single Hibernate SessionFactory
     * Synopsis: Binds a Hibernate Session from the specified factory to the thread.
     * <p>
     *
     * @return HibernateTransactionManager
     */
    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory);
        return transactionManager;
    }

    /**
     * Name: exceptionTranslation
     * Purpose: To translate native Hibernate resource exceptions to Spring's
     * DataAccessException hierarchy.
     * Synopsis: Bean post-processor that automatically applies persistence exception
     * translation to any bean marked with {@code Repository} annotation.
     * <p>
     *
     * @return PersistenceExceptionTranslationPostProcessor
     */
    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    /**
     * Name: dataSource
     * Purpose: Connects to the physical data source for the application.
     * Synopsis: A factory for connections to the physical data source (PostGreSQL) that
     * the DataSource object represents. Sets configuration properties for connecting to the
     * database.
     * <p>
     *
     * @return DataSource
     */
    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
        dataSource.setUrl(env.getProperty("jdbc.url"));
        dataSource.setUsername(env.getProperty("jdbc.user"));
        dataSource.setPassword(env.getProperty("jdbc.password"));

        return dataSource;
    }

    /**
     * Name: hibernateProperties
     * Purpose: Sets Hibernate Configuration properties.
     * <p>
     */
    Properties hibernateProperties() {
        return new Properties() {
            {
                setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
                setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
                setProperty("hibernate.globally_quoted_identifiers", "false");
                setProperty("hibernate.query.substitutions", "true 1, false 0, yes 'Y', no 'N");
                setProperty("hibernate.show_sql", "true");
            }
        };
    }


}
