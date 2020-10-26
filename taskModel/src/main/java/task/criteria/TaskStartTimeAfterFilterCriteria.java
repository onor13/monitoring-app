package task.criteria;

import java.time.LocalDateTime;
import task.TaskResult;

public class TaskStartTimeAfterFilterCriteria implements FilterCriteria<LocalDateTime> {

  private final transient LocalDateTime startedAfter;

  public TaskStartTimeAfterFilterCriteria(LocalDateTime startedAfter) {
    this.startedAfter = startedAfter;
  }

  @Override
  public FilterCriteriaType getType() {
    return FilterCriteriaType.StartTimeAfter;
  }

  @Override
  public LocalDateTime getCriteriaValue() {
    return startedAfter;
  }

  @Override
  public boolean isAccepted(TaskResult taskResult) {
    if (taskResult.getTaskStartTime() == null) {
      return false;
    }
    return taskResult.getTaskStartTime().isAfter(startedAfter);
  }
}
