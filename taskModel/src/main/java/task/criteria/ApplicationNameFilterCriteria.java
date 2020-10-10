package task.criteria;

public class ApplicationNameFilterCriteria implements FilterCriteria<String> {

  final transient String applicationName;

  public ApplicationNameFilterCriteria(String applicationNameCriteria) {
    this.applicationName = applicationNameCriteria;
  }

  @Override
  public FilterCriteriaType getType() {
    return FilterCriteriaType.ApplicationName;
  }

  @Override
  public String getCriteriaValue() {
    return applicationName;
  }

  @Override
  public String toString() {
    return "Application name criteria";
  }
}
