package fake;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import task.Application;
import task.TaskResult;
import task.TaskResultType;

@SuppressWarnings("PMD")
public class FakeTaskResult implements TaskResult {
  protected String         taskName;
  protected String         taskGroup;
  protected TaskResultType taskResultType;
  protected LocalDateTime taskStartTime;
  protected Duration      taskExecutionDuration;
  protected Application   app;

  public FakeTaskResult(Application application, String taskName, String taskGroup, TaskResultType taskResultType,
                        LocalDateTime startExecutionTime, Duration executionDuration) {
    Objects.requireNonNull(application);
    Objects.requireNonNull(taskResultType);
    Objects.requireNonNull(startExecutionTime);
    this.app = application;
    this.taskName = taskName;
    this.taskGroup = taskGroup;
    this.taskResultType = taskResultType;
    this.taskStartTime = startExecutionTime;
    this.taskExecutionDuration = executionDuration;
  }

  @Override
  public String getApplicationName() {
    return app.getName();
  }

  @Override
  public LocalDateTime getApplicationStartTime() {
    return app.getStartTime();
  }

  public String getTaskName() {
    return taskName;
  }

  public String getTaskGroup() {
    return taskGroup;
  }

  public TaskResultType getTaskResultType() {
    return taskResultType;
  }

  public LocalDateTime getTaskStartTime() {
    return taskStartTime;
  }

  public Duration getTaskExecutionDuration() {
    return taskExecutionDuration;
  }

  @Override public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (other == null || getClass() != other.getClass()) {
      return false;
    }

    FakeTaskResult otherTaskResult = (FakeTaskResult) other;
    return this.equals(otherTaskResult);
  }

}
