package task.criteria;

import java.time.LocalDateTime;
import task.TaskResult;

public class TaskStartTimeBeforeFilterCriteria implements FilterCriteria<LocalDateTime> {

  private final transient LocalDateTime startedBefore;

  public TaskStartTimeBeforeFilterCriteria(LocalDateTime startedBefore) {
    this.startedBefore = startedBefore;
  }

  @Override
  public FilterCriteriaType getType() {
    return FilterCriteriaType.StartTimeBefore;
  }

  @Override
  public LocalDateTime getCriteriaValue() {
    return startedBefore;
  }

  @Override
  public boolean isAccepted(TaskResult taskResult) {
    if (taskResult.getTaskStartTime() == null) {
      return false;
    }
    return taskResult.getTaskStartTime().isBefore(startedBefore);
  }
}
