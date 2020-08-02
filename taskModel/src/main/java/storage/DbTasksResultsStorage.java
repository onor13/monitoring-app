package storage;

import factory.TaskResultEntityFactory;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import task.TaskResult;
import task.dao.ApplicationDao;
import task.dao.TaskResultDao;
import task.entities.ApplicationEntity;
import task.entities.TaskResultEntity;

@Component
@SuppressWarnings("PMD.BeanMembersShouldSerialize")
public class DbTasksResultsStorage implements TasksResultsStorage {

  @Autowired
  ApplicationDao appDao;

  @Autowired
  TaskResultDao taskResultDao;

  @Override
  public long size() {
    return taskResultDao.size();
  }

  @Override
  public void addTaskResult(@NonNull TaskResult taskResult) {
    //TODO Cache result to avoid this query
    ApplicationEntity appEntity = appDao.findByName(taskResult.getApplicationName());
    if (appEntity == null) {
      appEntity = new ApplicationEntity();
      appEntity.setName(taskResult.getApplicationName());
      appEntity.setStartTime(taskResult.getTaskStartTime());
      appDao.save(appEntity);
    }
    TaskResultEntity tre = TaskResultEntityFactory.createInstanceFrom(appEntity, taskResult);
    taskResultDao.save(tre);
  }

  @Override
  public boolean contains(TaskResult taskResult) {
    ApplicationEntity appEntity = appDao.findByName(taskResult.getApplicationName());
    return taskResultDao.find(appEntity.getName(), taskResult.getTaskName(), taskResult.getTaskStartTime()) != null;
  }

  @Override
  public void removeAll() {
    taskResultDao.deleteAll();
  }

  @Override
  public void removeOlderThan(LocalDateTime instant) {
    taskResultDao.deleteOlderThan(instant);
  }

  @Override
  public Iterator<TaskResult> iterator() {
    List<TaskResult> result = new ArrayList<>();
    appDao.findAllWithTasksResults().forEach(ae -> result.addAll(ae.getTasksResults()));
    return result.iterator();
  }

  public void setAppDao(ApplicationDao appDao) {
    this.appDao = appDao;
  }

  public void setTaskResultDao(TaskResultDao taskResultDao) {
    this.taskResultDao = taskResultDao;
  }
}
