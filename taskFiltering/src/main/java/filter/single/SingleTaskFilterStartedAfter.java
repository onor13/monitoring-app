package filter.single;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.stereotype.Component;
import task.TaskResult;

@Component
@SuppressWarnings("PMD.BeanMembersShouldSerialize")
public class SingleTaskFilterStartedAfter implements SingleTaskResultFilter {

  Optional<LocalDateTime> startedAfterFilter = Optional.empty();

  @Override
  public boolean isAccepted(TaskResult taskResult) {
    if (startedAfterFilter.isEmpty()) {
      return true;
    }
    if (taskResult.getTaskStartTime() == null) {
      return false;
    }
    return taskResult.getTaskStartTime().isAfter(startedAfterFilter.get());
  }

  @Override
  public boolean isEmptyFilter() {
    return startedAfterFilter.isEmpty();
  }

  @Override
  public void resetFilter() {
    startedAfterFilter = Optional.empty();
  }

  public void setStartedAfterFilter(LocalDateTime startedBefore) {
    startedAfterFilter = Optional.ofNullable(startedBefore);
  }
}
