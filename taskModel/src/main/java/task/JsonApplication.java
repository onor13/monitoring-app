package task;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import serializer.CustomLocalDateTimeDeserializer;
import serializer.CustomLocalDateTimeSerializer;

import java.time.LocalDateTime;
import java.util.Set;

@JsonIdentityInfo(
    generator= ObjectIdGenerators.IntSequenceGenerator.class,property="@id",
    scope = JsonApplication.class)
public class JsonApplication implements Application {

  private String name;
  private LocalDateTime startTime;
  private Set<TaskResult> tasksResults;

  public JsonApplication(){ }

  public JsonApplication( String name, LocalDateTime startTime ){
    this.name = name;
    this.startTime = startTime;
  }

  @Override
  public String getName() {
    return name;
  }

  public void setName( String aName ) {
    name = aName;
  }

  @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
  @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
  public LocalDateTime getStartTime() {
    return startTime;
  }

  public void setStartTime( LocalDateTime aStartTime ) {
    startTime = aStartTime;
  }

  @Override
  public Set<TaskResult> getTasksResults() {
    return tasksResults;
  }

  public void setTasksResults( Set<TaskResult> aTasksResults ) {
    tasksResults = aTasksResults;
  }
}
