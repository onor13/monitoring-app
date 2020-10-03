package filter.single;

import task.TaskResult;

public interface SingleTaskResultFilter {

  boolean isAccepted(TaskResult taskResult);

  boolean isEmptyFilter();

  void resetFilter();
}
