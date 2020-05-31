package task;

import java.time.Duration;
import java.time.Instant;

public interface ITaskResult {

  String getApplicationId();

  String getTaskName();

  String getTaskGroup();

  TaskResultType getTaskResultType();

  Instant getStartTime();

  Duration getExecutionDuration();
}
