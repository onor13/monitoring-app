package fake;

import task.Application;
import task.TaskResult;
import task.TaskResultType;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class FakeTaskResult
    implements TaskResult {
  private String         taskName;
  private String         taskGroup;
  private TaskResultType taskResultType;
  private LocalDateTime taskStartTime;
  private Duration      taskExecutionDuration;
  private Application   app;

  public FakeTaskResult( Application application, String taskName, String taskGroup, TaskResultType taskResultType, LocalDateTime startExecutionTime, Duration executionDuration ){
    Objects.requireNonNull( application );
    Objects.requireNonNull( taskResultType );
    Objects.requireNonNull( startExecutionTime );
    Objects.requireNonNull( executionDuration );
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

  @Override public LocalDateTime getApplicationStartTime() {
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

  @Override public boolean equals( Object other ) {
    if ( this == other ) return true;
    if ( other == null || getClass() != other.getClass() ) return false;

    FakeTaskResult otherTaskResult = (FakeTaskResult) other;
    return this.equals( otherTaskResult ) ;
  }

}
