package storage;

import task.storage.*;

public class InMemoryTasksResultsStorageTest
    extends TasksResultsStorageTest {

  @Override protected TasksResultsStorage createStorage() {
    return new InMemoryTasksResultStorage();
  }

}
