import storage.TasksResultsStorage;

public class InMemoryTasksResultsStorageTest extends TasksResultsStorageTest {

  @Override protected TasksResultsStorage createStorage() {
    return new InMemoryTasksResultStorage();
  }

}
