package javafx.listeners;

import javafx.TaskFilterType;
import task.TaskResultType;

public interface TaskFilterChangeListener {
  void onApplicationNameFilterChange(String applicationName);

  void onTaskGroupFilterChange(String taskGroupF);

  void onTaskResultTypeFilterChange(TaskResultType taskResultType);

  void onFilterRemove(TaskFilterType taskFilterType);
}
