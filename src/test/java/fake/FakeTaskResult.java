package fake;

import task.TaskResult;
import task.TaskResultType;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

public class FakeTaskResult
    implements TaskResult {
  private Long           seqNumber;
  private String         applicationId;
  private String         taskName;
  private String         taskGroup;
  private TaskResultType taskResultType;
  private Instant        startTime;
  private Duration       executionDuration;

  public FakeTaskResult( Long seqNumber, String applicationId, String taskName, String taskGroup, TaskResultType taskResultType, Instant startExecutionTime, Duration executionDuration ){
    Objects.requireNonNull( applicationId );
    Objects.requireNonNull( taskResultType );
    Objects.requireNonNull( startExecutionTime );
    Objects.requireNonNull( executionDuration );

    this.seqNumber = seqNumber;
    this.applicationId = applicationId;
    this.taskName = taskName;
    this.taskGroup = taskGroup;
    this.taskResultType = taskResultType;
    this.startTime = startExecutionTime;
    this.executionDuration = executionDuration;
  }

  public String getApplicationId() {
    return applicationId;
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

  public Instant getStartTime() {
    return startTime;
  }

  public Duration getExecutionDuration() {
    return executionDuration;
  }

  @Override public boolean equals( Object other ) {
    if ( this == other ) return true;
    if ( other == null || getClass() != other.getClass() ) return false;

    FakeTaskResult otherTaskResult = (FakeTaskResult) other;

    if ( !applicationId.equals( otherTaskResult.applicationId ) ) return false;
    return seqNumber == otherTaskResult.seqNumber ;
  }

  @Override public int hashCode() {
    int l_result = applicationId.hashCode();
    l_result = 13 * l_result + seqNumber.hashCode();
    return l_result;
  }
}
