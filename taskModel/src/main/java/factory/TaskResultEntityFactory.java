package factory;

import task.TaskResult;
import task.entities.ApplicationEntity;
import task.entities.TaskResultEntity;

public class TaskResultEntityFactory {

  public static TaskResultEntity createInstanceFrom(ApplicationEntity appEntity, TaskResult taskResult) {
    TaskResultEntity tre = new TaskResultEntity();
    tre.setApplication(appEntity);
    tre.setTaskName(taskResult.getTaskName());
    tre.setTaskExecutionDuration(taskResult.getTaskExecutionDuration());
    tre.setTaskGroup(taskResult.getTaskGroup());
    tre.setTaskResultType(taskResult.getTaskResultType());
    tre.setTaskStartTime(taskResult.getTaskStartTime());
    return tre;
  }
}
