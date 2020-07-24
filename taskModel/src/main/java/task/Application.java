package task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;

public interface Application {
  String getName();

  LocalDateTime getStartTime();

  Set<TaskResult> getTasksResults();
}
