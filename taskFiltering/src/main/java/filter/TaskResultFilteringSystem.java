package filter;

import filter.single.SingeTaskResultFilterByAppName;
import filter.single.SingleTaskFilterExecutionDurationAbove;
import filter.single.SingleTaskFilterExecutionDurationBelow;
import filter.single.SingleTaskFilterStartedAfter;
import filter.single.SingleTaskFilterStartedBefore;
import filter.single.SingleTaskResultFilterByGroupName;
import filter.single.SingleTaskResultFilterByResultType;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import task.TaskResult;
import task.TaskResultType;
import task.criteria.ApplicationNameFilterCriteria;
import task.criteria.FilterCriteria;
import task.criteria.FilterCriteriaType;
import task.criteria.TaskExecutionDurationAboveFilterCriteria;
import task.criteria.TaskExecutionDurationBelowFilterCriteria;
import task.criteria.TaskGroupNameFilterCriteria;
import task.criteria.TaskResultTypeFilterCriteria;
import task.criteria.TaskStartTimeAfterFilterCriteria;
import task.criteria.TaskStartTimeBeforeFilterCriteria;

@Component
public class TaskResultFilteringSystem {

  @Autowired
  private transient SingeTaskResultFilterByAppName singeTaskResultFilterByAppName;

  @Autowired
  private transient SingleTaskResultFilterByResultType singleTaskResultFilterByResultType;

  @Autowired
  private transient SingleTaskResultFilterByGroupName singleTaskResultFilterByGroupName;

  @Autowired
  private transient SingleTaskFilterExecutionDurationBelow singleTaskFilterExecutionDurationBelow;

  @Autowired
  private transient SingleTaskFilterExecutionDurationAbove singleTaskFilterExecutionDurationAbove;

  @Autowired
  private transient SingleTaskFilterStartedBefore singleTaskFilterStartedBefore;

  @Autowired
  private transient SingleTaskFilterStartedAfter singleTaskFilterStartedAfter;

  private final transient Collection<FilterChangeListener> filterChangeListeners = new ArrayList<>();

  private final Collection<FilterCriteria> filterCriteria = new ArrayList<>();

  public void addChangeListener(FilterChangeListener filterChangeListener) {
    filterChangeListeners.add(filterChangeListener);
  }

  public void updateFilterByExecutionDurationBelow(Duration duration) {
    singleTaskFilterExecutionDurationBelow.setDurationFilter(duration);
    removeFilterCriteria(FilterCriteriaType.ExecutionDurationBelow);
    filterCriteria.add(new TaskExecutionDurationBelowFilterCriteria(duration));
    filterChangeListeners.stream().forEach(filterChangeListener ->
        filterChangeListener.onFilterChange(FilterCriteriaType.ExecutionDurationBelow));
  }

  public void updateFilterByExecutionDurationAbove(Duration duration) {
    singleTaskFilterExecutionDurationAbove.setDurationFilter(duration);
    removeFilterCriteria(FilterCriteriaType.ExecutionDurationAbove);
    filterCriteria.add(new TaskExecutionDurationAboveFilterCriteria(duration));
    filterChangeListeners.stream().forEach(filterChangeListener ->
        filterChangeListener.onFilterChange(FilterCriteriaType.ExecutionDurationAbove));
  }

  public void updateFilterByTaskGroupName(String taskGroupName) {
    singleTaskResultFilterByGroupName.setTaskGroupFilter(taskGroupName);

    removeFilterCriteria(FilterCriteriaType.TaskGroup);
    filterCriteria.add(new TaskGroupNameFilterCriteria(taskGroupName));
    filterChangeListeners.stream().forEach(filterChangeListener ->
        filterChangeListener.onFilterChange(FilterCriteriaType.TaskGroup));
  }

