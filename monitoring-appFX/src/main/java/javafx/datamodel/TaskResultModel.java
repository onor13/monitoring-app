package javafx.datamodel;

import converters.LocalDateTimeConverter;
import javafx.beans.property.SimpleStringProperty;
import task.TaskResult;

public class TaskResultModel {
  private final SimpleStringProperty applicationId = new SimpleStringProperty("");
  private final SimpleStringProperty name = new SimpleStringProperty("");
  private final SimpleStringProperty group = new SimpleStringProperty("");
  private final SimpleStringProperty result = new SimpleStringProperty("");
  private final SimpleStringProperty startTime = new SimpleStringProperty("");
  private final SimpleStringProperty executionDuration = new SimpleStringProperty("");

  private final transient TaskResult taskResult;

  //TODO use converters instead for enum's and date related objects
  private final transient LocalDateTimeConverter ldcFormatter = new LocalDateTimeConverter();

  public TaskResultModel(TaskResult taskResult) {
    this.taskResult = taskResult;
    applicationId.set(taskResult.getApplicationName());
    name.set(taskResult.getTaskName());
    group.set(taskResult.getTaskGroup());
    result.set(taskResult.getTaskResultType().name());
    startTime.set(ldcFormatter.format(taskResult.getTaskStartTime()));
    executionDuration.set(taskResult.getTaskExecutionDuration().toString());
  }

  public TaskResult getTaskResult() {
    return taskResult;
  }

  public String getApplicationId() {
    return applicationId.get();
  }

  public SimpleStringProperty applicationIdProperty() {
    return applicationId;
  }

  public void setApplicationId(String applicationId) {
    this.applicationId.set(applicationId);
  }

  public String getName() {
    return name.get();
  }

  public SimpleStringProperty nameProperty() {
    return name;
  }

  public void setName(String name) {
    this.name.set(name);
  }

  public String getGroup() {
    return group.get();
  }

  public SimpleStringProperty groupProperty() {
    return group;
  }

  public void setGroup(String group) {
    this.group.set(group);
  }

  public String getResult() {
    return result.get();
  }

  public SimpleStringProperty resultProperty() {
    return result;
  }

  public void setResult(String result) {
    this.result.set(result);
  }

  public String getStartTime() {
    return startTime.get();
  }

  public SimpleStringProperty startTimeProperty() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime.set(startTime);
  }

  public String getExecutionDuration() {
    return executionDuration.get();
  }

  public SimpleStringProperty executionDurationProperty() {
    return executionDuration;
  }

  public void setExecutionDuration(String executionDuration) {
    this.executionDuration.set(executionDuration);
  }
}
