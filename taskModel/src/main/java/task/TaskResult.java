package task;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;

public interface TaskResult {

  String getApplicationName();
  LocalDateTime getApplicationStartTime();
  String getTaskName();
  String getTaskGroup();
  TaskResultType getTaskResultType();
  LocalDateTime getTaskStartTime();
  Duration getTaskExecutionDuration();
}
