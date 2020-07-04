package storage;

import task.TaskResult;

import java.time.Instant;

public interface TasksResultsStorage extends Iterable<TaskResult> {
  int size();
  void addTaskResult( TaskResult taskResult );
  boolean contains( TaskResult taskResult );
  void removeAll();
  void removeOlderThan( Instant instant );
}
