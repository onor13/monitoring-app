import task.Application;
import task.JsonApplication;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ApplicationGenerator {

  AtomicInteger counter = new AtomicInteger();

  public Application generateInstance(){
    Application app = new JsonApplication(
        "doctor" + counter.incrementAndGet(),
        LocalDateTime.now().minus( 14, ChronoUnit.MINUTES ) );
    return app;
  }
}
