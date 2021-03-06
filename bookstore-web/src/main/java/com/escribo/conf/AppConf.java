package com.escribo.conf;

import java.beans.PropertyVetoException;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * @author Siva
 *
 */
@Configuration
@EnableTransactionManagement     
@ComponentScan(basePackages = { "com.escribo.*" })
@PropertySource(
		{ 
			"classpath:i18n/messages_pt.properties",
			"classpath:application.properties", 
			"classpath:custon.properties", 
			"classpath:general.properties",
			"classpath:database.properties",
			"classpath:hibernate.properties",
			"classpath:c3p0.properties",
			"classpath:bookstore.db"
		})
@Import({ SecurityConfiguration.class })
public class AppConf {

	
	
	public AppConf() {
		System.out.println(AppConf.class.toGenericString());
	}
	
	
	@Autowired
	private Environment environment;


    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(new String[] 
        		{ "com.escribo.common.security.model.impl", 
        		  "com.escribo.common.foundation.model.impl"
        		});
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
     }
     
    @Bean
    public DataSource dataSource() {
    	System.out.println("dataSource2");
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty("database.driverClassName"));
        dataSource.setUrl(environment.getRequiredProperty("database.url"));
        dataSource.setUsername(environment.getRequiredProperty("database.username"));
        dataSource.setPassword(environment.getRequiredProperty("database.password"));
        return dataSource;
    }
    
//    @Bean
//    public DataSource dataSource() {
//    	System.out.println("dataSource");
//    	ComboPooledDataSource dataSource = new ComboPooledDataSource();
//        try {
//			dataSource.setDriverClass(environment.getRequiredProperty("database.driverClassName"));
//		} catch (IllegalStateException e) {
//			e.printStackTrace();
//		} catch (PropertyVetoException e) {
//			e.printStackTrace();
//		}
//        dataSource.setJdbcUrl(environment.getRequiredProperty("database.url"));
//        dataSource.setUser(environment.getRequiredProperty("database.username"));
//        dataSource.setPassword(environment.getRequiredProperty("database.password"));
//        
//  /*      dataSource.setIdleConnectionTestPeriod(Integer.parseInt(environment.getRequiredProperty("c3p0.idle_test_period")));
//        dataSource.setMinPoolSize(Integer.parseInt(environment.getRequiredProperty("c3p0.min_size")));
//        dataSource.setMaxPoolSize(Integer.parseInt(environment.getRequiredProperty("c3p0.max_size")));
//        dataSource.setInitialPoolSize(Integer.parseInt(environment.getRequiredProperty("c3p0.initialPoolSize")));
//        dataSource.setTestConnectionOnCheckout(Boolean.getBoolean(environment.getRequiredProperty("c3p0.testConnectionsOnCheckout")));
//        dataSource.setAcquireRetryDelay(Integer.parseInt(environment.getRequiredProperty("c3p0.acquireRetryDelay")));
//        dataSource.setAcquireIncrement(Integer.parseInt(environment.getRequiredProperty("c3p0.acquire_increment")));
//        dataSource.setMaxStatements(Integer.parseInt(environment.getRequiredProperty("c3p0.max_statements")));
//        dataSource.setMaxStatementsPerConnection(Integer.parseInt(environment.getRequiredProperty("c3p0.max_statements_per_connection"))); */
//    	return dataSource;
//    }
     
    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
        properties.put("hibernate.SQL", environment.getRequiredProperty("hibernate.SQL"));
        properties.put("hibernate.type", environment.getRequiredProperty("hibernate.type"));
        properties.put("hibernate.jdbc", environment.getRequiredProperty("hibernate.jdbc"));
        properties.put("hibernate.engine.query", environment.getRequiredProperty("hibernate.engine.query"));
        properties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
        properties.put("hibernate.enable_lazy_load_no_trans", environment.getRequiredProperty("hibernate.enable_lazy_load_no_trans"));
        properties.put("hibernate.hbm2ddl.auto", environment.getRequiredProperty("hibernate.hbm2ddl.auto"));
        return properties;        
    }
     
    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory s) {
       HibernateTransactionManager txManager = new HibernateTransactionManager();
       txManager.setSessionFactory(s);
       return txManager;
    }

}
