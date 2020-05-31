package task.storage;

import task.ITaskResult;
import java.time.Instant;
import java.util.Set;

public interface ITasksResultsTimeFiltered extends ITasksResultsStorage {
  Set<ITaskResult> getTasksResultsStartedAfter( Instant instant );
  Set<ITaskResult> getTasksResultsFinishedBefore( Instant instant );
  Set<ITaskResult> getTasksResultsInTimeRange( Instant startInstant, Instant endInstant );
}
