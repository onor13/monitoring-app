package task.dao;

import java.util.List;
import task.entities.ApplicationEntity;

public interface ApplicationDao {

  List<ApplicationEntity> findAll();

  List<ApplicationEntity> findAllWithTasksResults();

  ApplicationEntity findById(Long id);

  ApplicationEntity findByName(String name);

  ApplicationEntity save(ApplicationEntity app);

  void delete(ApplicationEntity app);
}
