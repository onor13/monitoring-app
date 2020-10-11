import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;
import storage.TasksResultsStorage;
import task.TaskResult;
import task.criteria.FilterCriteria;

@SuppressWarnings("PMD.BeanMembersShouldSerialize")
public class InMemoryTasksResultStorage implements TasksResultsStorage {
  private Set<TaskResult> taskResults = new HashSet<>();

  public InMemoryTasksResultStorage() {
  }

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
  public Collection<TaskResult> filter(Collection<FilterCriteria> criteria) {
    return taskResults.stream().filter(taskResult -> isAllMatch(taskResult, criteria))
        .collect(Collectors.toCollection(ArrayList::new));
  }

  private boolean isAllMatch(TaskResult taskResult, Collection<FilterCriteria> criteria) {
    return criteria.stream().allMatch(fc -> fc.isAccepted(taskResult));
  }

  @Override
  public Iterator<TaskResult> iterator() {
    return taskResults.iterator();
  }
}
