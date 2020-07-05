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

@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class,property="@id", scope = JsonTaskResult.class)
public class JsonTaskResult implements TaskResult {

  private String applicationId;
  private String taskName;
  private String taskGroup;
  private TaskResultType taskResultType;
  private LocalDateTime startTime;
  private Duration executionDuration;

  @Override public String getApplicationId() {
    return applicationId;
  }

  public void setApplicationId( String aApplicationId ) {
    applicationId = aApplicationId;
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

  @Override
  @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
  @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
  public LocalDateTime getStartTime() {
    return startTime;
  }

  public void setStartTime( LocalDateTime aStartTime ) {
    startTime = aStartTime;
  }


  @Override
  @JsonSerialize(using = CustomDurationSerializer.class)
  @JsonDeserialize(using = CustomDurationDeserializer.class)
  public Duration getExecutionDuration() {
    return executionDuration;
  }

  public void setExecutionDuration( Duration aExecutionDuration ) {
    executionDuration = aExecutionDuration;
  }
}
