package task.dao;

import task.entities.TaskResultEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskResultDao {
  List<TaskResultEntity> findAll();

  TaskResultEntity findById(Long id);

  TaskResultEntity find(Long appId, String taskName, LocalDateTime startTaskTime);

  TaskResultEntity save(TaskResultEntity app);

  void delete(TaskResultEntity app);
}
