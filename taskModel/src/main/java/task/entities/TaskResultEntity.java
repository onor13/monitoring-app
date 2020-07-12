package task.entities;

import constants.Formats;
import task.TaskResult;
import task.TaskResultType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity(name="TaskResult")
@Table(name = TaskResultEntity.TABLE_NAME)
@NamedQueries({
    @NamedQuery(name= TaskResultEntity.FIND_TASK_RESULT_BY_ID,
        query="select tr from TaskResult tr " +
            "join fetch tr.application a " +
            "where tr.id = :id")
})
public class TaskResultEntity extends AbstractEntity implements TaskResult {

  final public static String TABLE_NAME="taskResult";
  public static final String FIND_TASK_RESULT_BY_ID = "TaskResult.findById";

  @ManyToOne
  @JoinColumn(name = "APPLICATION_ID")
  private ApplicationEntity application;

  @Column(name = "NAME")
  @NotNull
  private String taskName;

  @Column(name = "GROUP_NAME")
  private String taskGroup;

  @Column(name="RESULT")
  @Enumerated(EnumType.STRING)
  @NotNull
  private TaskResultType taskResultType;

  @Column(name = "START_DATE_TIME", columnDefinition = "TIMESTAMP")
  @NotNull
  private LocalDateTime startTime;

  @Column(name="EXECUTION_DURATION")
  private Duration executionDuration;

  public String getApplicationName() {
    return application.getName();
  }

  @Override
  public LocalDateTime getApplicationStartTime() {
    return application.getStartTime();
  }

  public void setApplication( ApplicationEntity aApplication ) {
    application = aApplication;
  }


  public LocalDateTime getTaskStartTime() {
    return startTime;
  }

  public void setTaskStartTime( LocalDateTime aStartTime ) {
    startTime = aStartTime;
  }

  @Override public String getTaskName() {
    return taskName;
  }

  public void setTaskName( String aTaskName ) {
    taskName = aTaskName;
  }

  @Override public String getTaskGroup() {
    return taskGroup;
  }

  public void setTaskGroup( String aTaskGroup ) {
    taskGroup = aTaskGroup;
  }

  @Override public TaskResultType getTaskResultType() {
    return taskResultType;
  }

  public void setTaskResultType( TaskResultType aTaskResultType ) {
    taskResultType = aTaskResultType;
  }

  public Duration getTaskExecutionDuration() {
    return executionDuration;
  }

  public void setTaskExecutionDuration( Duration aExecutionDuration ) {
    executionDuration = aExecutionDuration;
  }

  @Override
  public String toString() {
    return String.format("TaskResult - id: %d, Application id: %d, Name: %s, Group: %s, Start DateTime: %s, Duration: %d",
        id,
        (application != null && application.getId() != null) ? application.getId() :  0,
        taskName,
        taskGroup,
        startTime == null ? "<unknown>" : Formats.FORMATTER.format( startTime ),
        executionDuration == null ? 0 : executionDuration.toSeconds() );
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    if (!super.equals(o))
      return false;
    TaskResultEntity tr = (TaskResultEntity) o;
    if ( ! Objects.equals( taskName, tr.taskName ) )
      return false;
    return startTime != null && startTime.equals(tr.startTime);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 13 * result + ( taskName == null ? 0 : taskName.hashCode() ) + ( (application == null || application.id == null) ? 0 : application.id.hashCode()) ;
    result = 13 * result + (startTime == null ? 0 : startTime.hashCode() );
    return result;
  }
}
