package javafx;

public enum TaskFilterType {
  ApplicationName("Application name"),
  ResultType("Result type"),
  TaskGroup("Task group"),
  TaskStartTime("Task start datetime"),
  TaskExecutionDurationBelow("Task execution duration below"),
  TaskExecutionDurationAbove("Task execution duration above");

  private String name;

  TaskFilterType(String theType) {
    this.name = theType;
  }

  @Override
  public String toString() {
    return name;
  }
}
