package task;

import task.TaskDataConnector;
import storage.TasksResultsStorage;

public class TaskDataProvider {
  private TaskDataConnector dataConnector;
  TasksResultsStorage resultsStorage;

  public TaskDataProvider( TaskDataConnector taskDataConnector, TasksResultsStorage tasksResultsStorage ){
    this.dataConnector = taskDataConnector;
    this.resultsStorage = tasksResultsStorage;
  }
}
