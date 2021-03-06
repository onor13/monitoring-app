package task;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDateTime;
import java.util.Set;
import serializer.CustomLocalDateTimeDeserializer;
import serializer.CustomLocalDateTimeSerializer;

@JsonIdentityInfo(
    generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id",
    scope = JsonApplication.class)
public class JsonApplication implements Application {

  private String name;
  private LocalDateTime startTime;
  private Set<TaskResult> tasksResults;

  public JsonApplication() {
  }

  public JsonApplication(String name, LocalDateTime startTime) {
    this.name = name;
    this.startTime = startTime;
  }

  @Override
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
  @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
  @Override
  public LocalDateTime getStartTime() {
    return startTime;
  }

  public void setStartTime(LocalDateTime startTime) {
    this.startTime = startTime;
  }

  @Override
  public Set<TaskResult> getTasksResults() {
    return tasksResults;
  }

  public void setTasksResults(Set<TaskResult> taskResults) {
    this.tasksResults = taskResults;
  }
}
