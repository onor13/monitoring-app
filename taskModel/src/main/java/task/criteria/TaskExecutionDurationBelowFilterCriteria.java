package task.criteria;

import java.time.Duration;
import task.TaskResult;

public class TaskExecutionDurationBelowFilterCriteria implements FilterCriteria<Duration> {

  private final transient Duration duration;

  public TaskExecutionDurationBelowFilterCriteria(Duration duration) {
    this.duration = duration;
  }

  @Override
  public FilterCriteriaType getType() {
    return FilterCriteriaType.ExecutionDurationBelow;
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
    return taskResult.getTaskExecutionDuration().compareTo(duration) < 0;
  }
}
