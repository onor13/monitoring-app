package task.dao;

import task.entities.ApplicationTable;

import java.util.List;

public interface ApplicationDao {

  List<ApplicationTable> findAll();
  List<ApplicationTable> findAllWithTasksResults();
  ApplicationTable findById(Long id);
  ApplicationTable save( ApplicationTable app);
  void delete(ApplicationTable app);
}
