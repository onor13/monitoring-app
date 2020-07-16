package storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import task.TaskResult;
import task.dao.ApplicationDao;
import task.dao.TaskResultDao;

import java.time.LocalDateTime;
import java.util.Iterator;

@Component
//TODO implement
public class DBTasksResultsStorage implements TasksResultsStorage {

  @Autowired
  ApplicationDao appDao;

  @Autowired
  TaskResultDao taskResultDao;

  //TODO cache new data in memory to avoid extra calls to DB;

  @Override
  public int size() {
    return 0;
  }

  @Override
  public void addTaskResult( TaskResult taskResult ) {

  }

  @Override
  public boolean contains( TaskResult taskResult ) {
    return false;
  }

  @Override
  public void removeAll() {

  }

  @Override
  public void removeOlderThan( LocalDateTime instant ) {

  }

  @Override
  public Iterator<TaskResult> iterator() {
    return null;
  }
}
