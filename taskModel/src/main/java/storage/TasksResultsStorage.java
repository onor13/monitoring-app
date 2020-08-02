package storage;

import java.time.LocalDateTime;
import task.TaskResult;

public interface TasksResultsStorage extends Iterable<TaskResult> {
  long size();

  void addTaskResult(TaskResult taskResult);

  boolean contains(TaskResult taskResult);

  void removeAll();

  void removeOlderThan(LocalDateTime instant);
}
