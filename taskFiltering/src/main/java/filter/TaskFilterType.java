package filter;

public enum TaskFilterType {
  ApplicationName("Application name"), ResultType("Result type"), TaskGroup("Task group");

  private String name;

  TaskFilterType(String theType) {
    this.name = theType;
  }

  @Override
  public String toString() {
    return name;
  }

}
