package task.dao;

import java.time.LocalDateTime;
import java.util.List;
import task.entities.TaskResultEntity;

public interface TaskResultDao {
  List<TaskResultEntity> findAll();

  TaskResultEntity findById(Long id);

  TaskResultEntity find(String appName, String taskName, LocalDateTime startTaskTime);

  TaskResultEntity save(TaskResultEntity app);

  void delete(TaskResultEntity app);

  void deleteOlderThan(LocalDateTime taskStartTime);

  void deleteAll();

  long size();
}
