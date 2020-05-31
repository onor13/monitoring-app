package task.storage;

import task.ITaskResult;

import java.time.Instant;
import java.util.Set;

public interface ITasksResultsStorage {
  Set<ITaskResult> getAllTasksResults();
  void addTaskResult( ITaskResult taskResult );
  boolean contains( ITaskResult taskResult );
  void removeAllTaskResults();
  void removeTaskResultsOlderThan( Instant instant );
}
