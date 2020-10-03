package filter.single;

import org.springframework.stereotype.Component;
import task.TaskResult;

@Component
@SuppressWarnings("PMD.BeanMembersShouldSerialize")
public class SingleTaskResultFilterByGroupName implements SingleTaskResultFilter {

  private static final String EMPTY_FILTER = "";
  private String groupName = EMPTY_FILTER;

  @Override
  public boolean isAccepted(TaskResult taskResult) {
    if (isEmptyFilter()) {
      return true; //No filter, so everything is accepted;
    }
    return groupName.equals(taskResult.getTaskGroup());
  }

  @Override
  public void resetFilter() {
    groupName = EMPTY_FILTER;
  }

  @Override
  public boolean isEmptyFilter() {
    return EMPTY_FILTER.equals(groupName);
  }

  public void setTaskGroupFilter(String taskGroupFilter) {
    this.groupName = taskGroupFilter;
  }
}
