package task.dao;

import java.time.LocalDateTime;
import java.util.List;
import task.entities.TaskResultEntity;

public interface TaskResultDao {
  List<TaskResultEntity> findAll();

  TaskResultEntity findById(Long id);

  TaskResultEntity find(Long appId, String taskName, LocalDateTime startTaskTime);

  TaskResultEntity save(TaskResultEntity app);

  void delete(TaskResultEntity app);
}
