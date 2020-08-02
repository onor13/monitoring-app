package task.entities;

import converters.LocalDateTimeConverter;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import task.TaskResult;
import task.TaskResultType;

@Entity(name = "TaskResult")
@Table(name = TaskResultEntity.TABLE_NAME)
@NamedQueries({
    @NamedQuery(name = TaskResultEntity.FIND_TASK_RESULT_BY_ID,
        query = "select tr from TaskResult tr"
            + " join fetch tr.application a"
            + " where tr.id = :id"),
    @NamedQuery(name = TaskResultEntity.FIND_TASK_RESULT_BY_APP_NAME_TASK_NAME_TASK_START_TIME,
        query = "select tr from TaskResult tr"
            + " join fetch tr.application app"
            + " where app.name = :" + TaskResultEntity.PARAM_APP_NAME
            + " and tr.taskName = :" + TaskResultEntity.PARAM_TASK_NAME
            + " and tr.startTime = :" + TaskResultEntity.PARAM_TASK_START_TIME),
    @NamedQuery(name = TaskResultEntity.DELETE_TASK_RESULT_OLDER_THAN,
        query = "delete from TaskResult tr where"
            + " tr.startTime < :" + TaskResultEntity.PARAM_TASK_START_TIME
    )
})
@SuppressWarnings("PMD.BeanMembersShouldSerialize")
public class TaskResultEntity extends AbstractEntity implements TaskResult {

  public static final String FIND_TASK_RESULT_BY_ID =
      "TaskResult.findById";
  public static final String FIND_TASK_RESULT_BY_APP_NAME_TASK_NAME_TASK_START_TIME =
      "TaskResult.findByAppNameTaskNameTaskStartTime";

  public static final String DELETE_TASK_RESULT_OLDER_THAN = "TaskResult.deleteTaskResultOlderThan";
  public static final String TABLE_NAME = "TaskResult";
  public static final String PARAM_APP_NAME = "appName";
  public static final String PARAM_TASK_NAME = "taskName";
  public static final String PARAM_TASK_START_TIME = "taskStartTime";
  private static final long serialVersionUID = -4760477593367753944L;

  @ManyToOne
  @JoinColumn(name = "APPLICATION_NAME")
  private ApplicationEntity application;

  @Column(name = "NAME")
  @NotNull
  private String taskName;

  @Column(name = "GROUP_NAME")
  private String taskGroup;

  @Column(name = "RESULT")
  @Enumerated(EnumType.STRING)
  @NotNull
  private TaskResultType taskResultType;

  @Column(name = "START_DATE_TIME")
  @NotNull
  private LocalDateTime startTime;

  @Column(name = "EXECUTION_DURATION")
  private Duration executionDuration;

  @Override
  public String getApplicationName() {
    return application.getName();
  }

  @Override
  public LocalDateTime getApplicationStartTime() {
    return application.getStartTime();
  }

  public void setApplication(ApplicationEntity application) {
    this.application = application;
  }

  @Override
  public LocalDateTime getTaskStartTime() {
    return startTime;
  }

  public void setTaskStartTime(LocalDateTime taskStartTime) {
    startTime = taskStartTime;
  }

  @Override
  public String getTaskName() {
    return taskName;
  }

  public void setTaskName(String taskName) {
    this.taskName = taskName;
  }

  @Override
  public String getTaskGroup() {
    return taskGroup;
  }

  public void setTaskGroup(String taskGroup) {
    this.taskGroup = taskGroup;
  }

  @Override
  public TaskResultType getTaskResultType() {
    return taskResultType;
  }

  public void setTaskResultType(TaskResultType taskResultType) {
    this.taskResultType = taskResultType;
  }

  @Override
  public Duration getTaskExecutionDuration() {
    return executionDuration;
  }

  public void setTaskExecutionDuration(Duration taskExecutionDuration) {
    executionDuration = taskExecutionDuration;
  }

  @Override
  public String toString() {
    return String.format(
        "TaskResult - id: %d, Application name: %s, Name: %s, Group: %s, Start DateTime: %s, Duration: %d",
        id,
        (application != null && application.getName() != null) ? application.getName() : 0,
        taskName,
        taskGroup,
        startTime == null ? "<unknown>" : new LocalDateTimeConverter().format(startTime),
        executionDuration == null ? 0 : executionDuration.toSeconds());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    TaskResultEntity tr = (TaskResultEntity) o;
    if (!Objects.equals(taskName, tr.taskName)) {
      return false;
    }
    return startTime != null && startTime.equals(tr.startTime);
  }

  @Override
  public int hashCode() {
    int result = (taskName == null ? 0 : taskName.hashCode())
        + ((application == null || application.getName() == null) ? 0 : application.hashCode());
    result = 13 * result + (startTime == null ? 0 : startTime.hashCode());
    return result;
  }
}
