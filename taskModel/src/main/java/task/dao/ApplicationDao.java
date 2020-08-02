package task.dao;

import java.util.List;
import task.entities.ApplicationEntity;

public interface ApplicationDao {

  List<ApplicationEntity> findAll();

  List<ApplicationEntity> findAllWithTasksResults();

  ApplicationEntity findByName(String name);

  ApplicationEntity save(ApplicationEntity app);

  void delete(ApplicationEntity app);
}
