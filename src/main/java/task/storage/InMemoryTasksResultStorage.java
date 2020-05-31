package task.storage;

import task.ITaskResult;

import java.time.Instant;
import java.util.*;

public class InMemoryTasksResultStorage
    implements ITasksResultsStorage {
  Set<ITaskResult> taskResults = new HashSet<>();

  public InMemoryTasksResultStorage(){}


  @Override public int size() {
    return taskResults.size();
  }

  @Override public synchronized void addTaskResult( ITaskResult taskResult ) {
    taskResults.add( taskResult );
  }

  @Override public boolean contains( ITaskResult taskResult ) {
    return taskResults.contains( taskResult );
  }

  @Override public void removeAll() {
    taskResults.clear();
  }

  @Override public void removeOlderThan( Instant instant ) {
    taskResults.removeIf( taskResult -> taskResult.getStartTime().isBefore( instant ) );
  }

  @Override public Iterator<ITaskResult> iterator() {
    return taskResults.iterator();
  }
}
