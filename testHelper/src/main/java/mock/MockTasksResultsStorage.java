package mock;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import storage.TasksResultsStorage;
import task.TaskResult;

@SuppressWarnings("PMD.BeanMembersShouldSerialize")
public class MockTasksResultsStorage implements TasksResultsStorage {
  private final Set<TaskResult> taskResults = new HashSet<>();

  @Override
  public long size() {
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
