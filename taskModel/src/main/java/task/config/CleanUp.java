package task.config;

import com.google.common.flogger.FluentLogger;
import org.springframework.jdbc.core.JdbcTemplate;

public class CleanUp {
  private static final FluentLogger logger = FluentLogger.forEnclosingClass();

  private JdbcTemplate jdbcTemplate;

  public CleanUp(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  private void destroy() {
    logger.atInfo().log(" ... Deleting database files.");
    jdbcTemplate.execute("DROP ALL OBJECTS DELETE FILES;");
  }
}
