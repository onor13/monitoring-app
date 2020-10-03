package filter.single;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import task.TaskResult;
import task.TaskResultType;

@Component
public class SingleTaskResultFilteringSystem {

  @Autowired
  private transient SingeTaskResultFilterByAppName singeTaskResultFilterByAppName;

  @Autowired
  private transient SingleTaskResultFilterByResultType singleTaskResultFilterByResultType;

  public void updateFilterByAppName(String applicationName) {
    singeTaskResultFilterByAppName.setApplicationNameFilter(applicationName);
  }

  public void updateFilterByResultType(TaskResultType taskResultType) {
    singleTaskResultFilterByResultType.resetFilter();
    singleTaskResultFilterByResultType.addAcceptedResultType(taskResultType);
  }

  public void removeApplicationNameFilter(){
    singeTaskResultFilterByAppName.resetFilter();
  }
  public void removeTaskResultTypeFilter(){
    singleTaskResultFilterByResultType.resetFilter();
  }

  public boolean isAcceptedByAllFilters(TaskResult taskResult) {
    return singeTaskResultFilterByAppName.isAccepted(taskResult)
        && singleTaskResultFilterByResultType.isAccepted(taskResult);
  }
}
