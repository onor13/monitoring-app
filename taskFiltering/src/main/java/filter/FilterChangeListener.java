package filter;

import task.criteria.FilterCriteriaType;

public interface FilterChangeListener {
  void onFilterChange(FilterCriteriaType changedCriteriaType);
}
