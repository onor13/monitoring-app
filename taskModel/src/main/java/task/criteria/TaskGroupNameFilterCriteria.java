package task.criteria;

import task.TaskResult;

public class TaskGroupNameFilterCriteria implements FilterCriteria<String> {

  private final transient String groupName;

  public TaskGroupNameFilterCriteria(String taskGroupNameCriteria) {
    groupName = taskGroupNameCriteria;
  }

  @Override
  public FilterCriteriaType getType() {
    return FilterCriteriaType.TaskGroup;
  }

  @Override
  public String getCriteriaValue() {
    return groupName;
  }

  @Override
  public boolean isAccepted(TaskResult taskResult) {
    return groupName.equals(taskResult.getTaskGroup());
  }

  @Override
  public String toString() {
    return "TaskResult group name criteria";
  }
}
