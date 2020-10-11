package task.criteria;

import task.TaskResult;

public interface FilterCriteria<T> {

  FilterCriteriaType getType();

  T getCriteriaValue();

  boolean isAccepted(TaskResult taskResult);
}
