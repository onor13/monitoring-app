package javafx.listeners;

import java.time.Duration;
import java.time.LocalDateTime;
import task.TaskResultType;
import task.criteria.FilterCriteriaType;

public interface TaskFilterChangeListener {
  void onApplicationNameFilterChange(String applicationName);

  void onTaskGroupFilterChange(String taskGroupF);

  void onTaskResultTypeFilterChange(TaskResultType taskResultType);

  void onTaskStartTimeBeforeFilterChange(LocalDateTime dateTime);

  void onTaskStartTimeAfterFilterChange(LocalDateTime dateTime);

  void onExecutionDurationBelowFilterChange(Duration duration);

  void onExecutionDurationAboveFilterChange(Duration duration);

  void onFilterRemove(FilterCriteriaType taskFilterType);
}
