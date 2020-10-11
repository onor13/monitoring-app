package filter;

import filter.single.SingeTaskResultFilterByAppName;
import filter.single.SingleTaskResultFilterByResultType;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import task.TaskResult;
import task.TaskResultType;
import task.criteria.ApplicationNameFilterCriteria;
import task.criteria.FilterCriteria;
import task.criteria.FilterCriteriaType;
import task.criteria.TaskResultTypeFilterCriteria;

@Component
public class TaskResultFilteringSystem {

  @Autowired
  private transient SingeTaskResultFilterByAppName singeTaskResultFilterByAppName;

  @Autowired
  private transient SingleTaskResultFilterByResultType singleTaskResultFilterByResultType;

  private final transient Collection<FilterChangeListener> filterChangeListeners = new ArrayList<>();

  private final Collection<FilterCriteria> filterCriteria = new ArrayList<>();

  public void addChangeListener(FilterChangeListener filterChangeListener) {
    filterChangeListeners.add(filterChangeListener);
  }

  public void updateFilterByAppName(String applicationName) {
    singeTaskResultFilterByAppName.setApplicationNameFilter(applicationName);

    removeFilterCriteria(FilterCriteriaType.ApplicationName);
    filterCriteria.add(new ApplicationNameFilterCriteria(applicationName));
    filterChangeListeners.stream().forEach(filterChangeListener ->
        filterChangeListener.onFilterChange(FilterCriteriaType.ApplicationName));
  }

  public void updateFilterByResultType(TaskResultType taskResultType) {
    singleTaskResultFilterByResultType.resetFilter();
    singleTaskResultFilterByResultType.addAcceptedResultType(taskResultType);

    removeFilterCriteria(FilterCriteriaType.ResultType);
    filterCriteria.add(new TaskResultTypeFilterCriteria(taskResultType));
    filterChangeListeners.stream().forEach(filterChangeListener ->
        filterChangeListener.onFilterChange(FilterCriteriaType.ResultType));
  }

  public void removeApplicationNameFilter() {
    singeTaskResultFilterByAppName.resetFilter();
    removeFilterCriteria(FilterCriteriaType.ApplicationName);
    filterChangeListeners.stream().forEach(filterChangeListener ->
        filterChangeListener.onFilterChange(FilterCriteriaType.ApplicationName));
  }

  public void removeTaskResultTypeFilter() {
    singleTaskResultFilterByResultType.resetFilter();
    removeFilterCriteria(FilterCriteriaType.ResultType);
    filterChangeListeners.stream().forEach(filterChangeListener ->
        filterChangeListener.onFilterChange(FilterCriteriaType.ResultType));
  }

  public boolean isAcceptedByAllFilters(TaskResult taskResult) {
    return singeTaskResultFilterByAppName.isAccepted(taskResult)
        && singleTaskResultFilterByResultType.isAccepted(taskResult);
  }

  private void removeFilterCriteria(FilterCriteriaType criteriaType) {
    filterCriteria.removeIf(criteria -> criteria.getType().equals(criteriaType));
  }

  public Collection<FilterCriteria> getFilterCriteria() {
    return filterCriteria;
  }
}
