package task.criteria;

import task.TaskResultType;

public class TaskResultTypeFilterCriteria implements FilterCriteria<TaskResultType> {

  private final transient TaskResultType taskResultType;

  public TaskResultTypeFilterCriteria(TaskResultType filterCriteria) {
    taskResultType = filterCriteria;
  }

  @Override
  public FilterCriteriaType getType() {
    return FilterCriteriaType.ResultType;
  }

  @Override
  public TaskResultType getCriteriaValue() {
    return taskResultType;
  }

  @Override
  public String toString() {
    return "TaskResult type criteria";
  }
}
