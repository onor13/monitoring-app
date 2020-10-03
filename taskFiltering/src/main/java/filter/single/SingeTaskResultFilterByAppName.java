package filter.single;

import org.springframework.stereotype.Component;
import task.TaskResult;

@Component
@SuppressWarnings("PMD.BeanMembersShouldSerialize")
public class SingeTaskResultFilterByAppName implements SingleTaskResultFilter {

  private static final String EMPTY_FILTER = "";
  private String applicationName = EMPTY_FILTER;

  @Override
  public boolean isAccepted(TaskResult taskResult) {
    if (isEmptyFilter()) {
      return true; //No filter, so everything is accepted;
    }
    return applicationName.equals(taskResult.getApplicationName());
  }

  @Override
  public void resetFilter() {
    applicationName = EMPTY_FILTER;
  }

  @Override
  public boolean isEmptyFilter() {
    return EMPTY_FILTER.equals(applicationName);
  }

  public void setApplicationNameFilter(String applicationNameFilter) {
    this.applicationName = applicationNameFilter;
  }
}
