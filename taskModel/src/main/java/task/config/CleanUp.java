package task.config;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.logging.Logger;

public class CleanUp {
  private static Logger logger = Logger.getLogger(CleanUp.class.getSimpleName());

  private JdbcTemplate jdbcTemplate;

  public CleanUp(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  private void destroy() {
    logger.info(" ... Deleting database files.");
    jdbcTemplate.execute("DROP ALL OBJECTS DELETE FILES;");
  }
}
