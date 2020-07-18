package storage;

import factory.TaskResultEntityFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import task.TaskResult;
import task.dao.ApplicationDao;
import task.dao.TaskResultDao;
import task.entities.ApplicationEntity;
import task.entities.TaskResultEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class DBTasksResultsStorage implements TasksResultsStorage {

  @Autowired
  ApplicationDao appDao;

  @Autowired
  TaskResultDao taskResultDao;

  @Override
  public int size() {
    return 0;
  }

  @Override
  public void addTaskResult( @NonNull TaskResult taskResult ) {
    ApplicationEntity appEntity = appDao.findByName( taskResult.getApplicationName() ); //TODO Cache result to avoid this query
    if ( appEntity == null ){
      appEntity = new ApplicationEntity();
      appEntity.setName( taskResult.getApplicationName() );
      appEntity.setStartTime( taskResult.getTaskStartTime() );
      appDao.save( appEntity );
    }
    TaskResultEntity tre = TaskResultEntityFactory.createInstanceFrom( appEntity, taskResult );
    taskResultDao.save( tre );
  }

  @Override
  public boolean contains( TaskResult taskResult ) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void removeAll() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void removeOlderThan( LocalDateTime instant ) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Iterator<TaskResult> iterator() {
    List<TaskResult> result = new ArrayList<>();
    appDao.findAllWithTasksResults().forEach( ae -> result.addAll( ae.getTasksResults() ) );
    return result.iterator();
  }
}
