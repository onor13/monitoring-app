package javafx.dataModel;

import javafx.beans.property.SimpleStringProperty;
import task.TaskResult;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class TaskResultModel {
  private final SimpleStringProperty applicationId = new SimpleStringProperty("");
  private final SimpleStringProperty name = new SimpleStringProperty("");
  private final SimpleStringProperty group = new SimpleStringProperty("");
  private final SimpleStringProperty result = new SimpleStringProperty("");
  private final SimpleStringProperty startTime = new SimpleStringProperty("");
  private final SimpleStringProperty executionDuration = new SimpleStringProperty("");

  //TODO use converters instead for enum's and date related objects
  private final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone( ZoneId.systemDefault());

  public TaskResultModel( TaskResult taskResult ){
    setApplicationId( taskResult.getApplicationName() );
    setName( taskResult.getTaskName() );
    setGroup( taskResult.getTaskGroup() );
    setResult( taskResult.getTaskResultType().name() );
    setStartTime( DATE_TIME_FORMATTER.format( taskResult.getTaskStartTime() ) );
    setExecutionDuration( taskResult.getTaskExecutionDuration().toString() );
  }

  public String getApplicationId() {
    return applicationId.get();
  }

  public SimpleStringProperty applicationIdProperty() {
    return applicationId;
  }

  public void setApplicationId( String aApplicationId ) {
    this.applicationId.set( aApplicationId );
  }

  public String getName() {
    return name.get();
  }

  public SimpleStringProperty nameProperty() {
    return name;
  }

  public void setName( String aName ) {
    this.name.set( aName );
  }

  public String getGroup() {
    return group.get();
  }

  public SimpleStringProperty groupProperty() {
    return group;
  }

  public void setGroup( String aGroup ) {
    this.group.set( aGroup );
  }

  public String getResult() {
    return result.get();
  }

  public SimpleStringProperty resultProperty() {
    return result;
  }

  public void setResult( String aResult ) {
    this.result.set( aResult );
  }

  public String getStartTime() {
    return startTime.get();
  }

  public SimpleStringProperty startTimeProperty() {
    return startTime;
  }

  public void setStartTime( String aStartTime ) {
    this.startTime.set( aStartTime );
  }

  public String getExecutionDuration() {
    return executionDuration.get();
  }

  public SimpleStringProperty executionDurationProperty() {
    return executionDuration;
  }

  public void setExecutionDuration( String aExecutionDuration ) {
    this.executionDuration.set( aExecutionDuration );
  }
}
