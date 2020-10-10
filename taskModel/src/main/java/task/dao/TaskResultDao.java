package task.dao;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import task.criteria.FilterCriteria;
import task.entities.TaskResultEntity;

public interface TaskResultDao {

  List<TaskResultEntity> findByCriteria(Collection<FilterCriteria> criteria);

  List<TaskResultEntity> findAll();

  TaskResultEntity findById(Long id);

  TaskResultEntity find(String appName, String taskName, LocalDateTime startTaskTime);

  TaskResultEntity save(TaskResultEntity app);

  void delete(TaskResultEntity app);

  void deleteOlderThan(LocalDateTime taskStartTime);

  void deleteAll();

  long size();
}
