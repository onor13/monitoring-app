package task.criteria;

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
  public String toString() {
    return "TaskResult group name criteria";
  }
}
