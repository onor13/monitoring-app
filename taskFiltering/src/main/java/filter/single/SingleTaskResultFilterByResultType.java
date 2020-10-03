package filter.single;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import task.TaskResult;
import task.TaskResultType;

@Component
@SuppressWarnings("PMD.BeanMembersShouldSerialize")
public class SingleTaskResultFilterByResultType implements SingleTaskResultFilter {

  private final List<TaskResultType> acceptedResultTypes = new ArrayList<>();

  @Override
  public boolean isAccepted(TaskResult taskResult) {
    if (isEmptyFilter()) {
      return true; //No filter, so everything is accepted;
    }
    return acceptedResultTypes.contains(taskResult.getTaskResultType());
  }

  @Override
  public boolean isEmptyFilter() {
    return acceptedResultTypes.isEmpty();
  }

  @Override
  public void resetFilter() {
    acceptedResultTypes.clear();
  }

  public void addAcceptedResultType(TaskResultType resultType) {
    if (!acceptedResultTypes.contains(resultType)) {
      acceptedResultTypes.add(resultType);
    }
  }
}
