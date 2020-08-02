package javafx;

import task.TaskResult;

public interface TasksResultsPresenter {
  void addTaskResult(TaskResult taskResult);

  void reloadFrom(Iterable<TaskResult> newTasksResults);
}
