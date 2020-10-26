package task.criteria;

public enum FilterCriteriaType {
  ApplicationName("Application name"),
  ResultType("Result type"),
  TaskGroup("Task group"),
  StartTimeBefore("Task started before"),
  StartTimeAfter("Task started after"),
  ExecutionDurationBelow("Execution duration below"),
  ExecutionDurationAbove("Execution duration above");

  private String name;

  FilterCriteriaType(String theType) {
    this.name = theType;
  }

  @Override
  public String toString() {
    return name;
  }
}
