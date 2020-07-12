package task.dao;

import task.entities.TaskResultEntity;

import java.util.List;

public interface TaskResultDao {
  List<TaskResultEntity> findAll();
  TaskResultEntity findById(Long id);
  TaskResultEntity save( TaskResultEntity app);
  void delete( TaskResultEntity app);
}
