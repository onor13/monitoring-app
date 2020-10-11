package storage;

import java.time.LocalDateTime;
import java.util.Collection;
import task.TaskResult;
import task.criteria.FilterCriteria;

public interface TasksResultsStorage extends Iterable<TaskResult> {
  long size();

  void addTaskResult(TaskResult taskResult);

  boolean contains(TaskResult taskResult);

  void removeAll();

  void removeOlderThan(LocalDateTime instant);

  Collection<TaskResult> filter(Collection<FilterCriteria> criteria);
}
