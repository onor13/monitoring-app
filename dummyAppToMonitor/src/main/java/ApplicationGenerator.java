import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.atomic.AtomicInteger;
import task.Application;
import task.JsonApplication;

public class ApplicationGenerator {

  static final AtomicInteger counter = new AtomicInteger();

  public Application generateInstance() {
    return createApplication("doctor" + counter.incrementAndGet());
  }

  public Application createApplication(String applicationName) {
    Application app = new JsonApplication(
        applicationName,
        LocalDateTime.now().minus(14, ChronoUnit.MINUTES));
    return app;
  }
}
