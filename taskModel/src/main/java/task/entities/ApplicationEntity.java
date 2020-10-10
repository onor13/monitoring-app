package task.entities;

import converters.LocalDateTimeConverter;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import task.Application;
import task.TaskResult;

@Entity(name = "Application")
@Table(name = ApplicationEntity.TABLE_NAME)
@NamedQueries({
    @NamedQuery(name = ApplicationEntity.FIND_APPLICATION_BY_NAME,
        query = "select a from Application a "
            + " where a.name = :name"),
    @NamedQuery(name = ApplicationEntity.FIND_ALL_WITH_TASKS_RESULTS,
        query = "select distinct a from Application a"
            + " join fetch a.tasksResults t "),
})
@SuppressWarnings("PMD.BeanMembersShouldSerialize")
public class ApplicationEntity implements Application {

  public static final String TABLE_NAME = "application";
  public static final String CRITERIA_NAME = "name";
  public static final String FIND_APPLICATION_BY_NAME = "Application.findByName";
  public static final String FIND_ALL_WITH_TASKS_RESULTS = "Application.findAllWithTasksResults";
  private static final long serialVersionUID = -5884907756474896718L;

  @Column(name = "NAME")
  @NotNull
  @Id
  private String name;

  @Column(name = "START_DATE_TIME")
  @NotNull
  private LocalDateTime startTime;

  @OneToMany(mappedBy = "application", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<TaskResultEntity> tasksResults = new HashSet<>();

  @Override
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public LocalDateTime getStartTime() {
    return startTime;
  }

  @Override
  public Set<TaskResult> getTasksResults() {
    return tasksResults.stream().collect(Collectors.toSet());
  }

  public void setStartTime(LocalDateTime startTime) {
    this.startTime = startTime;
  }

  public void addTaskResult(TaskResultEntity taskResult) {
    tasksResults.add(taskResult);
  }

  @Override
  public String toString() {
    return String.format("Application - name: %s, startDateTime: %s",
      name, startTime == null ? "<unknown>" : new LocalDateTimeConverter().format(startTime));
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
    ApplicationEntity app = (ApplicationEntity) o;
    if (!Objects.equals(name, app.name)) {
      return false;
    }
    return Objects.equals(startTime, app.startTime);
  }

  @Override
  public int hashCode() {
    return 31 * name.hashCode();
  }

}
