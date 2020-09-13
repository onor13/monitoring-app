package javafx.listeners;

import java.time.LocalDateTime;
import javafx.TaskFilterType;
import task.TaskResultType;

public interface TaskFilterChangeListener {
  void onApplicationNameFilterChange(String applicationName);

  void onTaskGroupFilterChange(String taskGroupF);

  void onTaskResultTypeFilterChange(TaskResultType taskResultType);

  void onTaskStartTimeFilterChange(LocalDateTime dateTime);

  void onFilterRemove(TaskFilterType taskFilterType);
}
