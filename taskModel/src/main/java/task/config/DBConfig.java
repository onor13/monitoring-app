package task.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import task.dao.ApplicationDao;
import task.dao.ApplicationDaoImpl;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:db/jdbc.properties")
public class DBConfig {

  private static Logger logger = Logger.getLogger(DBConfig.class.getSimpleName());

  @Value("${driverClassName}")
  private String driverClassName;
  @Value("${url}")
  private String url;
  @Value("${username}")
  private String username;
  @Value("${password}")
  private String password;

  @Bean
  public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
    return new PropertySourcesPlaceholderConfigurer();
  }

  @Bean(destroyMethod = "close")
  public DataSource dataSource() {
    try {
      BasicDataSource dataSource = new BasicDataSource();
      dataSource.setDriverClassName(driverClassName);
      dataSource.setUrl(url);
      dataSource.setUsername(username);
      dataSource.setPassword(password);
      return dataSource;
    } catch (Exception e) {
      logger.log( Level.SEVERE, ("DBCP DataSource bean cannot be created! " + e.toString() ) );
      return null;
    }
  }

  private Properties hibernateProperties() {
    Properties hibernateProp = new Properties();
    hibernateProp.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
    hibernateProp.put("hibernate.hbm2ddl.auto", "create-drop");
    hibernateProp.put("hibernate.format_sql", true);
    hibernateProp.put("hibernate.use_sql_comments", true);
    hibernateProp.put("hibernate.show_sql", true);
    hibernateProp.put("hibernate.max_fetch_depth", 3);
    hibernateProp.put("hibernate.jdbc.batch_size", 10);
    hibernateProp.put("hibernate.jdbc.fetch_size", 50);
    return hibernateProp;
  }

  @Bean
  public SessionFactory sessionFactory() {
    return new LocalSessionFactoryBuilder(dataSource())
        .scanPackages("task.entities")
        .addProperties(hibernateProperties())
        .buildSessionFactory();
  }

  @Bean public PlatformTransactionManager transactionManager() throws IOException {
    return new HibernateTransactionManager(sessionFactory());
  }

  @Bean(destroyMethod = "destroy")
  public CleanUp cleanUp() {
    return new CleanUp(new JdbcTemplate(dataSource()));
  }

  @Bean
  public ApplicationDao applicationDao(){
    ApplicationDaoImpl appDao = new ApplicationDaoImpl();
    appDao.setSessionFactory( sessionFactory() );
    return appDao;
  }

  @Bean DBInitializer dbInitializer(){
    DBInitializer dbInit = new DBInitializer();
    return dbInit;
  }
}
