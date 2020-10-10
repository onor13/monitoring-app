package task.criteria;

public interface FilterCriteria<T> {

  FilterCriteriaType getType();

  T getCriteriaValue();
}
