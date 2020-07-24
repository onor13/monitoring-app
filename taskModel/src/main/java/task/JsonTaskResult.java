package task;

import java.time.Duration;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import serializer.CustomDurationDeserializer;
import serializer.CustomDurationSerializer;
import serializer.CustomLocalDateTimeDeserializer;
import serializer.CustomLocalDateTimeSerializer;

@JsonIdentityInfo(
    generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id",
    scope = JsonTaskResult.class)
public class JsonTaskResult implements TaskResult {

  private String applicationName;
  private LocalDateTime applicationStartTime;
  private String taskName;
  private String taskGroup;
  private TaskResultType taskResultType;
  private LocalDateTime taskStartTime;
  private Duration taskExecutionDuration;

  public JsonTaskResult() {
  }

  public JsonTaskResult(Application app) {
    applicationName = app.getName();
    applicationStartTime = app.getStartTime();
  }

  @Override
  public String getApplicationName() {
    return applicationName;
  }

  @Override
  @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
  @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
  public LocalDateTime getApplicationStartTime() {
    return applicationStartTime;
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
  @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
  @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
  public LocalDateTime getTaskStartTime() {
    return taskStartTime;
  }

  public void setTaskStartTime(LocalDateTime startTime) {
    this.taskStartTime = startTime;
  }


  @Override
  @JsonSerialize(using = CustomDurationSerializer.class)
  @JsonDeserialize(using = CustomDurationDeserializer.class)
  public Duration getTaskExecutionDuration() {
    return taskExecutionDuration;
  }

  public void setTaskExecutionDuration(Duration aExecutionDuration) {
    taskExecutionDuration = aExecutionDuration;
  }
}
