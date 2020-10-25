package filter.single;

import java.time.Duration;
import java.util.Optional;
import org.springframework.stereotype.Component;
import task.TaskResult;

@Component
@SuppressWarnings("PMD.BeanMembersShouldSerialize")
public class SingleTaskFilterExecutionDurationBelow implements SingleTaskResultFilter {

  Optional<Duration> durationFilter = Optional.empty();

  @Override
  public boolean isAccepted(TaskResult taskResult) {
    if (durationFilter.isEmpty()) {
      return true;
    }
    if (taskResult.getTaskExecutionDuration() == null) {
      return false;
    }
    return taskResult.getTaskExecutionDuration().compareTo(durationFilter.get()) < 0;
  }

  @Override
  public boolean isEmptyFilter() {
    return durationFilter.isEmpty();
  }

  @Override
  public void resetFilter() {
    durationFilter = Optional.empty();
  }

  public void setDurationFilter(Duration durationFilterBelow) {
    durationFilter = Optional.ofNullable(durationFilterBelow);
  }
}
