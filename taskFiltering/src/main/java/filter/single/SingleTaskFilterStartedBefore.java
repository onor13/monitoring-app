package filter.single;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.stereotype.Component;
import task.TaskResult;

@Component
@SuppressWarnings("PMD.BeanMembersShouldSerialize")
public class SingleTaskFilterStartedBefore implements SingleTaskResultFilter {

  Optional<LocalDateTime> startedBeforeFilter = Optional.empty();

  @Override
  public boolean isAccepted(TaskResult taskResult) {
    if (startedBeforeFilter.isEmpty()) {
      return true;
    }
    if (taskResult.getTaskStartTime() == null) {
      return false;
    }
    return taskResult.getTaskStartTime().isBefore(startedBeforeFilter.get());
  }

  @Override
  public boolean isEmptyFilter() {
    return startedBeforeFilter.isEmpty();
  }

  @Override
  public void resetFilter() {
    startedBeforeFilter = Optional.empty();
  }

  public void setStartedBeforeFilter(LocalDateTime startedBefore) {
    startedBeforeFilter = Optional.ofNullable(startedBefore);
  }
}
