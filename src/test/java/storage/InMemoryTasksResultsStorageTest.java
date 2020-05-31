package storage;

import task.storage.*;

public class InMemoryTasksResultsStorageTest
    extends TasksResultsStorageTest {

  @Override protected ITasksResultsStorage createStorage() {
    return new InMemoryTasksResultStorage();
  }

}
