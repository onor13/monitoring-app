package fake;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import task.Application;
import task.TaskResult;

@SuppressWarnings("PMD")
public class FakeApplication implements Application {

  private LocalDateTime startTime = LocalDateTime.now();
  private String appName;

  public FakeApplication(String appName) {
    this.appName = appName;
  }

  @Override
  public String getName() {
    return appName;
  }

  @Override
  public LocalDateTime getStartTime() {
    return startTime;
  }

  @Override
  public Set<TaskResult> getTasksResults() {
    return new HashSet<>();
  }
}
