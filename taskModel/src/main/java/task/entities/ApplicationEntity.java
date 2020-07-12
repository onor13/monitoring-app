package task.entities;

import constants.Formats;
import task.Application;
import task.TaskResult;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity(name="Application")
@Table(name = ApplicationEntity.TABLE_NAME)
@NamedQueries({
    @NamedQuery(name= ApplicationEntity.FIND_APPLICATION_BY_ID,
        query="select a from Application a " +
            "join fetch a.tasksResults t " +
            "where a.id = :id"),
    @NamedQuery(name= ApplicationEntity.FIND_ALL_WITH_TASKS_RESULTS,
        query="select distinct a from Application a " +
            "join fetch a.tasksResults t "),
})
public class ApplicationEntity
    extends AbstractEntity implements Application {

  public static final String TABLE_NAME = "application";
  public static final String FIND_APPLICATION_BY_ID = "Application.findById";
  public static final String FIND_ALL_WITH_TASKS_RESULTS = "Application.findAllWithTasksResults";

  @Column(name = "NAME")
  @NotNull
  private String name;

  @Column(name = "START_DATE_TIME", columnDefinition = "TIMESTAMP")
  @NotNull
  private LocalDateTime startTime;

  @OneToMany(mappedBy = "application", cascade=CascadeType.ALL,
      orphanRemoval=true)
  private Set<TaskResultEntity> tasksResults = new HashSet<>();

  public String getName() {
    return name;
  }

  public void setName( String aName ) {
    name = aName;
  }

  public LocalDateTime getStartTime() {
    return startTime;
  }

  @Override
  public Set<TaskResult> getTasksResults() {
    return tasksResults.stream().collect( Collectors.toSet() );
  }

  public void setStartTime( LocalDateTime aStartDateTime ) {
    startTime = aStartDateTime;
  }

  public void addTaskResult( TaskResultEntity taskResult ){
    tasksResults.add( taskResult );
  }

  public String toString() {
    return String.format("Application - id: %d, name: %s, startDateTime: %s",
        id, name, startTime == null ? "<unknown>" : Formats.FORMATTER.format( startTime ) );
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    if (!super.equals(o))
      return false;
    ApplicationEntity app = (ApplicationEntity) o;
    if ( ! Objects.equals( name, app.name ) )
      return false;
    return Objects.equals( startTime, app.startTime );
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + (name == null ? 0 : name.hashCode() );
    result = 31 * result + (startTime == null ? 0 : startTime.hashCode()) ;
    return result;
  }

}
