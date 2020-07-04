package task.storage;

import task.TaskResult;

import java.time.Instant;
import java.util.*;

public class InMemoryTasksResultStorage
    implements TasksResultsStorage {
  Set<TaskResult> taskResults = new HashSet<>();

  public InMemoryTasksResultStorage(){}


  @Override public int size() {
    return taskResults.size();
  }

  @Override public synchronized void addTaskResult( TaskResult taskResult ) {
    taskResults.add( taskResult );
  }

  @Override public boolean contains( TaskResult taskResult ) {
    return taskResults.contains( taskResult );
  }

  @Override public void removeAll() {
    taskResults.clear();
  }

  @Override public void removeOlderThan( Instant instant ) {
    taskResults.removeIf( taskResult -> taskResult.getStartTime().isBefore( instant ) );
  }

  @Override public Iterator<TaskResult> iterator() {
    return taskResults.iterator();
  }
}