  public void updateFilterByAppName(String applicationName) {
    singeTaskResultFilterByAppName.setApplicationNameFilter(applicationName);

    removeFilterCriteria(FilterCriteriaType.ApplicationName);
    filterCriteria.add(new ApplicationNameFilterCriteria(applicationName));
    filterChangeListeners.stream().forEach(filterChangeListener ->
        filterChangeListener.onFilterChange(FilterCriteriaType.ApplicationName));
  }

  public void updateFilterByStartedBefore(LocalDateTime referenceTime) {
    singleTaskFilterStartedBefore.setStartedBeforeFilter(referenceTime);

    removeFilterCriteria(FilterCriteriaType.StartTimeBefore);
    filterCriteria.add(new TaskStartTimeBeforeFilterCriteria(referenceTime));
    filterChangeListeners.stream().forEach(filterChangeListener ->
        filterChangeListener.onFilterChange(FilterCriteriaType.StartTimeBefore));
  }

  public void updateFilterByStartedAfter(LocalDateTime referenceTime) {
    singleTaskFilterStartedAfter.setStartedAfterFilter(referenceTime);

    removeFilterCriteria(FilterCriteriaType.StartTimeAfter);
    filterCriteria.add(new TaskStartTimeAfterFilterCriteria(referenceTime));
    filterChangeListeners.stream().forEach(filterChangeListener ->
        filterChangeListener.onFilterChange(FilterCriteriaType.StartTimeAfter));
  }

  public void updateFilterByResultType(TaskResultType taskResultType) {
    singleTaskResultFilterByResultType.resetFilter();
    singleTaskResultFilterByResultType.addAcceptedResultType(taskResultType);

    removeFilterCriteria(FilterCriteriaType.ResultType);
    filterCriteria.add(new TaskResultTypeFilterCriteria(taskResultType));
    filterChangeListeners.stream().forEach(filterChangeListener ->
        filterChangeListener.onFilterChange(FilterCriteriaType.ResultType));
  }

  /***
   * <p>removes task filter criteria is it was present.</p>
   * @param taskFilterType task filter type to remove
   */
  public void removeTaskFilterCriteria(FilterCriteriaType taskFilterType) {
    removeFilterCriteria(taskFilterType);
    switch (taskFilterType) {
      case ResultType:
        singleTaskResultFilterByResultType.resetFilter();
        break;
      case ApplicationName:
        singeTaskResultFilterByAppName.resetFilter();
        break;
      case TaskGroup:
        singleTaskResultFilterByGroupName.resetFilter();
        break;
      case ExecutionDurationBelow:
        singleTaskFilterExecutionDurationBelow.resetFilter();
        break;
      case ExecutionDurationAbove:
        singleTaskFilterExecutionDurationAbove.resetFilter();
        break;
      case StartTimeBefore:
        singleTaskFilterStartedBefore.resetFilter();
        break;
      case StartTimeAfter:
        singleTaskFilterStartedAfter.resetFilter();
        break;
      default:
        throw new UnsupportedOperationException("Not supported FilterCriteriaType: " + taskFilterType);
    }
    filterChangeListeners.stream().forEach(filterChangeListener ->
        filterChangeListener.onFilterChange(taskFilterType));
  }

  public boolean isAcceptedByAllFilters(TaskResult taskResult) {
    return singeTaskResultFilterByAppName.isAccepted(taskResult)
        && singleTaskResultFilterByResultType.isAccepted(taskResult)
        && singleTaskResultFilterByGroupName.isAccepted(taskResult)
        && singleTaskFilterExecutionDurationBelow.isAccepted(taskResult)
        && singleTaskFilterExecutionDurationAbove.isAccepted(taskResult)
        && singleTaskFilterStartedBefore.isAccepted(taskResult)
        && singleTaskFilterStartedAfter.isAccepted(taskResult);
  }

  private void removeFilterCriteria(FilterCriteriaType criteriaType) {
    filterCriteria.removeIf(criteria -> criteria.getType().equals(criteriaType));
  }

  public Collection<FilterCriteria> getFilterCriteria() {
    return filterCriteria;
  }
}
