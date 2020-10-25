package task.criteria;

import java.time.Duration;
import task.TaskResult;

public class TaskExecutionDurationAboveFilterCriteria implements FilterCriteria<Duration> {

  private final transient Duration duration;

  public TaskExecutionDurationAboveFilterCriteria(Duration duration) {
    this.duration = duration;
  }

  @Override
  public FilterCriteriaType getType() {
    return FilterCriteriaType.ExecutionDurationAbove;
  }

  @Override
  public Duration getCriteriaValue() {
    return duration;
  }

  @Override
  public boolean isAccepted(TaskResult taskResult) {
    if (taskResult.getTaskExecutionDuration() == null) {
      return false;
    }
    return taskResult.getTaskExecutionDuration().compareTo(duration) > 0;
  }
}
