package task;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;

public interface TaskResult {

  String getApplicationId();

  String getTaskName();

  String getTaskGroup();

  TaskResultType getTaskResultType();

  LocalDateTime getStartTime();

  Duration getExecutionDuration();
}
