package task.storage;

import task.ITaskResult;

import java.time.Instant;

public interface ITasksResultsStorage extends Iterable<ITaskResult> {
  int size();
  void addTaskResult( ITaskResult taskResult );
  boolean contains( ITaskResult taskResult );
  void removeAll();
  void removeOlderThan( Instant instant );
}
