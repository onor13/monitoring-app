package task.criteria;

public enum FilterCriteriaType {
  ApplicationName("Application name"),
  ResultType("Result type"),
  TaskGroup("Task group");

  private String name;

  FilterCriteriaType(String theType) {
    this.name = theType;
  }

  @Override
  public String toString() {
    return name;
  }
}