package storage;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import task.TaskResult;

@SuppressWarnings("PMD.BeanMembersShouldSerialize")
public class InMemoryTasksResultStorage implements TasksResultsStorage {
  private Set<TaskResult> taskResults = new HashSet<>();

  public InMemoryTasksResultStorage() {
  }

  @Override
  public int size() {
    return taskResults.size();
  }

  @Override
  public synchronized void addTaskResult(TaskResult taskResult) {
    taskResults.add(taskResult);
  }

  @Override
  public boolean contains(TaskResult taskResult) {
    return taskResults.contains(taskResult);
  }

  @Override
  public void removeAll() {
    taskResults.clear();
  }

  @Override
  public void removeOlderThan(LocalDateTime ldt) {
    taskResults.removeIf(taskResult -> taskResult.getTaskStartTime().isBefore(ldt));
  }

  @Override
  public Iterator<TaskResult> iterator() {
    return taskResults.iterator();
  }
}
