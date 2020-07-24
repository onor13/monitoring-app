package task.dao;

import task.entities.ApplicationEntity;

import java.util.List;

public interface ApplicationDao {

  List<ApplicationEntity> findAll();

  List<ApplicationEntity> findAllWithTasksResults();

  ApplicationEntity findById(Long id);

  ApplicationEntity findByName(String name);

  ApplicationEntity save(ApplicationEntity app);

  void delete(ApplicationEntity app);
}
